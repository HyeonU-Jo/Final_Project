package com.work.finalproject.repository;

import com.work.finalproject.entity.diary_tbl;
import com.work.finalproject.entity.festa_tbl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface diary_repository extends JpaRepository<diary_tbl, Integer> {

}