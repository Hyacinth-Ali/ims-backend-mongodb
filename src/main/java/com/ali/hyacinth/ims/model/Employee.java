package com.ali.hyacinth.ims.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name="employees")
public class Employee implements Serializable{
   private String password;
   
   public void setPassword(String value) {
    this.password = value;
    }
@Column( nullable=false ) 
public String getPassword() {
    return this.password;
    }
private long id;
public void setId (long value) 
{
   this.id = value;
   	}
@Id
@GeneratedValue
public long getId () 
{
   return this.id;
   	}
private boolean manager;

public void setManager(boolean value) {
    this.manager = value;
    }
@Column( nullable=false ) 
public boolean isManager() {
    return this.manager;
    }
private String firstName;

public void setFirstName(String value) {
    this.firstName = value;
    }
@Column( nullable=false, length=50 ) 
public String getFirstName() {
    return this.firstName;
    }
private String lastName;

public void setLastName(String value) {
    this.lastName = value;
    }
@Column( nullable=false, length=50 ) 
public String getLastName() {
    return this.lastName;
    }
private String email;

public void setEmail(String value) {
    this.email = value;
    }
@Column( unique=true, nullable=false, length=120 ) 
public String getEmail() {
    return this.email;
    }
private String userName;

public void setUserName(String value) {
    this.userName = value;
    }
@Column( unique=true, nullable=false, length=50 ) 
public String getUserName() {
    return this.userName;
    }
private String encryptedPassword;

public void setEncryptedPassword(String value) {
    this.encryptedPassword = value;
    }
@Column( nullable=false ) 
public String getEncryptedPassword() {
    return this.encryptedPassword;
    }
private String employeeId;

public void setEmployeeId(String value) {
    this.employeeId = value;
    }
@Column( nullable=false ) 
public String getEmployeeId() {
    return this.employeeId;
       }
   private Set<Transaction> sales;
   
   @OneToMany(mappedBy="seller" )
   public Set<Transaction> getSales() {
      return this.sales;
   }
   
   public void setSales(Set<Transaction> saless) {
      this.sales = saless;
   }
   
   private Set<Address> addresss;
   
   @OneToMany(mappedBy="employee" , cascade={CascadeType.ALL})
   public Set<Address> getAddresss() {
      return this.addresss;
   }
   
   public void setAddresss(Set<Address> addressss) {
      this.addresss = addressss;
   }
   
   private static final long serialVersionUID = 8282812751828419590L;
   }
