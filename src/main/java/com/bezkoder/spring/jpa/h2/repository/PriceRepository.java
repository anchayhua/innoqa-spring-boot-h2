package com.bezkoder.spring.jpa.h2.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.h2.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(long productId);

    List<Price> findByCurrContainingIgnoreCase(String curr);

    List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            long brandId, long productId, Date startDate, Date endDate);
}
