package com.vodaphone.codechallenge.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.model.ServiceType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MobileSubscriberRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;
  
  @Autowired
  private MobileSubscriberRepository mobileSubscriberRepository;
  
  @Test
  public void whenFindAll_thenReturnMobileSubscriberList() {

    MobileSubscriber mobileSubscriber = new MobileSubscriber("35699123456", 1, 1, ServiceType.MOBILE_PREPAID);
    entityManager.persist(mobileSubscriber);
    entityManager.flush();
    
    List<MobileSubscriber> resultList = mobileSubscriberRepository.findAll();
    
    assertThat(resultList).isNotEmpty().contains(mobileSubscriber);
  }
  
  @Test
  public void whenFindByNumber_thenReturnMobileSubscriber() {
    
    MobileSubscriber mobileSubscriber = new MobileSubscriber("35699123456", 1, 1, ServiceType.MOBILE_PREPAID);
    entityManager.persist(mobileSubscriber);
    entityManager.flush();
    
    MobileSubscriber found = mobileSubscriberRepository.findByMsisdn("35699123456");
    
    assertThat(found.getMsisdn()).isEqualTo(found.getMsisdn());
  }
  
  @Test
  public void whenFindByCustomerIdOwner_thenReturnMobileSubscriberList() {
    
    List<MobileSubscriber> list = new ArrayList<MobileSubscriber>(2);
    MobileSubscriber mobileSubscriber = new MobileSubscriber("35699123456", 1, 1, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    mobileSubscriber = new MobileSubscriber("35699123457", 2, 1, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    mobileSubscriber = new MobileSubscriber("35699123458", 1, 1, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    list.stream().forEach(mobSub -> {
      entityManager.persist(mobSub);
      entityManager.flush();
    });
    
    List<MobileSubscriber> resultList = mobileSubscriberRepository.findByCustomerIdOwner(1);
    
    assertThat(resultList).isNotEmpty().size().isEqualTo(2);
  }
  
  @Test
  public void whenFindByCustomerIdUser_thenReturnMobileSubscriberList() {
    List<MobileSubscriber> list = new ArrayList<MobileSubscriber>(2);
    MobileSubscriber mobileSubscriber = new MobileSubscriber("35699123456", 1, 1, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    mobileSubscriber = new MobileSubscriber("35699123457", 1, 2, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    mobileSubscriber = new MobileSubscriber("35699123458", 1, 1, ServiceType.MOBILE_PREPAID);
    list.add(mobileSubscriber);
    list.stream().forEach(mobSub -> {
      entityManager.persist(mobSub);
      entityManager.flush();
    });
    
    List<MobileSubscriber> resultList = mobileSubscriberRepository.findByCustomerIdUser(1);
    
    assertThat(resultList).isNotEmpty().size().isEqualTo(2);
  }

}
