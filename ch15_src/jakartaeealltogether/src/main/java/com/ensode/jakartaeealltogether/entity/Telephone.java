package com.ensode.jakartaeealltogether.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TELEPHONES")
@NamedQueries({
  @NamedQuery(name = "Telephone.findAll", query = "SELECT t FROM Telephone t"),
  @NamedQuery(name = "Telephone.findByTelephoneId", query = "SELECT t FROM Telephone t WHERE t.telephoneId = :telephoneId"),
  @NamedQuery(name = "Telephone.findByTelephoneNumber", query = "SELECT t FROM Telephone t WHERE t.telephoneNumber = :telephoneNumber")})
public class Telephone implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "TELEPHONE_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer telephoneId;
  @Column(name = "TELEPHONE_NUMBER")
  private String telephoneNumber;
  @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
  @ManyToOne
  private Customer customer;
  @JoinColumn(name = "TELEPHONE_TYPE_ID", referencedColumnName = "TELEPHONE_TYPE_ID")
  @ManyToOne
  private TelephoneType telephoneType;

  public Telephone() {
  }

  public Telephone(Integer telephoneId) {
    this.telephoneId = telephoneId;
  }

  public Integer getTelephoneId() {
    return telephoneId;
  }

  public void setTelephoneId(Integer telephoneId) {
    this.telephoneId = telephoneId;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public TelephoneType getTelephoneType() {
    return telephoneType;
  }

  public void setTelephoneType(TelephoneType telephoneType) {
    this.telephoneType = telephoneType;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (telephoneId != null ? telephoneId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Telephone)) {
      return false;
    }
    Telephone other = (Telephone) object;
    if ((this.telephoneId == null && other.telephoneId != null) || (this.telephoneId != null && !this.telephoneId.equals(other.telephoneId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getTelephoneNumber();
  }

}
