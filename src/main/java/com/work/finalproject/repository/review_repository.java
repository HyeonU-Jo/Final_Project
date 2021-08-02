package com.work.finalproject.repository;


import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;
import java.util.List;


public interface review_repository extends JpaRepository<review_tbl, Integer>, QuerydslPredicateExecutor<review_tbl> {
    @Query("select r from review_tbl r where r.content_id = ?1")
    List<review_tbl> findByContent_id(String content_id);

    @Transactional
    @Modifying
    @Query(value = "select sum(r.r_rating) from review_tbl r where r.content_id=?1", nativeQuery = true)
    int sumR_rating(String content_id);

}
