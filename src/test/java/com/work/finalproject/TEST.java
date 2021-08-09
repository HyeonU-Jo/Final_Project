package com.work.finalproject;

import com.work.finalproject.entity.like_tbl;
import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.repository.like_repository;
import com.work.finalproject.repository.notice_repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class TEST {

    @Autowired
    private notice_repository noticeRepository;
    @Autowired
    private like_repository repository;

/*    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i -> {
            notice_tbl noticeTbl = notice_tbl.builder()
                    .n_title("Title...." + i)
                    .n_content("Content..." +i)
                    .n_writer("user" + (i % 10))
                    .build();
            System.out.println(noticeRepository.save(noticeTbl));
        });
    }*/
  /*  @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,5).forEach(i -> {
            like_tbl like = like_tbl.builder()
                    .no(i+10)
                    .content_id("content_id"+i)
                    .username("username"+i)
                    .build();
            System.out.println(repository.save(like));
        });
    }*/
}
