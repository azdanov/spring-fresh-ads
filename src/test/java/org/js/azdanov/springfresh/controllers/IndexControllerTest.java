package org.js.azdanov.springfresh.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.js.azdanov.springfresh.security.StubUserDetailsService;
import org.js.azdanov.springfresh.services.AreaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IndexController.class)
class IndexControllerTest {

  @MockBean private AreaService areaService;

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldGetAreas() throws Exception {
    when(areaService.getAllAreasTree()).thenReturn(List.of());

    mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
      return new StubUserDetailsService(passwordEncoder);
    }
  }
}
