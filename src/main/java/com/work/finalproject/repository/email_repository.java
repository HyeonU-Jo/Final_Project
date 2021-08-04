package com.work.finalproject.repository;

import com.work.finalproject.entity.Qmember_tbl;
import com.work.finalproject.entity.member_tbl;
import org.springframework.data.repository.CrudRepository;

public interface email_repository extends CrudRepository<member_tbl, String> {
    member_tbl findByEmailIgnoreCase(String email);
}
