package com.vodaphone.codechallenge.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vodaphone.codechallenge.model.MobileSubscriber;

public interface MobileSubscriberRepository extends JpaRepository<MobileSubscriber, Integer> {

  List<MobileSubscriber> findAll();

  MobileSubscriber findByMsisdn(String string);

  List<MobileSubscriber> findByCustomerIdOwner(int i);
}
