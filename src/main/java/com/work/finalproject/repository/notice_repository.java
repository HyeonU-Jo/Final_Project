package com.work.finalproject.repository;

import com.work.finalproject.entity.notice_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface notice_repository extends JpaRepository<notice_tbl, Integer>, QuerydslPredicateExecutor<notice_tbl> {

}
