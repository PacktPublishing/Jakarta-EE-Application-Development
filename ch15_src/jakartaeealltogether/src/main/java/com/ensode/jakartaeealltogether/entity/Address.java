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
@Table(name = "ADDRESSES")
@NamedQueries({
  @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
  @NamedQuery(name = "Address.findByAddressId", query = "SELECT a FROM Address a WHERE a.addressId = :addressId"),
  @NamedQuery(name = "Address.findByAddrLine1", query = "SELECT a FROM Address a WHERE a.addrLine1 = :addrLine1"),
  @NamedQuery(name = "Address.findByAddrLine2", query = "SELECT a FROM Address a WHERE a.addrLine2 = :addrLine2"),
  @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
  @NamedQuery(name = "Address.findByZip", query = "SELECT a FROM Address a WHERE a.zip = :zip")})
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ADDRESS_ID")
  private Integer addressId;
  @Column(name = "ADDR_LINE_1")
  private String addrLine1;
  @Column(name = "ADDR_LINE_2")
  private String addrLine2;
  @Column(name = "CITY")
  private String city;
  @Column(name = "ZIP")
  private String zip;
  @JoinColumn(name = "ADDRESS_TYPE_ID", referencedColumnName = "ADDRESS_TYPE_ID")
  @ManyToOne
  private AddressType addressType;
  @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
  @ManyToOne
  private Customer customer;
  @JoinColumn(name = "US_STATE_ID", referencedColumnName = "US_STATE_ID")
  @ManyToOne
  private UsState usState;

  public Address() {
  }

  public Address(Integer addressId) {
    this.addressId = addressId;
  }

  public Integer getAddressId() {
    return addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public String getAddrLine1() {
    return addrLine1;
  }

  public void setAddrLine1(String addrLine1) {
    this.addrLine1 = addrLine1;
  }

  public String getAddrLine2() {
    return addrLine2;
  }

  public void setAddrLine2(String addrLine2) {
    this.addrLine2 = addrLine2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public UsState getUsState() {
    return usState;
  }

  public void setUsState(UsState usState) {
    this.usState = usState;
  }

 

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (addressId != null ? addressId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Address)) {
      return false;
    }
    Address other = (Address) object;
    if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ensode.jakartaeealltogether.Address[ addressId=" + addressId + " ]";
  }

}
