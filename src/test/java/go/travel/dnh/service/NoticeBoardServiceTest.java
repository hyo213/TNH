package go.travel.dnh.service;

import go.travel.dnh.domain.notice.NoticeDTO;
import go.travel.dnh.domain.notice.NoticeSearchDTO;
import go.travel.dnh.mapper.NoticeBoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NoticeBoardServiceTest {

    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    NoticeBoardMapper noticeBoardMapper;

    @Test
    void search() {
        String keyword = "볼이";
        NoticeSearchDTO noticeSearchDTO = new NoticeSearchDTO();
        noticeSearchDTO.setKeyword(keyword);
        List<NoticeDTO> list = noticeBoardMapper.search(noticeSearchDTO);
        list.get(1).getN_title();
        System.out.println("list.get(1).getN_title() = " + list.get(1).getN_title());
    }
    @Test
    void delete() {
        for (int i = 633; i <=636 ; i++) {
            noticeBoardService.boardDelete(i);
        }
    }
    @Test
    void insert() {
        for (int i = 1; i < 100 ; i++) {
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setNno(i);
            noticeDTO.setN_title("공지 TEST" + i);
            noticeDTO.setN_content("Hello, World");
            noticeBoardMapper.save(noticeDTO);
        }
    }

}