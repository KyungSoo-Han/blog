package kr.hankyungsoo.blog.user.service;


import kr.hankyungsoo.blog.user.dto.UserDto;
import kr.hankyungsoo.blog.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public UserDto login(String loginId, String password) {
        return userMapper.login(loginId, password);
    }
}
