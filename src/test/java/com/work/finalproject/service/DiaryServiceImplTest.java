package com.work.finalproject.service;

import com.work.finalproject.entity.diary_tbl;
import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.repository.diary_repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTest {

    @Autowired
    private diary_repository repository;

    @Test
    void registertest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            diary_tbl tbl = diary_tbl.builder()
                    .d_title(" Diary Title test..." + i)
                    .d_content("Diary Content test...." + i)
                    .d_username("Diary username~~~~"+(i))
                    .build();
            System.out.println(repository.save(tbl));
            System.out.println("good");
        });
    }


}