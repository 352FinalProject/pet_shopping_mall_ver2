package com.shop.app.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.app.member.entity.MemberDetails;
import com.shop.app.order.dto.OrderCancelInfoDto;
import com.shop.app.order.dto.OrderHistoryDto;
import com.shop.app.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RequestMapping("/order")
@Controller
@Slf4j
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/orderDetails.do")
	public void orderDetails() {}
	
	@GetMapping("/orderExchange.do")
	public void orderExchange() {}
	
	/**
	 * 멤버 개인이 주문 내역 조회
	 */
	@GetMapping("/orderList.do")
	public void getOrderList(Model model, @RequestParam(name = "period", required = false) Integer period, @AuthenticationPrincipal MemberDetails member) {
	    String memberId = member.getMemberId();
	    List<OrderHistoryDto> orderHistories;

	    if (period != null) {
	        orderHistories = orderService.getOrderListByPeriod(memberId, period);
	    } else {
	        orderHistories = orderService.getOrderList(memberId);
	    }

	    String[] status = {"입금대기", "결제완료", "배송준비", "배송중", "배송완료", "주문취소", "환불완료"};

	    model.addAttribute("status", status);
	    model.addAttribute("orderHistories", orderHistories);
	}
	
	
	
	@GetMapping("/cancelOrderDetail.do")
	public void cancelOrder(Model model, @RequestParam String orderNo) {
		OrderCancelInfoDto cancelInfos = orderService.getCancelInfo(orderNo);
		model.addAttribute("cancelInfo", cancelInfos);
	}
	
	
	/**
	 * 미입금 주문의 주문 취소 (환불은 paymentController)
	 */
	@PostMapping("/cancelOrder.do")
	public String insertCancelOrder(RedirectAttributes redirectAttr, @RequestParam String orderNo, @RequestParam String isRefund) {
		int result = orderService.insertCancelOrder(orderNo, isRefund);
		return "redirect:/order/orderList.do";
	}
	
	
	@GetMapping("/orderDetail.do")
	public void orderDetail(Model model, @RequestParam String orderNo) {
	}
	
}
