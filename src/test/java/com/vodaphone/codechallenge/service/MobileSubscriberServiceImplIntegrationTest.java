package com.vodaphone.codechallenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.model.repository.MobileSubscriberRepository;
import com.vodaphone.codechallenge.service.impl.MobileSubscriberServiceImpl;

@RunWith(SpringRunner.class)
public class MobileSubscriberServiceImplIntegrationTest {
  
  private static final String MOBILE_NUMBER_1 = "35699123456";

  @TestConfiguration
  static class MobileSubscriberServiceImplTestConfiguration {
    
    @Bean
    public MobileSubscriberService mobileSubscriberService() {
      return new MobileSubscriberServiceImpl();
    }
  }
  
  @Autowired
  private MobileSubscriberService mobileSubscriberService;
  
  @MockBean
  private MobileSubscriberRepository mobileSubscriberRepository;
  
  @Before
  public void setUp() {
    MobileSubscriber mobileSubscriber = new MobileSubscriber(MOBILE_NUMBER_1, 1, 1, ServiceType.MOBILE_PREPAID);
    List<MobileSubscriber> mobileSubscribers = new ArrayList<>();
    mobileSubscribers.add(mobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.findByMsisdn(mobileSubscriber.getMsisdn()))
      .thenReturn(mobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.findAll())
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.findByCustomerIdOwner(mobileSubscriber.getCustomerIdOwner()))
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.findByCustomerIdUser(mobileSubscriber.getCustomerIdUser()))
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.setPlan(mobileSubscriber.getMsisdn(), mobileSubscriber.getServiceType()))
      .thenReturn(1);
    
    Mockito.when(mobileSubscriberRepository.deleteByMsisdn(mobileSubscriber.getMsisdn())).thenReturn(1);
  }
  
  @Test
  public void whenValidNumber_thenMobileSubscriberShouldBeFound() {
    
    MobileSubscriber found = mobileSubscriberService.getMobileSubscriberByNumber(MOBILE_NUMBER_1);
    
    assertThat(found.getMsisdn()).isEqualTo(MOBILE_NUMBER_1);
  }
  
  @Test
  public void whenFindAll_thenReturnMobileSubcriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getAllMobileSubscribers();
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void whenValidCustomerIdOwner_thenReturnMobileSubscriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(1);
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void whenValidCustomerIdUser_thenReturnMobileSubscriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getMobileSubscriberByCustomerIdUser(1);
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void whenServicePrepaidIsChanged_thenReturnPospaid() {
    int result = mobileSubscriberService.changeMobileSubscriberPlan(MOBILE_NUMBER_1);
    
    assertThat(result).isEqualTo(1);
  }
  
  @Test
  public void whenDeletingNumber_thenReturnDeleted() {
    int result = mobileSubscriberService.deleteByNumber(MOBILE_NUMBER_1);
    
    assertThat(result).isEqualTo(1);
  }
}
