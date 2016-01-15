package app.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.login.LoginBean;
import app.login.LoginBeanToUserConverter;
import app.login.LoginDelegate;
import app.model.User;


@Controller
public class LoginController
{
	@Autowired
	private LoginDelegate loginDelegate;
	
	private LoginBeanToUserConverter converter;
	
	public LoginController() {
		converter = new LoginBeanToUserConverter();
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("login");
		LoginBean loginBean = new LoginBean();
		model.addObject("loginBean", loginBean);
		return model;
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	{
		ModelAndView model= null;
		try
		{
			boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
			if(isValidUser)
			{
				System.out.println("User Login Successful");
				
				model = new ModelAndView("welcome");
				model.addObject("user", loginBean.getUsername());
				User convertedUser = converter.convert(loginBean);
				request.getSession().setAttribute("user", convertedUser); 
			}
			else
			{
				model = new ModelAndView("login");
				model.addObject("loginBean", loginBean);
				model.addObject("message", "Invalid credentials!!");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	public ModelAndView executeRegistration(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")User user)
	{
		ModelAndView model = new ModelAndView("home");
		try
		{
			loginDelegate.saveUser(user);
			model.addObject("user" ,user);
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("home");

		return model;
	}
}
