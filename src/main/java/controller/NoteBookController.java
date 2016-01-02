package controller;


import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import model.Note;
import model.NoteManager;
import model.NoteManagerImpl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/")
@Scope("session")
public class NoteBookController extends HttpServlet{

	private static final long serialVersionUID = 3918673777710626949L;
	
	NoteManager noteManager = new NoteManagerImpl();
	
    @RequestMapping(value ="/addNote", method = RequestMethod.POST)
    public String addNote(ModelMap model, @ModelAttribute("note") Note note) {
    	noteManager.addNote(new Note(note.getName(), note.getContent()));
        model.addAttribute("notes", noteManager.getAllNotes());
        return "redirect:/notes";
    }
    
    @RequestMapping(value = "/addNote", method = RequestMethod.GET)
    public void addNote(ModelMap model) {
        model.addAttribute("note", new Note());       
    }
    
    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String displayAllNotes(ModelMap model) {
    	model.addAttribute("notes", noteManager.getAllNotes());
    	return "notes";
}
    
    @RequestMapping(value = "/notes/{name}", method = RequestMethod.GET)
    public String displayNoteByName(ModelMap model, @PathVariable("name") String name) {
    	model.addAttribute("note", noteManager.getNote(name));
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
    	List<Note> notes = noteManager.getAllNotes();
    	for (int i =0; i<notes.size(); i++) {
    		for (int k =0; k<selectedNotes.length; k++) {
    			if (notes.get(i).getName().equals(selectedNotes[k])) {
    				notes.remove(i);
    				i--;
    			}
    		}
    	}
    	noteManager.setNotes(notes);
    	return "redirect:/notes";
    }
    
   
    
}