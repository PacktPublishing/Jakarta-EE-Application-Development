package com.ensode.jakartaeealltogether.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ADDRESS_TYPES")
@NamedQueries({
  @NamedQuery(name = "AddressType.findAll", query = "SELECT a FROM AddressType a"),
  @NamedQuery(name = "AddressType.findByAddressTypeId", query = "SELECT a FROM AddressType a WHERE a.addressTypeId = :addressTypeId"),
  @NamedQuery(name = "AddressType.findByAddressTypeCode", query = "SELECT a FROM AddressType a WHERE a.addressTypeCode = :addressTypeCode"),
  @NamedQuery(name = "AddressType.findByAddressTypeText", query = "SELECT a FROM AddressType a WHERE a.addressTypeText = :addressTypeText")})
public class AddressType implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "ADDRESS_TYPE_ID")
  private Integer addressTypeId;
  @Column(name = "ADDRESS_TYPE_CODE")
  private Character addressTypeCode;
  @Column(name = "ADDRESS_TYPE_TEXT")
  private String addressTypeText;
  @OneToMany(mappedBy = "addressType")
  private List<Address> addressList;

  public AddressType() {
  }

  public AddressType(Integer addressTypeId) {
    this.addressTypeId = addressTypeId;
  }

  public Integer getAddressTypeId() {
    return addressTypeId;
  }

  public void setAddressTypeId(Integer addressTypeId) {
    this.addressTypeId = addressTypeId;
  }

  public Character getAddressTypeCode() {
    return addressTypeCode;
  }

  public void setAddressTypeCode(Character addressTypeCode) {
    this.addressTypeCode = addressTypeCode;
  }

  public String getAddressTypeText() {
    return addressTypeText;
  }

  public void setAddressTypeText(String addressTypeText) {
    this.addressTypeText = addressTypeText;
  }

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (addressTypeId != null ? addressTypeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof AddressType)) {
      return false;
    }
    AddressType other = (AddressType) object;
    if ((this.addressTypeId == null && other.addressTypeId != null) || (this.addressTypeId != null && !this.addressTypeId.equals(other.addressTypeId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return addressTypeText;
  }

}
