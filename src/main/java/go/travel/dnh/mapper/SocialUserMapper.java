package go.travel.dnh.mapper;

import go.travel.dnh.domain.User.SocialUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SocialUserMapper {
    SocialUser findByEmail(String s_id);
    void saveSocialUser(SocialUser socialUser);

    void deleteSocialUser(int sno);
}
