package com.shop.app.pet.service;

import java.util.List;
import com.shop.app.pet.dto.PetCreateDto;
import com.shop.app.pet.dto.PetUpdateDto;
import com.shop.app.pet.entity.Pet;
import com.shop.app.product.dto.ProductReviewPetInfoDto;

public interface PetService {
    int petCreate(PetCreateDto petCreateDto);
    
    int petUpdate(PetUpdateDto petUpdateDto);

    List<Pet> findPetsByMemberId(String memberId);

	Pet findPetById(int petId);

	int petDelete(int petId);

	// 상상품 상세페이지 - 리뷰에 펫정보 가져오기(혜령)
	List<ProductReviewPetInfoDto> findReviewPetByMemberId(String reviewMemberId);


	

}