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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vodaphone.codechallenge.exceptions.MobileSubscriberNotFoundException;
import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HttpRequestHomeControllerTest {
  
  private static final String VALID_MOBILE_NUMBER = "35699123456";
  private static final String INVALID_MOBILE_NUMBER = "35699123452";

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private MobileSubscriberService mobileSubscriberService;
  
  @Before
  public void setUp() {
    
    MobileSubscriber mobileSubscriber = new MobileSubscriber(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    List<MobileSubscriber> allMobileSubscribers = Arrays.asList(mobileSubscriber);
    
    Mockito.when(mobileSubscriberService.getAllMobileSubscribers()).thenReturn(allMobileSubscribers);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByNumber(VALID_MOBILE_NUMBER)).thenReturn(mobileSubscriber);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByNumber(INVALID_MOBILE_NUMBER))
    .thenThrow(new MobileSubscriberNotFoundException(INVALID_MOBILE_NUMBER));
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(1)).thenReturn(allMobileSubscribers);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByCustomerIdUser(1)).thenReturn(allMobileSubscribers);
    
    Mockito.when(mobileSubscriberService.changeMobileSubscriberPlan(VALID_MOBILE_NUMBER)).thenReturn(1);
  }

  @Test
  public void givenMobileSubscribers_whenFindAll_thenReturnJsonArray() throws Exception {
    
    mockMvc.perform(get("/api/subscribers")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].msisdn", is(VALID_MOBILE_NUMBER)));
    
  }

  @Test
  public void givenMobileSubscribers_whenFindByValideNumber_thenReturnJsonObject() throws Exception {
    
    mockMvc.perform(get("/api/subscribers/" + VALID_MOBILE_NUMBER)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.msisdn", is(VALID_MOBILE_NUMBER)));
  }
  
  @Test
  public void givenMobileSubscribers_whenFindByInvalidNumber_thenThrowNotFoundException() throws Exception {
    
    mockMvc.perform(get("/api/subscribers/" + INVALID_MOBILE_NUMBER)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  public void givenMobileSubscribers_whenFindByCustomerIdOwner_thenReturnJsonArray() throws Exception {
    
    mockMvc.perform(get("/api/subscribers/owner/1")
        .contentType(MediaType.APPLICATION_JSON))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$[0].msisdn", is(VALID_MOBILE_NUMBER)));
  }
  
  @Test
  public void givenMobileSubscribers_whenFindByCustomerIdUser_thenReturnJsonArray() throws Exception {
    
    mockMvc.perform(get("/api/subscribers/user/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].msisdn", is(MOBILE_NUMBER)));
  }
}
