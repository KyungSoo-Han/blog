package kr.hankyungsoo.blog.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String phoneNo;

}
