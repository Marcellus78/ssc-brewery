package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerByIdTestWithUrlParamsBadCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584")
                .param("username","spring").param("password", "guruxxx"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerByIdTestWithUrlParams() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584")
                .param("username","spring").param("password", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCredentialsTest() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584")
                .header("Api-key","spring").header("Api-secret", "guruxxx"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void deleteBeerByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584")
                .header("Api-key","spring").header("Api-secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void deleteBeerNoAuthorize() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeer() throws Exception {

        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }
    @Test
    void findBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/eed94501-a881-4254-babc-f235525c7584"))
                .andExpect(status().isOk());
    }
    @Test
    void findBeerByUpc() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/235525c7584"))
                .andExpect(status().isOk());
    }
}
