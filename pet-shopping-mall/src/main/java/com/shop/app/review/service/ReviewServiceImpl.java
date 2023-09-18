package com.shop.app.review.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.app.common.entity.ImageAttachment;
import com.shop.app.order.repository.OrderRepository;
import com.shop.app.order.service.OrderService;
import com.shop.app.pet.entity.Pet;
import com.shop.app.pet.repository.PetRepository;
import com.shop.app.pet.service.PetService;
import com.shop.app.point.entity.Point;
import com.shop.app.point.repository.PointRepository;
import com.shop.app.point.service.PointService;
import com.shop.app.product.dto.ProductInfoDto;
import com.shop.app.product.entity.Product;
import com.shop.app.product.repository.ProductRepository;
import com.shop.app.review.dto.ProductReviewAvgDto;
import com.shop.app.review.dto.ProductReviewDto;
import com.shop.app.review.dto.ReviewCreateDto;
import com.shop.app.review.dto.ReviewDetailDto;
import com.shop.app.review.dto.ReviewListDto;
import com.shop.app.review.dto.ReviewProductDto;
import com.shop.app.review.entity.Review;
import com.shop.app.review.entity.ReviewDetails;
import com.shop.app.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PetService petService;

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PointService pointService;
	
	
	/**
	 * @author 이혜령
	 * 리뷰 CRUD
	 */
	@Override
	public ReviewDetails createReview(ReviewCreateDto _review, List<ImageAttachment> attachments, Pet pet) {
		
		ReviewDetails reviews = ReviewDetails.builder()
				.reviewId(_review.getReviewId())
				.petId(pet.getPetId())
				.orderId(_review.getOrderId())
				.productId(_review.getProductId())
				.productDetailId(_review.getProductDetailId())
				.reviewMemberId(_review.getReviewMemberId())
				.reviewStarRate(_review.getReviewStarRate())
				.reviewTitle(_review.getReviewTitle())
				.reviewContent(_review.getReviewContent())
				.attachments(attachments)
				.build();
		
		// 리뷰 작성자의 펫정보 연결
		String memberId = _review.getReviewMemberId();
		List<Pet> petInfo = petService.findPetsByMemberId(memberId);
	
		// 가장 첫번째 펫정보를 등록
		if (!petInfo.isEmpty()) { 
			Pet firstPet = petInfo.get(0); 
			reviews.setPetId(firstPet.getPetId()); 
		} else {
			reviews.setPetId(null);
		}
		return reviews;
	}
	
	@Override
	public int insertReview(Review review) {
		int result = 0;
		result = reviewRepository.insertReview(review);
		int refId = review.getReviewId();
		
		List<ImageAttachment> attachments = ((ReviewDetails) review).getAttachments();
		if(attachments != null && !attachments.isEmpty()) {
			for(ImageAttachment attach : attachments) {
				
				int result2 = reviewRepository.insertAttachment(attach);
				int imageId = attach.getImageId();
				int reviewIdImageId = reviewRepository.insertMapping(refId, imageId);
			}
		}
		int orderId = review.getOrderId();
		int productDetailId = review.getProductDetailId();
		int productId = review.getProductId();
		int newStatus = 6;
		orderRepository.updateOrderStatusWithDetail(orderId, productDetailId, newStatus, productId);
		
		return result;
	}

	@Override
	public int reviewDelete(int reviewId) {
		return reviewRepository.reviewDelete(reviewId);
	}

	@Override
	public List<ReviewListDto> findReviewAll(Map<String, Object> params) {
		int limit = (int) params.get("limit");
		int page = (int) params.get("page");
		int offset = (page - 1) * limit;
		String reviewMemberId = (String) params.get("reviewMemberId");
		RowBounds rowBounds = new RowBounds(offset, limit);
		return reviewRepository.findReviewAll(reviewMemberId, rowBounds);
	}

	// 리뷰 페이징바
	@Override
	public int findTotalReviewCount(String reviewMemberId) {
		return reviewRepository.findTotalReviewCount(reviewMemberId);
	}

	@Override
	@Transactional
	public ReviewDetailDto findReviewId(int reviewId) {
		Review review = reviewRepository.findReviewId(reviewId);
		
	    ReviewDetailDto reviewDetailDto = new ReviewDetailDto();
	    reviewDetailDto.setReviewId(review.getReviewId());
	    reviewDetailDto.setReviewTitle(review.getReviewTitle());
	    reviewDetailDto.setReviewContent(review.getReviewContent());
	    reviewDetailDto.setReviewStarRate(review.getReviewStarRate());
	    reviewDetailDto.setReviewCreatedAt(review.getReviewCreatedAt());

	    // 펫정보 연결
	    Integer petId = review.getPetId();

	    // 펫정보가 없는 경우
	    if (petId != null ) {
		    Pet pet = petRepository.findPetById(review.getPetId());
		    reviewDetailDto.setPetId(pet.getPetId());
		    reviewDetailDto.setPetName(pet.getPetName());
		    reviewDetailDto.setPetAge(pet.getPetAge());
		    reviewDetailDto.setPetBreed(pet.getPetBreed());
		    reviewDetailDto.setPetWeight(pet.getPetWeight());
		    reviewDetailDto.setPetGender(pet.getPetGender());
	    } 
	    
	    return reviewDetailDto;
	}

	@Override
	public ReviewDetails findImageAttachmentsByReviewId(int reviewId) {
		return reviewRepository.findImageAttachmentsByReviewId(reviewId);
	}
	
	@Override
	public ReviewProductDto findProductReviewId(int reviewId) {
		return reviewRepository.findProductReviewId(reviewId);
	}

	@Override
	public int updateReview(Review review) {
		return reviewRepository.updateReview(review);
	}

	@Override
	public ReviewDetails getDeleteReviewById(int reviewId) {
		return reviewRepository.getDeleteReviewById(reviewId);
	}
	
	
	/**
	 * @author 이혜령
	 * 상품 상세페이지에서 리뷰 페이징바, 상세조회, 평균별점, 별점 선택비율(%)
	 */
	@Override
	public int findProductTotalReviewCount(int productId) {
		return reviewRepository.findProductTotalReviewCount(productId);
	}

	@Override
	public List<ProductReviewDto> findProductReviewAll(Map<String, Object> params, int productId) {
		int limit = (int) params.get("limit");
		int page = (int) params.get("page");
		int offset = (page - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return reviewRepository.findProductReviewAll(rowBounds, productId);
	}

	@Override
	public int findReviewTotalCount(int productId) {
		return reviewRepository.findReviewTotalCount(productId);
	}

	@Override
	public ProductReviewAvgDto productReviewStarAvg(int productId) {
		return reviewRepository.productReviewStarAvg(productId);
	}

	@Override
	public List<Review> findProductReviewAllStarPercent(int productId) {
		return reviewRepository.findProductReviewAllStarPercent(productId);
	}

	
    /**
     * @author 전예라
     * 리뷰 삭제 시 적립된 포인트 반환 (리팩토링)
     */
    @Override
    public void deleteReviewAndRollbackPoints(int reviewId) {
        Point earnedPoint = pointService.getPointByReviewId(reviewId);

        if (earnedPoint != null) {
            Point currentPoints = pointService.findReviewPointCurrentById(earnedPoint); 
            int updatedPointAmount = currentPoints.getPointCurrent() - earnedPoint.getPointAmount();

            Point rollbackPoint = new Point();
            rollbackPoint.setPointCurrent(updatedPointAmount);
            rollbackPoint.setPointAmount(-earnedPoint.getPointAmount());
            rollbackPoint.setPointType("리뷰삭제");
            rollbackPoint.setPointMemberId(earnedPoint.getPointMemberId());
            rollbackPoint.setReviewId(reviewId);

            int rollbackResult = pointService.insertRollbackPoint(rollbackPoint);
        }

        // 리뷰 삭제
        int result = this.reviewDelete(reviewId);
    }


}





