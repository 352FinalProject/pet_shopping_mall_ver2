package com.shop.app.review.entity;

import java.util.List;

import com.shop.app.common.entity.imageAttachment;
import com.shop.app.common.entity.imageAttachmentMapping;
import com.shop.app.servicecenter.inquiry.entity.Question;
import com.shop.app.servicecenter.inquiry.entity.QuestionDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReviewDetails extends Review {
	
//	private int reviewId;
//	private String reviewMemberId;
	
	private List<imageAttachment> attachments;
	private List<imageAttachmentMapping> attachmentMapping;
	

}
