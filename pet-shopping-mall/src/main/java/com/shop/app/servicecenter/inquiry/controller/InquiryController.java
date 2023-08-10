package com.shop.app.servicecenter.inquiry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.app.servicecenter.inquiry.dto.QuestionCreateDto;
import com.shop.app.servicecenter.inquiry.dto.QuestionUpdateDto;
import com.shop.app.servicecenter.inquiry.entity.Answer;
import com.shop.app.servicecenter.inquiry.entity.Question;
import com.shop.app.servicecenter.inquiry.service.InquiryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/servicecenter")
@Controller
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;
	
	// 고객센터 (선모)
	@GetMapping("/service.do")
	public void serviceCenter() {
		
	}
	
	// 1:1 목록 조회 (예라)
	@GetMapping("/inquiry/questionList.do")
	public void questionList(Question question, Model model) {
		List<Question> questions = inquiryService.findQuestionAll(question);
		model.addAttribute("questions", questions);
	}
	
	// 1:1 목록 상세 조회 + 답변 (예라)
	@GetMapping("/inquiry/questionDetail.do")
	public void questionDetail(@RequestParam int questionId, Model model) {
	    
		// 1:1 상세 조회
		Question question = Question
				.builder()
				.id(questionId)
				.build();

	    Question questions = inquiryService.findQuestionById(question);
	    model.addAttribute("questions", questions);
	    
	    // 1:1 답변 조회
	    Answer answer = Answer
	    		.builder()
	    		.questionId(questionId)
	    		.build();

	    Answer answers = inquiryService.findQuestionAnswersById(answer);
	    model.addAttribute("answers", answers);
	}
	
	// 1:1 문의 작성 연결 (예라)
	@GetMapping("/inquiry/questionCreate.do")
	public void CreateQuestion() {}
	
	// 1:1 문의 작성 (예라)
	@PostMapping("/inquiry/questionCreate.do")
	public String CreateQuestion(QuestionCreateDto _question) {
		
		Question question = _question.toQuestion();
		int result = inquiryService.insertQuestion(question);
		
		return "redirect:/servicecenter/inquiry/questionList.do";
	}
	
	// 1:1 문의 삭제 (예라)
	@PostMapping("/inquiry/DeleteQuestion.do")
	public String DeleteQuestion(@RequestParam int id) {
		
		int result = inquiryService.deleteQuestion(id);
		
		return "redirect:/servicecenter/inquiry/questionList.do";
	}
	
	// 1:1 문의 수정 연결 (예라)
	@GetMapping("/inquiry/questionUpdate.do")
	public void UpdateQuestion(@RequestParam int id, Model model) {
		Question question = inquiryService.findById(id);
		
		model.addAttribute("question", question);
	}
	
	// 1:1 문의 수정 (예라)
	@PostMapping("/inquiry/questionUpdate.do")
	public String UpdateQuestion(QuestionUpdateDto _question) {
		
		Question question = _question.toQuestion();
		log.debug("question = {}", question);
		int result = inquiryService.updateQuestion(question);
		
		return "redirect:/servicecenter/inquiry/questionList.do";
//		return "redirect:/servicecenter/inquiry/questionDetail.do?id=";
	}
	
}
