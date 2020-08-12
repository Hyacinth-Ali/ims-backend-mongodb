package com.ali.hyacinth.ims.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;
import javax.persistence.Column;

@Entity
public class CEO implements Serializable{
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
private String/*No type specified!*/ encryptedPassword;

public void setEncryptedPassword(String/*No type specified!*/ value) {
    this.encryptedPassword = value;
    }
@Column( nullable=false ) 
public String/*No type specified!*/ getEncryptedPassword() {
    return this.encryptedPassword;
    }
private String ceoId;

public void setCeoId(String value) {
    this.ceoId = value;
    }
@Column( nullable=false ) 
public String getCeoId() {
    return this.ceoId;
       }
   private static final long serialVersionUID = -1343071302787757901L;
   }
