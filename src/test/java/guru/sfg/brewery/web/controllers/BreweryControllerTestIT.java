package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BreweryControllerTestIT extends BaseIT{

    @Test
    void showBreweriesTestWithUrlParamsBadCredentials() throws Exception {

        mockMvc.perform(delete("/brewery/api/v1/breweries")
                .param("username","spring").param("password", "guruxxx"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void showBreweriesCustomerRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott","tiger")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void showBreweriesRestCustomerRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("scott","tiger")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void showBreweriesAdminRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring","guru")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void showBreweriesUserRole() throws Exception {

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }
    @Test
    void showBreweriesRestAdminRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("spring","guru")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void showBreweriesRestUserRole() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }
    @Test
    void showBreweriesRestUnauthorized() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }
}