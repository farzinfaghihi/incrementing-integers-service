package com.farzinfaghihi.thinkific;

import com.farzinfaghihi.thinkific.v1.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// This annotation marks each test as "dirty" after it completes, so the application context will reset and the database cleared
// It adds some extra overhead, but for simplicity it works for the tests here
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ThinkificApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		// Set the authenticated user performing secured API calls
		User currentUser = new User();
		currentUser.setId(1L);
		currentUser.setEmail("test@abc.com");
		currentUser.setPassword("strawberry");

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(currentUser, null, new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	@Test
	public void createUserTest() throws Exception {
		mockMvc.perform(post("/v1/users")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{ \"email\": \"test@abc.com\", \"password\": \"strawberry\" }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.apiKey").value("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.DYylzBiKrslvDPviVUBQ6K-ntKcsss_1SItBBzuwWKgQ-3zxaNtR7hxzd0Y-b6_r9JUbdHShBnRUX4r2FNF0hQ"));
	}

	@Test
	public void createDuplicateUserTest() throws Exception {
		mockMvc.perform(post("/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"test@abc.com\", \"password\": \"strawberry\" }"));

		mockMvc.perform(post("/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"test@abc.com\", \"password\": \"strawberry\" }"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value(hasSize(1)))
				.andExpect(jsonPath("$.errors[0].code").value("400"))
				.andExpect(jsonPath("$.errors[0].message").value("A user exists with this email address."));
	}

	@Test
	public void getCurrentIntegerAsFirstCallTest() throws Exception {
		createUserCall();

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("1"));
	}

	@Test
	public void getNextIntegerAsFirstCallTest() throws Exception {
		createUserCall();

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("2"));
	}

	@Test
	public void getCurrentAndNextIntegerTest() throws Exception {
		createUserCall();

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("3"));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("4"));

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("4"));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("5"));
	}

	@Test
	public void resetIntegerTest() throws Exception {
		createUserCall();

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("1"))
				.andExpect(jsonPath("$.data.value").value("2"));

		mockMvc.perform(put("/v1/current")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"value\": 200 }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("2"))
				.andExpect(jsonPath("$.data.value").value("200"));

		mockMvc.perform(put("/v1/current")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"value\": 500 }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("3"))
				.andExpect(jsonPath("$.data.value").value("500"));

		mockMvc.perform(get("/v1/current")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("3"))
				.andExpect(jsonPath("$.data.value").value("500"));

		mockMvc.perform(get("/v1/next")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value("3"))
				.andExpect(jsonPath("$.data.value").value("501"));

		mockMvc.perform(get("/v1/history")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.values").value(hasSize(3)))
				.andExpect(jsonPath("$.data.values[0]").value("1"))
				.andExpect(jsonPath("$.data.values[1]").value("200"))
				.andExpect(jsonPath("$.data.values[2]").value("500"));
	}

	private void createUserCall() throws Exception {
		mockMvc.perform(post("/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"test@abc.com\", \"password\": \"strawberry\" }"));
	}
}
