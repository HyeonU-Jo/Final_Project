package com.work.finalproject.repository;

import com.work.finalproject.entity.member_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface member_repository extends JpaRepository<member_tbl, String>{

    Optional<member_tbl> findById(String id);
}
