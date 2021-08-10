package com.work.finalproject.repository;


import com.work.finalproject.entity.diary_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
public interface diary_repository extends JpaRepository<diary_tbl, Integer>, QuerydslPredicateExecutor<diary_tbl> {
}