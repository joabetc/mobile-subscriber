package com.vodaphone.codechallenge.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodaphone.codechallenge.dto.MobileSubscriberDTO;
import com.vodaphone.codechallenge.exceptions.MobileSubscriberAlreadyExistsException;
import com.vodaphone.codechallenge.exceptions.MobileSubscriberNotFoundException;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HttpRequestHomeControllerTest {
  
  private static final String VALID_MOBILE_NUMBER = "35699123456";
  private static final String INVALID_MOBILE_NUMBER = "35699123452";
  private static final String EXISTING_MOBILE_NUMBER = "35699123451";

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private MobileSubscriberService mobileSubscriberService;
  
  @Autowired
  private ObjectMapper mapper;
  
  @Before
  public void setUp() {
    
    MobileSubscriberDTO mobileSubscriberDTO = new MobileSubscriberDTO(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    MobileSubscriberDTO mobileSubscriberPlanChangedDTO = new MobileSubscriberDTO(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_POSTPAID);
    MobileSubscriberDTO existingMobileSubscriberDTO = new MobileSubscriberDTO(EXISTING_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_POSTPAID);
    
    List<MobileSubscriberDTO> allMobileSubscribersDTO = Arrays.asList(mobileSubscriberDTO);
    
    Mockito.when(mobileSubscriberService.getAllMobileSubscribers())
      .thenReturn(allMobileSubscribersDTO);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByNumber(VALID_MOBILE_NUMBER))
      .thenReturn(mobileSubscriberDTO);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByNumber(INVALID_MOBILE_NUMBER))
      .thenThrow(new MobileSubscriberNotFoundException(INVALID_MOBILE_NUMBER));
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(1))
      .thenReturn(allMobileSubscribersDTO);
    
    Mockito.when(mobileSubscriberService.getMobileSubscriberByCustomerIdUser(1))
      .thenReturn(allMobileSubscribersDTO);
    
    Mockito.when(mobileSubscriberService.changeMobileSubscriberPlan(VALID_MOBILE_NUMBER))
      .thenReturn(mobileSubscriberPlanChangedDTO);
    
    Mockito.when(mobileSubscriberService.changeMobileSubscriberPlan(INVALID_MOBILE_NUMBER))
      .thenThrow(new MobileSubscriberNotFoundException(INVALID_MOBILE_NUMBER));
    
    Mockito.when(mobileSubscriberService.deleteByNumber(VALID_MOBILE_NUMBER))
      .thenReturn(1);
    
    Mockito.when(mobileSubscriberService.deleteByNumber(INVALID_MOBILE_NUMBER))
      .thenThrow(new MobileSubscriberNotFoundException(INVALID_MOBILE_NUMBER));
    
    Mockito.when(mobileSubscriberService.createMobileSubscriber(existingMobileSubscriberDTO))
      .thenThrow(new MobileSubscriberAlreadyExistsException(EXISTING_MOBILE_NUMBER));
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
      .andExpect(jsonPath("$[0].msisdn", is(VALID_MOBILE_NUMBER)));
  }
  
  @Test
  public void givenMobileSubscribers_whenChangePlan_thenReturnChanged() throws Exception {
    
    mockMvc.perform(put("/api/subscribers/" + VALID_MOBILE_NUMBER + "/plan")
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.msisdn", is(VALID_MOBILE_NUMBER)));
  }
  
  @Test
  public void givenInvalidNumber_whenChangingPlan_thenThrowNotFound() throws Exception {
    
    mockMvc.perform(put("/api/subscribers/" + INVALID_MOBILE_NUMBER + "/plan")
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  public void givenMobileSubscribers_whenDeleteValidNumber_thenReturnDeleted() throws Exception {
    
    mockMvc.perform(delete("/api/subscribers/" + VALID_MOBILE_NUMBER)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
  
  @Test
  public void givenMobileSubscribers_whenDeleteValidNumber_thenThrowNotFound() throws Exception {
    
    mockMvc.perform(delete("/api/subscribers/" + INVALID_MOBILE_NUMBER)
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  public void givenNewNumber_whenInserting_thenReturnCreated() throws Exception {
    
    MobileSubscriberDTO mobileSubscriber = new MobileSubscriberDTO(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    mockMvc.perform(post("/api/subscribers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(mobileSubscriber)))
    .andDo(print())
    .andExpect(status().is(HttpStatus.CREATED.value()));
  }
  
  @Test
  public void givenExistingNumber_whenInserting_thenThrowAlreadyExists() throws Exception {
    
    MobileSubscriberDTO mobileSubscriber = new MobileSubscriberDTO(EXISTING_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    mockMvc.perform(post("/api/subscribers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(mobileSubscriber)))
    .andDo(print())
    .andExpect(status().is(HttpStatus.CONFLICT.value()));
  }
}
