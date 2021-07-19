package com.work.finalproject.repository;

import com.work.finalproject.entity.Qreview_tbl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.work.finalproject.entity.review_tbl;

import java.util.List;

@SpringBootTest
public class ReviewRepository {

    @Autowired
    private review_repository repository;

    @Test
    public void selectTest(){
        Qreview_tbl t = Qreview_tbl.review_tbl;


    }
    @Test
    public void findByContent_id(){
        review_tbl revt = new review_tbl();


        revt.setContent_id("2458633");



        List<review_tbl> all = repository.findByContent_id("2458633");

        for (int i = 0; i<all.size(); i++){
            System.out.println("값 확인한다. "+all.get(i).getR_content());
        }

    }

}
