package com.work.finalproject.repository;


import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface review_repository extends JpaRepository<review_tbl, String>, QuerydslPredicateExecutor<review_tbl> {
    @Query("select r from review_tbl r where r.content_id = ?1")
    List<review_tbl> findByContent_id(String content_id);
}
