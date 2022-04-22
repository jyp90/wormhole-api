package com.jypark.coding.domain.advice.repository;

import com.jypark.coding.domain.advice.entity.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {}