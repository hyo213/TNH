package go.travel.dnh.repository;

import go.travel.dnh.domain.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdminRepository {
    List<MemberDTO> readList();

    MemberDTO readMno(Integer mno);

    void update(MemberDTO memberDTO);
}
