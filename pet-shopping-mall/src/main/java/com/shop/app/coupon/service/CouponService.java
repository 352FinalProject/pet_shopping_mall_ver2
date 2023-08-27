package com.shop.app.coupon.service;

import java.util.List;

import com.shop.app.coupon.dto.MemberCouponDto;
import com.shop.app.coupon.entity.Coupon;
import com.shop.app.coupon.entity.MemberCoupon;

public interface CouponService {

	// 배송비 무료 쿠폰 회원한테 넣어주기 위해서 쿠폰 db 조회 (예라)
	List<Coupon> findCoupon();

	// memberCoupon db 추가 (예라)
	int insertDeliveryCoupon(MemberCoupon memberCoupon);
	
	// 멤버 쿠폰 전체 조회 (예라)
	List<MemberCouponDto> findCouponsByMemberId(String memberId);

	// 멤버 쿠폰 아이디 조회 (예라)
	int findCouponById();

	// 쿠폰 조회(예라)
	MemberCoupon findCouponCurrendById(MemberCoupon coupon);

	// 유효기간이 있는 쿠폰인지 확인 (예라)
	MemberCoupon validateCoupon(int couponId, String memberId);

	// 쿠폰 사용 (예라)
	int updateCouponStatus(MemberCoupon validCoupon);

}
