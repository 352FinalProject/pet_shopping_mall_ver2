package com.shop.app.product.dto;

import java.util.List;

import com.shop.app.product.entity.ProductDetail;
import com.shop.app.review.dto.ProductReviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDetailDto {
	private List<ProductDetail> productOptions;
	private String productName;
	private int productId;
	private String thumbnail;
	private double reviewStarRateAvg;
	private int reviewCnt;
	private int productPrice;
	private List<ProductReviewDto> reviews;
}
