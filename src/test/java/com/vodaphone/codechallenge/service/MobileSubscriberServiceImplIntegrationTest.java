package com.vodaphone.codechallenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vodaphone.codechallenge.exceptions.MobileSubscriberNotFoundException;
import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;
import com.vodaphone.codechallenge.model.repository.MobileSubscriberRepository;
import com.vodaphone.codechallenge.service.impl.MobileSubscriberServiceImpl;

@RunWith(SpringRunner.class)
public class MobileSubscriberServiceImplIntegrationTest {
  
  private static final String VALID_MOBILE_NUMBER = "35699123456";
  private static final String NEW_MOBILE_NUMBER = "35699123457";
  private static final String INVALID_MOBILE_NUMBER = "35699123452";

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
    MobileSubscriber mobileSubscriber = new MobileSubscriber(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    MobileSubscriber mobileSubscriberPlanChanged = new MobileSubscriber(VALID_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_POSTPAID);
    MobileSubscriber newMobileSubscriber = new MobileSubscriber(NEW_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    Optional<MobileSubscriber> optMobileSubscriber = Optional.of(mobileSubscriber);
    
    List<MobileSubscriber> mobileSubscribers = new ArrayList<>();
    mobileSubscribers.add(mobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.findByMsisdn(VALID_MOBILE_NUMBER))
      .thenReturn(optMobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.findByMsisdn(NEW_MOBILE_NUMBER))
      .thenReturn(Optional.empty());
    
    Mockito.when(mobileSubscriberRepository.findAll())
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.findByCustomerIdOwner(mobileSubscriber.getCustomerIdOwner()))
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.findByCustomerIdUser(mobileSubscriber.getCustomerIdUser()))
      .thenReturn(mobileSubscribers);
    
    Mockito.when(mobileSubscriberRepository.save(mobileSubscriber))
      .thenReturn(mobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.save(mobileSubscriberPlanChanged))
      .thenReturn(mobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.save(newMobileSubscriber))
      .thenReturn(newMobileSubscriber);
    
    Mockito.when(mobileSubscriberRepository.deleteByMsisdn(VALID_MOBILE_NUMBER)).thenReturn(1);
  }
  
  @Test
  public void givenValidNumber_whenFindingByNumber_thenMobileSubscriberShouldBeFound() {
    
    MobileSubscriber found = mobileSubscriberService.getMobileSubscriberByNumber(VALID_MOBILE_NUMBER);
    
    assertThat(found.getMsisdn()).isEqualTo(VALID_MOBILE_NUMBER);
  }
  
  @Test
  public void givenInvalidNumber_whenFindingByNumber_thenShouldThrowNotFound() {
    
    assertThatExceptionOfType(MobileSubscriberNotFoundException.class).isThrownBy(() -> {
      mobileSubscriberService.getMobileSubscriberByNumber(INVALID_MOBILE_NUMBER);
    });
  }
  
  @Test
  public void givenMobileSubscriber_whenFindingdAll_thenReturnMobileSubcriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getAllMobileSubscribers();
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void givenMobileSubscriber_whenFindingdValidCustomerIdOwner_thenReturnMobileSubscriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(1);
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void givenMobileSubscriber_whenFindingdValidCustomerIdUser_thenReturnMobileSubscriberList() {
    
    List<MobileSubscriber> listFound = mobileSubscriberService.getMobileSubscriberByCustomerIdUser(1);
    
    assertThat(listFound).isNotEmpty().size().isEqualTo(1);
  }
  
  @Test
  public void whenServicePrepaidIsChanged_thenReturnPospaid() {
    MobileSubscriber result = mobileSubscriberService.changeMobileSubscriberPlan(VALID_MOBILE_NUMBER);
    
    assertThat(result).isNotNull();
  }
  
  @Test
  public void givenMobileSubscriber_whenDeletingNumber_thenReturnDeleted() {
    int result = mobileSubscriberService.deleteByNumber(VALID_MOBILE_NUMBER);
    
    assertThat(result).isEqualTo(1);
  }
  
  @Test
  public void givenMobileSubscriber_whenInsertingNumber_thenReturnCreated() {
    
    MobileSubscriber mobileSubscriber = new MobileSubscriber(NEW_MOBILE_NUMBER, 1, 1, ServiceType.MOBILE_PREPAID);
    
    MobileSubscriber saved = mobileSubscriberService.createMobileSubscriber(mobileSubscriber);
    
    assertThat(saved).isNull();
  }
}
