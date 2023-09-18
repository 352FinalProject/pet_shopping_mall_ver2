package com.shop.app.review.dto;

import java.sql.Timestamp;
import java.util.List;

import com.shop.app.common.entity.ImageAttachment;
import com.shop.app.pet.entity.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
	private int reviewId;
	private int reviewStarRate;
	private String reviewMemberId;
	private Timestamp reviewCreatedAt;
	private String reviewTitle;
	private String reviewContent;
	private List<ImageAttachment> reviewImages;
	private Pet pet;
	
}
