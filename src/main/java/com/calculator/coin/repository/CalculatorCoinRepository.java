package com.calculator.coin.repository;

import com.calculator.coin.entity.CalculatorCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CalculatorCoinRepository extends JpaRepository<CalculatorCoinEntity,Long> {

}
