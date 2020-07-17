package com.labforward.api.hello.service;

import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.core.validation.EntityValidator;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.util.HelloUtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class HelloWorldService {

	public static final String GREETING_NOT_FOUND = "Greeting Not Found";

	public static String DEFAULT_ID = "default";

	public static String DEFAULT_MESSAGE = "Hello World!";

	private Map<String, Greeting> greetings;

	private EntityValidator entityValidator;
	@Autowired
	private HelloUtilityClass helloUtilityClass;


	public HelloWorldService(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;

		this.greetings = new HashMap<>(1);
		save(getDefault());
	}

	private static Greeting getDefault() {
		return new Greeting(DEFAULT_ID, DEFAULT_MESSAGE);
	}

	public Greeting createGreeting(Greeting request) throws EntityValidationException {
		entityValidator.validateCreate(request);

		request.setId(UUID.randomUUID().toString());
		return save(request);

	}

	public Optional<Greeting> getGreeting(String id) {
		Greeting greeting = greetings.get(id);
		if (greeting == null) {
			return Optional.empty();
		}

		return Optional.of(greeting);
	}

	public Optional<Greeting> getDefaultGreeting() {
		return getGreeting(DEFAULT_ID);
	}

	private Greeting save(Greeting greeting) {
		this.greetings.put(greeting.getId(), greeting);
		return greeting;
	}
	/**
	 * This method will update any existing greeting message available with the new greeting message provided
	 * @return Optional<Object>
	 */
	public Optional<Object> update(String oldGreetingMsg, String newGreetingMessage) {
		boolean isExists=helloUtilityClass.checkIfGreetingExists(oldGreetingMsg,greetings);
		if(isExists)
		{
			String oldGreetingKey=this.greetings.entrySet().stream().filter(e->e.getValue().getMessage().equals(oldGreetingMsg)).findFirst().get().getKey();
			Greeting greeting=new Greeting(oldGreetingKey,newGreetingMessage);
			this.greetings.put(oldGreetingKey,greeting);
			return Optional.of(greeting);
		}
		else
		{
			throw new ResourceNotFoundException(GREETING_NOT_FOUND);
		}


	}

	/**
	 * This method will return map containing all stored greetings.
	 * @return Map
	 */
	public Map<String, Greeting> getAllMessage()
	{
		return this.greetings;
	}
}
