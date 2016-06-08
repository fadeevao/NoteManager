package app.controller;


import app.entities.User;
import app.login.LoginBean;
import app.login.LoginBeanToUserConverter;
import app.login.LoginDelegate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
public class LoginController
{
	private static final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginDelegate loginDelegate;

	private LoginBeanToUserConverter converter = new LoginBeanToUserConverter();


	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView entities = new ModelAndView("login");
		LoginBean loginBean = new LoginBean();
		entities.addObject("loginBean", loginBean);
		return entities;
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	{
		ModelAndView model= null;
		boolean userExists = loginDelegate.checkUsernameExists(loginBean.getUsername());

		//check if username exists
		if (userExists) {

			try
			{
				//check if overall credentials provided are valid
				boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
				if(isValidUser)
				{
					model = new ModelAndView("welcome");
					model.addObject("user", loginBean.getUsername());
					User convertedUser = converter.convert(loginBean, loginDelegate.getIdForUserFromLoginBean(loginBean));
					request.getSession().setAttribute("user", convertedUser);
					log.info("User " + convertedUser.getName() + "has logged in");
				}
				else
				{
					model = new ModelAndView("login");
					model.addObject("loginBean", loginBean);
					model.addObject("message", "Invalid credentials!");
					log.info("Invalid attempt to login");
				}

			}
			catch(Exception e)
			{
				log.error("Exception occured when user tried to log into the app", e);
			}
		} else {
			model = new ModelAndView("login");
			model.addObject("loginBean", loginBean);
			model.addObject("message", "Invalid credentials");
			log.info("Invalid attempt to login");
		}

		return model;
	}

	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ModelAndView displayRegistration(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("register");
		model.addObject("user", new User());
		return model;
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView executeRegistration(@Valid User user, BindingResult bindingResult)
	{
		ModelAndView modelAndView =  null;

		//validate user object
		if (bindingResult.hasErrors()) {
			return new ModelAndView("register");
		}

		boolean userExists = loginDelegate.checkUsernameExists(user.getName());
		if (!userExists) {
			try
			{
				loginDelegate.saveUser(user);
				log.info("New user has been registered with username: " + user.getName());
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
	public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView entities = new ModelAndView("home");
		return entities;
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpSession session, SessionStatus status) {
	    status.setComplete();
	    User user = (User) session.getAttribute("user");
	    request.removeAttribute("user");
	    log.info("User" + user.getName() + " has logged out");
		return new ModelAndView("redirect:/home");
	    //return "redirect:/home";
	}

}
