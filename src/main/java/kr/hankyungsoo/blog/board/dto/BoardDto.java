package kr.hankyungsoo.blog.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String userId;
    private String orgFileName;
    private String srvFileName;
    private int viewCnt;
    private String createdDt;
    private String modifiedDt;
}
