package com.review.manage.question.entity; 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 测评试题实体类
 * @author yourname
 * 
 */
@Entity
@Table(name="review_question")
public class ReviewQuestionEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="question_id",nullable=false,length=10)
	private Integer questionId;   
	
    @Column(name="is_important")
    private java.lang.String isImportant;   
    @Column(name="question_type")
    private java.lang.String questionType;   
    
    @Column(name="right_answer")
    private String rightAnswer;

    @Column(name="content")
    private java.lang.String content;   
    @Column(name="question_num")
    private java.lang.Integer questionNum;   
    @Column(name="create_time")
    private java.util.Date createTime;   
    @Column(name="create_by")
    private java.lang.String createBy;   
    
    @Column(name="picture_attach")
    private byte[] pictureAttach;
    
    public void setQuestionId(Integer questionId){  
        this.questionId=questionId;   
    }    
       
    public Integer getQuestionId(){    
        return this.questionId;    
    }    
       
    public void setIsImportant(java.lang.String isImportant){  
        this.isImportant=isImportant;   
    }    
       
    public java.lang.String getIsImportant(){    
        return this.isImportant;    
    }    
       
    public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public void setQuestionType(java.lang.String questionType){  
        this.questionType=questionType;   
    }    
       
    public java.lang.String getQuestionType(){    
        return this.questionType;    
    }    
       
    public void setContent(java.lang.String content){  
        this.content=content;   
    }    


	public java.lang.String getContent(){    
        return this.content;    
    }    
       
    public void setQuestionNum(java.lang.Integer questionNum){  
        this.questionNum=questionNum;   
    }    
       
    public java.lang.Integer getQuestionNum(){    
        return this.questionNum;    
    }    
       
    public void setCreateTime(java.util.Date createTime){  
        this.createTime=createTime;   
    }    
       
    public java.util.Date getCreateTime(){    
        return this.createTime;    
    }    
       
    public void setCreateBy(java.lang.String createBy){  
        this.createBy=createBy;   
    }    
       
    public java.lang.String getCreateBy(){    
        return this.createBy;    
    }

	public byte[] getPictureAttach() {
		return pictureAttach;
	}

	public void setPictureAttach(byte[] pictureAttach) {
		this.pictureAttach = pictureAttach;
	}    
       
} 