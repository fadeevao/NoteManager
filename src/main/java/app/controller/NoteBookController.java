package app.controller;


import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import app.model.Note;
import app.model.NoteManagerImpl;
import app.model.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
 
@Controller
@RequestMapping("/")
@SessionAttributes("user")
public class NoteBookController extends HttpServlet implements Serializable {

	private static final Logger log = Logger.getLogger(NoteBookController.class);
	
	private User currentUser;
	
	@Autowired
	private NoteManagerImpl noteManager;
	
	private static final long serialVersionUID = 3918673777710626949L;

	@RequestMapping(value ="/addNote", method = RequestMethod.POST)
    public String addNote(ModelMap model, @ModelAttribute("note") Note note) {
    	noteManager.addNote(new Note(note.getName(), note.getContent()), currentUser);
        model.addAttribute("notes",(ArrayList<Note>)  noteManager.getAllNotes(currentUser.getId()));
        log.info("Added a new note to collection with name: "+ note.getName());
        return "redirect:/notes";
    }
    
    @RequestMapping(value = "/addNote", method = RequestMethod.GET)
    public void addNote(ModelMap model) {
        model.addAttribute("note", new Note());       
    }
    
    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String displayAllNotes(ModelMap model, HttpSession session) {
    	currentUser = (User) session.getAttribute("user");
    	model.addAttribute("notes",  (ArrayList<Note>) noteManager.getAllNotes(currentUser.getId()));
    	model.addAttribute("user", currentUser);
    	return "notes";
}
    
    @RequestMapping(value = "/notes/{name}", method = RequestMethod.GET)
    public String displayNoteByName(ModelMap model, @PathVariable("name") String name) {
    	model.addAttribute("note", noteManager.getNote(name));
    	log.info("Retrieving details about a note: " + name);
    	 User user = (User) model.get("user");
    	 System.out.println(user.getUsername());
    	return "note";
}
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteNotes(ModelMap model) {
    	noteManager.deleteAllNotes();
    	return "redirect:/notes";
    }
    
    @RequestMapping(value = "/deleteSelectedNotes", method = RequestMethod.POST)
    public String deleteSelectedNotes(ModelMap model,  HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
    	String[] selectedNotes = request.getParameterValues("selected");
    	if (selectedNotes != null) {
    		noteManager.deleteSelectedNotes(selectedNotes);
    		log.info("Deleted notes: " + selectedNotes.length);
    	}
    	return "redirect:/notes";
    }
    
}