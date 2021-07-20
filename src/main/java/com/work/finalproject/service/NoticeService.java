package com.work.finalproject.service;


import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.notice_tbl;

public interface NoticeService {
    int register(NoticeDTO dto);

    //추가 내용 : notice_tblServiceImpl에 해당 메소드 추가(Override)되어야 함

    //목록관련
    PageResultDTO<NoticeDTO, notice_tbl> getList(PageRequestDTO requestDTO);

    //조회관련
    NoticeDTO read(int n_no);
    //수정관련
    void modify(NoticeDTO dto);
    //삭제관련
    void remove(int n_no);


    //DTO => Entity
    default notice_tbl dtoToEntity(NoticeDTO dto) {
        notice_tbl entity = notice_tbl.builder()
                .n_no(dto.getN_no())
                .n_title(dto.getN_title())
                .n_content(dto.getN_content())
                .n_writer(dto.getN_writer())
                .build();

        return entity;
    }

    //Entity => DTO
    default NoticeDTO entityToDto(notice_tbl entity) {
        NoticeDTO dto = NoticeDTO.builder()
                .n_no(entity.getN_no())
                .n_title(entity.getN_title())
                .n_content(entity.getN_content())
                .n_writer(entity.getN_writer())
                .build();

        return dto;
    }

}
