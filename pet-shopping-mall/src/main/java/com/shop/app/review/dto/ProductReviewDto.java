package com.shop.app.review.dto;

import java.security.Timestamp;

import com.shop.app.pet.entity.Pet;
import com.shop.app.pet.entity.PetGender;

import lombok.Data;

@Data
public class ProductReviewDto {
	
	// review
	private int reviewId;
	private int productId;
	private String reviewMemberId;
	private String reviewTitle;
	private String reviewContent;
	private int reviewStarRate;
	private Timestamp reviewCreatedAt;
	
	// pet
	private int petId;
	private String petName;   
	private int petAge;
    private String petBreed;
    private String petWeight;
    private PetGender petGender;
    
	
	
}
