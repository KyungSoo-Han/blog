package kr.hankyungsoo.blog.user.mapper;

import kr.hankyungsoo.blog.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto login(String loginId, String password);
}
