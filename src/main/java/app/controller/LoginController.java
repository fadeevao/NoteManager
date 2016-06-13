package app.controller;


import app.LogoutHandler;
import app.login.CurrentUser;
import app.login.LoginBean;
import app.login.LoginDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class LoginController
{
	private static final Logger log = Logger.getLogger(LoginController.class);

	private CurrentUser currentUser;

	@Autowired
	private LogoutHandler logoutHandler;

	@Autowired
	private LoginDelegate loginDelegate;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView displayLogin()
	{
		ModelAndView model = new ModelAndView("login");
		model.addObject("loginBean", new LoginBean());
		return model;
	}

	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request) {
		currentUser =  (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("user", currentUser.getUsername());
		log.info(String.format("User %s is viewing the welcome page", currentUser.getUsername()));
		return model;
	}

	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ModelAndView displayRegistration()
	{
		ModelAndView model = new ModelAndView("register");
		model.addObject("user", new LoginBean());
		return model;
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView executeRegistration(@Valid @ModelAttribute LoginBean user, BindingResult bindingResult)
	{
		ModelAndView modelAndView =  null;

		//validate user object
		if (bindingResult.hasErrors()) {
			return new ModelAndView("register");
		}

		boolean userExists = loginDelegate.checkUsernameExists(user.getUsername());
		if (!userExists) {
			try
			{
				loginDelegate.saveUser(user);
				log.info(String.format("New user has been registered with username: %s",  user.getUsername()));
				modelAndView = new ModelAndView("home");
			}
			catch(Exception e)
			{
				log.error("Exception occured when user tried to register in the app", e);
			}
		} else {
			modelAndView = new ModelAndView("register");
			modelAndView.addObject("invalidUsernameMessage", "Invalid username");
			log.error("Invalid attempt to register with existing credentials");

		}

		return modelAndView;
	}

	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView getHome()
	{
		ModelAndView model = new ModelAndView("home");
		return model;
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		logoutHandler.logout(request, httpServletResponse, SecurityContextHolder.getContext().getAuthentication());
		return new ModelAndView("redirect:/home");
	}

}
