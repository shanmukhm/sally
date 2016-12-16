package com.casabots.pantry.controller;

import com.casabots.pantry.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private MongoOperations mongoOperation;

    @InjectMocks
    private UserController userController = new UserController();

    private MockMvc controller;

    @Before
    public void setUp() {
        controller = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void shouldGetUser() throws Exception{
        User user = new User();
        user.setFirstName("testUser");
        user.setEmail("test@abc.com");
        user.setDob("testDate");
        user.setId("testID");
        Query query = new Query(Criteria.where("firstName").is("testUser"));
        when(mongoOperation.findOne(eq(query), eq(User.class))).thenReturn(user);

        controller.perform(get("/getUser/testUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(user)));

    }
}
