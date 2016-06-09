package app.controller;


import app.utils.NoteUtils;
import app.entities.Note;
import app.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RestController
@RequestMapping("/")
@SessionAttributes("user")
public class NoteBookController extends HttpServlet implements Serializable {

	private static final Logger log = Logger.getLogger(NoteBookController.class);

	private User currentUser;

	@Autowired
	private NoteUtils noteUtils;

	private static final long serialVersionUID = 3918673777710626949L;

	@RequestMapping(value ="/addNote", method = RequestMethod.POST)
    public ModelAndView addNote(ModelMap modelMap, @Valid @ModelAttribute("note") Note note, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addNote");
		}
    	noteUtils.addNote(new Note(note.getName(), note.getContent(), currentUser.getId()));
        modelMap.addAttribute("notes",(ArrayList<Note>)  noteUtils.getAllNotes(currentUser.getId()));
        log.info("Added a new note to collection with name: "+ note.getName());
		return new ModelAndView("redirect:/notes");
    }

    @RequestMapping(value = "/addNote", method = RequestMethod.GET)
    public ModelAndView addNote(ModelMap modelMap) {
        modelMap.addAttribute("note", new Note());
		return new ModelAndView("addNote");
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public ModelAndView displayAllNotes(ModelMap entities, HttpSession session) {
    	currentUser = (User) session.getAttribute("user");
    	entities.addAttribute("notes",  (ArrayList<Note>) noteUtils.getAllNotes(currentUser.getId()));
    	entities.addAttribute("user", currentUser);
    	return new ModelAndView("notes");
}

    @RequestMapping(value = "/notes/{name}", method = RequestMethod.GET)
    public ModelAndView displayNoteByName(ModelMap entities, @PathVariable("name") String name) {
    	entities.addAttribute("note", noteUtils.getNote(name));
    	log.info("Retrieving details about a note: " + name);
    	User user = (User) entities.get("user");
    	log.info("Current session user is: "+ user.getName());
    	return new ModelAndView("note");
}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteNotes(ModelMap entities) {
    	noteUtils.deleteAllNotes();
    	return new ModelAndView("redirect:/notes");
    }

    @RequestMapping(value = "/deleteSelectedNotes", method = RequestMethod.POST)
    public ModelAndView deleteSelectedNotes(ModelMap entities,  HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
    	String[] selectedNotes = request.getParameterValues("selected");
    	if (selectedNotes != null) {
    		noteUtils.deleteSelectedNotes(selectedNotes);
    		log.info("Deleted notes: " + selectedNotes.length);
    	}
    	return new ModelAndView("redirect:/notes");
    }

}