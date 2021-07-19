package com.work.finalproject.repository;


import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import com.work.finalproject.entity.review_tbl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface review_repository extends JpaRepository<review_tbl, String>, QuerydslPredicateExecutor<review_tbl> {
}
