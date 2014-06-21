package com.edwin.agentsys.model;



/**
 * AgCpPackageProduct entity. @author MyEclipse Persistence Tools
 */

public class AgCpPackageProduct  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer packageId;
     private Integer productId;


    // Constructors

    /** default constructor */
    public AgCpPackageProduct() {
    }

    
    /** full constructor */
    public AgCpPackageProduct(Integer packageId, Integer productId) {
        this.packageId = packageId;
        this.productId = productId;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
   








}