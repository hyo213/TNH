package go.travel.dnh.mapper;

import go.travel.dnh.domain.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AdminMapper {
    List<MemberDTO> read();

    MemberDTO readMno(Integer mno);

    void update(MemberDTO memberDTO);
}
