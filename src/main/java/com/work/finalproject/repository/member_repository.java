package com.work.finalproject.repository;

import com.work.finalproject.entity.member_tbl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface member_repository extends JpaRepository<member_tbl, String> {

}
