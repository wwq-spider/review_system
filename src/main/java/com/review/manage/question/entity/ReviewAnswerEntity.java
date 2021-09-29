package com.review.manage.question.entity; 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 测评试题答案实体类
 * @author yourname
 * 
 */
@Entity
@Table(name="review_answer")
public class ReviewAnswerEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="answer_id",nullable=false,length=32)
	private java.lang.String answerId;   
	
    @Column(name="question_id")
    private Integer questionId;   
    @Column(name="answer_content")
    private java.lang.String answerContent;   
    @Column(name="answer_code")
    private java.lang.String answerCode;   
    @Column(name="grade")
    private java.lang.Double grade;   
    @Column(name="is_right")
    private java.lang.String isRight;   
    @Column(name="prepare")
    private java.lang.String prepare;   
    
    @Column(name="picture_attach")
    private String pictureAttach;
       
    public void setAnswerId(java.lang.String answerId){  
        this.answerId=answerId;   
    }    
       
    public java.lang.String getAnswerId(){    
        return this.answerId;    
    }    
       
    public void setQuestionId(Integer questionId){  
        this.questionId=questionId;   
    }    
       
    public Integer getQuestionId(){    
        return this.questionId;    
    }    
       
    public void setAnswerContent(java.lang.String answerContent){  
        this.answerContent=answerContent;   
    }    
       
    public java.lang.String getAnswerContent(){    
        return this.answerContent;    
    }    
       
    public void setAnswerCode(java.lang.String answerCode){  
        this.answerCode=answerCode;   
    }    
       
    public java.lang.String getAnswerCode(){    
        return this.answerCode;    
    }    

    public java.lang.Double getGrade() {
		return grade;
	}

	public void setGrade(java.lang.Double grade) {
		this.grade = grade;
	}

	public void setIsRight(java.lang.String isRight){  
        this.isRight=isRight;   
    }    
       
    public java.lang.String getIsRight(){    
        return this.isRight;    
    }    
       
    public void setPrepare(java.lang.String prepare){  
        this.prepare=prepare;   
    }    
       
    public java.lang.String getPrepare(){    
        return this.prepare;    
    }

	public String getPictureAttach() {
		return pictureAttach;
	}

	public void setPictureAttach(String pictureAttach) {
		this.pictureAttach = pictureAttach;
	}

   
       
} 