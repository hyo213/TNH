package go.travel.dnh.service;


import go.travel.dnh.domain.member.MemberDTO;
import go.travel.dnh.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<MemberDTO> readList() {
        return adminRepository.readList();
    }

    @Override
    public MemberDTO readMno(Integer mno) {
        return adminRepository.readMno(mno);
    }

    @Override
    public void updateRole(MemberDTO memberDTO) {
        adminRepository.update(memberDTO);
    }
}
