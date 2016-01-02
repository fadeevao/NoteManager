package controller;

import javax.servlet.http.HttpServlet;

import model.Note;
import model.NoteManager;
import model.NoteManagerImpl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    	noteManager.addNote(new Note(note.getName(), note.getSize()));
        model.addAttribute("notes", noteManager.getAllNotes());
        return "notes";
    }
 
    @RequestMapping(value = "/helloagain", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
        return "welcome";
    }
    
    @RequestMapping(value = "/addNote", method = RequestMethod.GET)
    public void addNote(ModelMap model) {
        model.addAttribute("note", new Note());       
    }
    
    @RequestMapping(value = "/listNotes", method = RequestMethod.GET)
    public String test(ModelMap model) { 
    	model.addAttribute("notes", noteManager.getAllNotes());
        return "notes";
    }

    
 
}