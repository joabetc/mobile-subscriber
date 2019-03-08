package com.vodaphone.codechallenge.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HttpRequestHomeControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private MobileSubscriberService mobileSubscriberService;
  
  @Before
  public void setUp() {
    
    MobileSubscriber mobileSubscriber = new MobileSubscriber("35699123456", 1, 1, ServiceType.MOBILE_PREPAID);
    
    List<MobileSubscriber> allMobileSubscribers = Arrays.asList(mobileSubscriber);
    
    Mockito.when(mobileSubscriberService.getAllMobileSubscribers()).thenReturn(allMobileSubscribers);
  }

  @Test
  public void givenMobileSubscribers_whenFindAll_thenReturnJsonArray() throws Exception {
    
    mockMvc.perform(get("/api/subscribers")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].msisdn", is("35699123456")));
    
  }

}
