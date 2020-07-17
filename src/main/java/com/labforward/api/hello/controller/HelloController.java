package com.labforward.api.hello.controller;

import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.domain.UpdateGreeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
public class HelloController {

	public static final String GREETING_NOT_FOUND = "Greeting Not Found";

	private HelloWorldService helloWorldService;

	public HelloController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	/**
	 * This method is used to get DEFAULT greeting based on DEFAULT_ID provided.
	 * @return Greeting This returns Greeting Object for the DEFAULT_ID.
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public Greeting helloWorld() {
		return getHello(HelloWorldService.DEFAULT_ID);
	}

	/**
	 * This method is used to get existing greeting based on id provided.
	 * @param id : Id of the sent greeting.
	 * @return Greeting This returns Greeting Object for the corresponding id.
	 */
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Greeting getHello(@PathVariable String id) {
		return helloWorldService.getGreeting(id)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	/**
	 * This method is used to create a new Greeting(UI Version).
	 * @param Greeting object which contains id and message.
	 * @return Greeting This returns Greeting Object which has been created.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Greeting createGreetingUI( Greeting request) throws EntityValidationException {
		return helloWorldService.createGreeting(request);
	}



	/**
	 * This method is used to get ModelAndView which would direct to home page i.e Home.html
	 * @
	 * @return ModelAndView:  It returns ModelAndView with empty Model and home.html
	 * 	as View.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("serverTime", formattedDate);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	/**
	 * This method is used to create a new Greeting (TEST VERSION).
	 * @
	 * @return ModelAndView:  It returns Greeting List set as Object inside ModelAndView and GreetingList.html
	 * 	as View.
	 */
	@RequestMapping(value = "/getAllMessages", method = RequestMethod.GET)
	public ModelAndView getAllMessages() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("greetings", helloWorldService.getAllMessage());
		modelAndView.setViewName("GreetingList");
		return modelAndView;
	}
	/**
	 * This method is used to update existing greeting which is already present.
	 * @param UpdateGreeting This is the model used for updating existing greeting,It contains two properties
	 *        ie. oldGreetingMsg and newGreetingMsg
	 * @return Greeting This returns Greeting Object which has been updated.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public Greeting updateGreeting( @RequestBody UpdateGreeting updateGreeting) {
		return (Greeting) helloWorldService.update(updateGreeting.getOldGreetingMsg(),updateGreeting.getNewGreetingMsg()).orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	/**
	 * This method is used to update existing greeting which is already present.
	 * @param Greeting object which contains id and message.
	 * @return Greeting This returns Greeting Object which has been created.
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public Greeting createGreeting( @RequestBody Greeting request) throws EntityValidationException {
		return helloWorldService.createGreeting(request);
	}
	@RequestMapping(value = "/uiupdate", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public Greeting updateGreetingUI( UpdateGreeting updateGreeting) {
		return (Greeting) helloWorldService.update(updateGreeting.getOldGreetingMsg(),updateGreeting.getNewGreetingMsg()).orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}
}
