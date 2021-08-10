package com.work.finalproject.repository;

import com.work.finalproject.entity.like_tbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface like_repository extends JpaRepository<like_tbl, Integer> {

    @Query("select l from like_tbl l where l.content_id = ?1 and l.username = ?2 and l.like_type =?3")
    List<like_tbl> findByContent_idAndUsername(String content_id, String username, String like_type);

    @Transactional
    @Modifying
    @Query("delete from like_tbl l where l.content_id = ?1 and l.username = ?2 and l.like_type =?3")
    void deleteByContent_idAndUsername(String content_id, String username, String like_type);

    @Query("select l from like_tbl l where l.username =?1")
    List<like_tbl> findByUsername(String username);

}
