package com.edwin.agentsys.model;



/**
 * AgCpOrderdDetail entity. @author MyEclipse Persistence Tools
 */

public class AgCpOrderdDetail  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer orderId;
     private Integer packageId;


    // Constructors

    /** default constructor */
    public AgCpOrderdDetail() {
    }

    
    /** full constructor */
    public AgCpOrderdDetail(Integer orderId, Integer packageId) {
        this.orderId = orderId;
        this.packageId = packageId;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }
   








}