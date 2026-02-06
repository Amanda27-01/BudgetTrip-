package com.budgettrip.repository;

import com.budgettrip.entity.Trip;
import com.budgettrip.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {

    // List<Trip> වෙනුවට Page<Trip> භාවිතා කිරීම
    Page<Trip> findByUser(User user, Pageable pageable);
}