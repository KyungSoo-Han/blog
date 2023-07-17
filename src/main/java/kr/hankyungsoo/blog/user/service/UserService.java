package kr.hankyungsoo.blog.user.service;

import kr.hankyungsoo.blog.user.dto.UserDto;

public interface UserService {
    UserDto login (String loginId, String password);
}
