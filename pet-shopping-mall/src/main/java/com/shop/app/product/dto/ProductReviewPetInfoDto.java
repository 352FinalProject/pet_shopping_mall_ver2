package com.shop.app.product.dto;

import com.shop.app.pet.entity.PetGender;

import lombok.Data;

@Data
public class ProductReviewPetInfoDto {

	private int petId;
	private String petName;
	private int petAge;
	private String petBreed;
	private String petWeight;
	private PetGender petGender; 
	
	
}
