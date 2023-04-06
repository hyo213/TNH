package go.travel.dnh.repository;

import go.travel.dnh.domain.member.MemberDTO;
import go.travel.dnh.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminMapper adminMapper;

    @Override
    public List<MemberDTO> readList() {
        return adminMapper.read();
    }

    @Override
    public MemberDTO readMno(Integer mno) {
        return adminMapper.readMno(mno);
    }

    @Override
    public void update(MemberDTO memberDTO) {
        adminMapper.update(memberDTO);
    }
}
