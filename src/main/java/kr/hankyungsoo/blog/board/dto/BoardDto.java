package kr.hankyungsoo.blog.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String userId;
    private int viewCnt;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;
}
