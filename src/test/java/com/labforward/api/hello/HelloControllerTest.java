package com.labforward.api.hello;

import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.domain.UpdateGreeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest extends MVCIntegrationTest {

	private static final String HELLO_LUKE = "Hello Luke";

	@Test
	public void getHelloIsOKAndReturnsValidJSON() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(HelloWorldService.DEFAULT_ID)))
				.andExpect(jsonPath("$.message", is(HelloWorldService.DEFAULT_MESSAGE)));
	}

	@Test
	public void returnsBadRequestWhenMessageMissing() throws Exception {
		String body = "{}";
		mockMvc.perform(post("/hello").content(body)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void returnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
		String body = "{ \"tacos\":\"value\" }";
		mockMvc.perform(post("/hello").content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void returnsBadRequestWhenMessageEmptyString() throws Exception {
		Greeting emptyMessage = new Greeting("");
		final String body = getGreetingBody(emptyMessage);

		mockMvc.perform(post("/hello").content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void createOKWhenRequiredGreetingProvided() throws Exception {
		Greeting hello = new Greeting(HELLO_LUKE);
		final String body = getGreetingBody(hello);

		mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(hello.getMessage())));
	}
	@Test
	public void updateOKWhenExistingGreetingProvided() throws Exception {
		Greeting hello = new Greeting(HELLO_LUKE);
		final String body = getGreetingBody(hello);
		UpdateGreeting updateGreeting= new UpdateGreeting();
		updateGreeting.setOldGreetingMsg(HELLO_LUKE);
		updateGreeting.setNewGreetingMsg("Hello Ritesh");
		final String updatedBody=getUpdateGreetingBody(updateGreeting);
		mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(body))
				.andExpect(status().isOk());
		mockMvc.perform(post("/update").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(updatedBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(updateGreeting.getNewGreetingMsg())));
	}

	private String getGreetingBody(Greeting greeting) throws JSONException {
		JSONObject json = new JSONObject().put("message", greeting.getMessage());

		if (greeting.getId() != null) {
			json.put("id", greeting.getId());
		}

		return json.toString();
	}
	private String getUpdateGreetingBody(UpdateGreeting updateGreeting) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("oldGreetingMsg", updateGreeting.getOldGreetingMsg());
		json.put("newGreetingMsg",updateGreeting.getNewGreetingMsg());
		return json.toString();
	}

}
