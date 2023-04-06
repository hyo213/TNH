package go.travel.dnh.service;


import go.travel.dnh.domain.member.MemberDTO;

import java.util.List;

public interface AdminService {
    List<MemberDTO> readList();

    MemberDTO readMno(Integer mno);

    void updateRole(MemberDTO memberDTO);
}
