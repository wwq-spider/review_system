package com.review.manage.reviewClass.entity; 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author yourname
 * 
 */
@Entity
@Table(name="review_question_class")
public class ReviewQuestionClassEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	private java.lang.String Id;   
	
    @Column(name="question_id")
    private java.lang.Integer questionId;   
    @Column(name="class_id")
    private java.lang.String classId;
	public java.lang.String getId() {
		return Id;
	}
	public void setId(java.lang.String id) {
		Id = id;
	}
	public java.lang.Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(java.lang.Integer questionId) {
		this.questionId = questionId;
	}
	public java.lang.String getClassId() {
		return classId;
	}
	public void setClassId(java.lang.String classId) {
		this.classId = classId;
	}   
 
} 