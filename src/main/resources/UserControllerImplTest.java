package org.project.bestpractice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.CustomPageResponse;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IUserService userService;

    //1. ADD USER TEST
    @Test
    void addUser_shouldReturnOk_whenRequestIsValid() throws Exception {
        UserRequest request = new UserRequest("ramazan", "123456789", "ramazan@test.com");

        UserResponse mockResponse = UserResponse.builder().id(UUID.randomUUID()).email("ramazan@test.com").username("ramazan").build();
        when(userService.addUser(any(UserRequest.class))).thenReturn(mockResponse);


        ResultActions result = mockMvc.perform(post("/rest/api/user/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))); // Nesneyi JSON string'e çevirir


        result.andDo(print())
                .andExpect(status().isOk());
    }

    //2. GET USER BY ID TEST
    @Test
    void getUserById_shouldReturnUser_whenIdExists() throws Exception {

        UUID userId = UUID.randomUUID();
        UserResponse mockResponse = new UserResponse();
        mockResponse.setId(userId);
        mockResponse.setUsername("ramazan");
        mockResponse.setEmail("ramazan@gmail.com");

        when(userService.getUserById(userId)).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(get("/rest/api/user/getById/{id}", userId));

        result.andDo(print())
                .andExpect(status().isOk());
    }

    //3. GET USER LIST TEST
    @Test
    void getUserList_shouldReturnList() throws Exception {
        // Listeyi hazırladık
        List<UserResponse> mockList = List.of(
                UserResponse.builder().id(UUID.randomUUID()).username("sedat").email("sedat@gmail.com").build(),
                UserResponse.builder().id(UUID.randomUUID()).username("burak").email("burak@gmail.com").build()
        );

        // DÜZELTME 3: Listeyi CustomPageResponse içine koyduk.
        // Listeyi direkt cast edemezsin, hata alırdın.
        CustomPageResponse<UserResponse> pageResponse = new CustomPageResponse<>();
        pageResponse.setContent(mockList);
        pageResponse.setTotalElements(2);
        pageResponse.setPageNumber(0);

        // Mock servisin dönüşünü ayarladık
        when(userService.getUserList(0, 10)).thenReturn(pageResponse);

        // Varsayılan page=0, size=10 ile çağırdık
        mockMvc.perform(get("/rest/api/user/getUserList")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //4. VALIDATION TEST
    @Test
    void addUser_shouldReturnBadRequest_whenEmailIsInvalid() throws Exception {
        UserRequest invalidRequest = new UserRequest("ramazan", "bozuk-email", "123");

        mockMvc.perform(post("/rest/api/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());//HTTP 400
    }

    // --- 3. UPDATE USER TEST ---
    @Test
    void updateUser_shouldReturnOk_whenRequestIsValid() throws Exception {
        UserRequest updateRequest = new UserRequest(
                "ramazan_updated",
                "newPassword123",
                "ramazan_new@test.com"

        );
        UUID userId = UUID.randomUUID();

        UserResponse mockResponse = UserResponse
                .builder().email("ramazan_new@test.com")
                .id(userId)
                .username("ramazan_updated")
                .build();

        when(userService.updateUser(any(UserRequest.class),eq(userId))).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(put("/rest/api/user/updateUser/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        result.andDo(print())
                .andExpect(status().isOk());
    }






}
