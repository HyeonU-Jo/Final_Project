package com.work.finalproject.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    // 총 페이지
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호
    private int start;

    // 끝 페이지 번호
    private int end;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // 이전 버튼 링크 여부
    private boolean prev;

    // 다음 버튼 링크 여부
    private boolean next;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());

    }

    public void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page/10.0))*10;

        start = tempEnd -9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
