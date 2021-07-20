package com.work.finalproject;

import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.repository.notice_repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class noticeRepositoryTests {

    @Autowired
    private notice_repository noticeRepository;

    @Test

    public void insertNotice() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            notice_tbl noticeTbl = notice_tbl.builder()
                    .n_title(" Title..." + i)
                    .n_content("Content...." + i)
                    .n_writer("writer~~~~"+(i%10))
                    .build();
            System.out.println(noticeRepository.save(noticeTbl));
        });
    }

}
