package com.shop.app.admin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.shop.app.member.entity.Member;
import com.shop.app.servicecenter.inquiry.entity.Question;
@Mapper
public interface AdminRepository {
	
	@Select("select * from member order by id desc")
	List<Member> adminMemberList(RowBounds rowBounds);
	
	@Select("select * from member where name like '%' || #{searchKeyword} || '%' or member_id like '%' || #{searchKeyword} || '%'")
	List<Member> adminMemberSearchByNameOrId(@Param("searchKeyword") String searchKeyword);
	
	@Insert("insert into member values ()")
	int insertMember(Member member);
	
	@Update("update member set birthday =")
	int updateMember(Member member);
	
	@Delete("delete memeber where id = #{memberId}")
	int deleteMember(Member member);

	// 관리자 1:1 문의 전체 내역 조회 + 페이징바(예라)
	@Select("select q.*, (select count(*) from answer where answer_question_id = q.question_id) awnser_count from question q order by question_id desc")
	List<Question> findQuestionAll(RowBounds rowBounds);

	// 관리자 1:1 문의 전체 카운트 (예라)
	@Select("select count (*) from question")
	int findTotalQuestionCount();
	
	@Select("select * from question where question_title like '%' || #{searchKeyword} || '%' or question_content like '%' || #{searchKeyword} || '%'")
	List<Question> questionSearch(String searchKeyword);


}
