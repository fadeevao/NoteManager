package app.controller;


import app.entities.Note;
import app.login.CurrentUser;
import app.login.CurrentUserInitializer;
import app.utils.NoteUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/notebook")
public class NoteBookController {

	private static final Logger log = Logger.getLogger(NoteBookController.class);

	private CurrentUser currentUser;

	@Autowired
	private NoteUtils noteUtils;

	@Autowired
	private CurrentUserInitializer currentUserInitializer;


	@RequestMapping(path="/welcome", method = RequestMethod.GET)
	public ModelAndView welcome() {
		currentUser =  currentUserInitializer.initializeCurrentUser();
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("user", currentUser.getUsername());
		log.info(String.format("User %s is viewing the welcome page", currentUser.getUsername()));
		return model;
	}

	@RequestMapping(path ="/addNote", method = RequestMethod.POST)
    public ModelAndView addNote(ModelMap modelMap, @Valid @ModelAttribute("note") Note note, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addNote");
		}
		currentUser =  currentUserInitializer.initializeCurrentUser();
    	noteUtils.addNote(new Note(note.getName(), note.getContent(), currentUser.getId()));
        modelMap.addAttribute("notes", noteUtils.getAllNotes(currentUser.getId()));
        log.info("Added a new note to collection with name: "+ note.getName());
		return new ModelAndView("redirect:/notebook/notes");
    }

    @RequestMapping(path = "/addNote", method = RequestMethod.GET)
    public ModelAndView addNote(ModelMap modelMap) {
        modelMap.addAttribute("note", new Note());
		return new ModelAndView("addNote");
    }

	@RequestMapping(path = "/notes", method = RequestMethod.GET)
    public ModelAndView displayAllNotes(ModelMap model) {
		currentUser =  currentUserInitializer.initializeCurrentUser();
    	model.addAttribute("notes", noteUtils.getAllNotes(currentUser.getId()));
    	model.addAttribute("user", currentUser.getUser());
    	return new ModelAndView("notes");
	}

    @RequestMapping(path = "/notes/{name}", method = RequestMethod.GET)
    public ModelAndView displayNoteByName(ModelMap model, @PathVariable("name") String name) {
    	model.addAttribute("note", noteUtils.getNote(name));
    	log.info("Retrieving details about a note: " + name);
    	return new ModelAndView("note");
	}

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteNotes() {
    	noteUtils.deleteAllNotes();
    	return new ModelAndView("redirect:/notebook/notes");
    }

    @RequestMapping(value = "/deleteSelectedNotes", method = RequestMethod.POST)
    public ModelAndView deleteSelectedNotes(HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
    	String[] selectedNotes = request.getParameterValues("selected");
    	if (selectedNotes != null) {
    		noteUtils.deleteSelectedNotes(selectedNotes);
    		log.info("Deleted notes: " + selectedNotes.length);
    	}
    	return new ModelAndView("redirect:/notebook/notes");
    }

}