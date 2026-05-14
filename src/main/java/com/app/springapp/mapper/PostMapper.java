package com.app.springapp.mapper;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
//    게시글 목록 가져오기
    public List<PostDTO> selectAll(Map<String, Object> filters);

//    특정 게시글의 정보 (상세정보) 불러오기
    public PostDTO select(PostDTO postDTO);

//    유저 프로필 에서 유저가 작성한 게시글 목록
    public List<PostDTO> selectByUserId(Map<String, Object> filters);

//    게시글 전체 갯수 (페이지네이션 용)
    public int selectCount(String postTag);

//    유저 작성한 게시글 전체 갯수
    public int countByUserId(Long userId);

//    해당 게시글 수정 가능한지 (해당 게시글을 해당 유저가 작성한게 맞는지?)
    public int existByIdAndUserId(PostVO postVO);

//    게시글 작성
    public void insert(PostVO postVO);

//    게시글 수정
    public void update(PostVO postVO);

//    게시글 조회수 1 증가
    public void updatePostReadCount(Long id);

//    게시글 삭제 (소프트 삭제)
    public void updatePostIsDeleted(PostVO postVO);
}
