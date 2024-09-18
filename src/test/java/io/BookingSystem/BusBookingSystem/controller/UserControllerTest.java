package io.BookingSystem.BusBookingSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.BookingSystem.BusBookingSystem.dto.UserDto;
import io.BookingSystem.BusBookingSystem.entity.User;
import io.BookingSystem.BusBookingSystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserRegistration() throws Exception {
        // Create a new user
        UserDto userDto = new UserDto();
        userDto.setUserName("Anil123");
        userDto.setEmail("billupatianil@gmail.com");
        userDto.setPassword("Nani@1902");
        userDto.setRole("User");

        // Convert the userDto to JSON
        String json = objectMapper.writeValueAsString(userDto);

        // Send a POST request to the /register endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType("application/json;charset=UTF-8") // Use MediaType.APPLICATION_JSON instead of "APPLICATION_JSON;charset=UTF-8"
                        .content(json))
                .andExpect(status().isCreated());
        User savedUser = userRepository.findByEmail("billupatianil@gmail.com");

        assertEquals("Anil123", savedUser.getUserName());
        assertEquals("billupatianil@gmail.com", savedUser.getEmail());
        assertEquals("User", savedUser.getRole());
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
