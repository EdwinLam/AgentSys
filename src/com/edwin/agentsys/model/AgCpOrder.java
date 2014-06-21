package com.edwin.agentsys.model;

import java.util.Date;


/**
 * AgCpOrder entity. @author MyEclipse Persistence Tools
 */

public class AgCpOrder  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userId;
     private Date createTime;


    // Constructors

    /** default constructor */
    public AgCpOrder() {
    }

    
    /** full constructor */
    public AgCpOrder(Integer userId, Date createTime) {
        this.userId = userId;
        this.createTime = createTime;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   








}