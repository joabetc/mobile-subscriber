package com.vodaphone.codechallenge.service;

import static org.assertj.core.api.Assertions.assertThat;

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
    
    Mockito.when(mobileSubscriberRepository.findByMsisdn(mobileSubscriber.getMsisdn()))
      .thenReturn(mobileSubscriber);
  }
  
  @Test
  public void whenValidNumber_ThenMobileSubscriberShouldBeFound() {
    
    MobileSubscriber found = mobileSubscriberService.getMobileSubscriberByNumber(MOBILE_NUMBER_1);
    
    assertThat(found.getMsisdn()).isEqualTo(MOBILE_NUMBER_1);
  }
}
