package com.work.finalproject.repository;

import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.entity.review_tbl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface review_repository extends JpaRepository<review_tbl, String> {
//    List<ReviewDTO> findByContent_id(String content_id);
    List<ReviewDTO> findByContent_idContains(String content_id);
}
