package app.controller;


import app.NoteManager;
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
	private NoteManager noteManager;

	private static final long serialVersionUID = 3918673777710626949L;

	@RequestMapping(value ="/addNote", method = RequestMethod.POST)
    public ModelAndView addNote(ModelMap entities, @Valid @ModelAttribute("note") Note note, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addNote");
		}
    	noteManager.addNote(new Note(note.getName(), note.getContent(), currentUser.getId()));
        entities.addAttribute("notes",(ArrayList<Note>)  noteManager.getAllNotes(currentUser.getId()));
        log.info("Added a new note to collection with name: "+ note.getName());
		return new ModelAndView("redirect:/notes");
    }

    @RequestMapping(value = "/addNote", method = RequestMethod.GET)
    public void addNote(ModelMap modelMap) {
        modelMap.addAttribute("note", new Note());
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public ModelAndView displayAllNotes(ModelMap entities, HttpSession session) {
    	currentUser = (User) session.getAttribute("user");
    	entities.addAttribute("notes",  (ArrayList<Note>) noteManager.getAllNotes(currentUser.getId()));
    	entities.addAttribute("user", currentUser);
    	return new ModelAndView("notes");
}

    @RequestMapping(value = "/notes/{name}", method = RequestMethod.GET)
    public ModelAndView displayNoteByName(ModelMap entities, @PathVariable("name") String name) {
    	entities.addAttribute("note", noteManager.getNote(name));
    	log.info("Retrieving details about a note: " + name);
    	User user = (User) entities.get("user");
    	log.info("Current session user is: "+ user.getName());
    	return new ModelAndView("note");
}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteNotes(ModelMap entities) {
    	noteManager.deleteAllNotes();
    	return new ModelAndView("redirect:/notes");
    }

    @RequestMapping(value = "/deleteSelectedNotes", method = RequestMethod.POST)
    public ModelAndView deleteSelectedNotes(ModelMap entities,  HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
    	String[] selectedNotes = request.getParameterValues("selected");
    	if (selectedNotes != null) {
    		noteManager.deleteSelectedNotes(selectedNotes);
    		log.info("Deleted notes: " + selectedNotes.length);
    	}
    	return new ModelAndView("redirect:/notes");
    }

}