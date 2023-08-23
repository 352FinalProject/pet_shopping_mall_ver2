package com.shop.app.coupon.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shop.app.coupon.entity.Coupon;
import com.shop.app.coupon.entity.MemberCoupon;
import com.shop.app.coupon.repository.CouponRepository;
import com.shop.app.member.entity.Member;
import com.shop.app.member.repository.MemberRepository;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	// 배송비 무료 쿠폰 회원한테 넣어주기 위해서 쿠폰 db 조회 (예라)
	@Override
	public List<Coupon> findCoupon() {
		return couponRepository.findCoupon();
	}

	// memberCoupon db 추가
	@Override
	public int insertDeliveryCoupon(MemberCoupon memberCoupon) {
		return couponRepository.insertDeliveryCoupon(memberCoupon);
	}
	
	@Scheduled(cron = "0 38 19 * * ?")  // 매월 1일 자정에 실행
	public void issueBirthdayCoupons() {
	    
	    // 오늘 날짜 (매월 1일)
	    LocalDate today = LocalDate.now();
	    int currentMonth = today.getMonthValue();
	    
	    // 1. 쿠폰 지급을 위해 이번 달에 생일인 회원을 찾는다.
	    List<Member> thisMonthBirthdayMembers = memberRepository.findThisMonthBirthdayMembers(currentMonth);
	    
	    for (Member member : thisMonthBirthdayMembers) {
	        
	        // 쿠폰 발급
	        MemberCoupon memberCoupon = new MemberCoupon();
	        memberCoupon.setMemberId(member.getMemberId());
	        memberCoupon.setCouponId(1);  // 생일 쿠폰 id
	        
	        // 이번 달 1일을 기준으로 쿠폰 발급
	        memberCoupon.setCreateDate(LocalDateTime.of(today.getYear(), currentMonth, 1, 0, 0));
	        memberCoupon.setEndDate(memberCoupon.getCreateDate().plusMonths(1));  // 유효기간 1개월
	        memberCoupon.setUseStatus(0);  // 사용 안함
	        
	        int result = couponRepository.insertDeliveryCoupon(memberCoupon);
	    }
	}


}
