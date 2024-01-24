
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
@Table(name = "TELEPHONE_TYPES")
@NamedQueries({
  @NamedQuery(name = "TelephoneType.findAll", query = "SELECT t FROM TelephoneType t"),
  @NamedQuery(name = "TelephoneType.findByTelephoneTypeId", query = "SELECT t FROM TelephoneType t WHERE t.telephoneTypeId = :telephoneTypeId"),
  @NamedQuery(name = "TelephoneType.findByTelephoneTypeCd", query = "SELECT t FROM TelephoneType t WHERE t.telephoneTypeCd = :telephoneTypeCd"),
  @NamedQuery(name = "TelephoneType.findByTelephoneTypeText", query = "SELECT t FROM TelephoneType t WHERE t.telephoneTypeText = :telephoneTypeText")})
public class TelephoneType implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "TELEPHONE_TYPE_ID")
  private Integer telephoneTypeId;
  @Column(name = "TELEPHONE_TYPE_CD")
  private Character telephoneTypeCd;
  @Column(name = "TELEPHONE_TYPE_TEXT")
  private String telephoneTypeText;
  @OneToMany(mappedBy = "telephoneTypeId")
  private List<Telephone> telephoneList;

  public TelephoneType() {
  }

  public TelephoneType(Integer telephoneTypeId) {
    this.telephoneTypeId = telephoneTypeId;
  }

  public Integer getTelephoneTypeId() {
    return telephoneTypeId;
  }

  public void setTelephoneTypeId(Integer telephoneTypeId) {
    this.telephoneTypeId = telephoneTypeId;
  }

  public Character getTelephoneTypeCd() {
    return telephoneTypeCd;
  }

  public void setTelephoneTypeCd(Character telephoneTypeCd) {
    this.telephoneTypeCd = telephoneTypeCd;
  }

  public String getTelephoneTypeText() {
    return telephoneTypeText;
  }

  public void setTelephoneTypeText(String telephoneTypeText) {
    this.telephoneTypeText = telephoneTypeText;
  }

  public List<Telephone> getTelephoneList() {
    return telephoneList;
  }

  public void setTelephoneList(List<Telephone> telephoneList) {
    this.telephoneList = telephoneList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (telephoneTypeId != null ? telephoneTypeId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TelephoneType)) {
      return false;
    }
    TelephoneType other = (TelephoneType) object;
    if ((this.telephoneTypeId == null && other.telephoneTypeId != null) || (this.telephoneTypeId != null && !this.telephoneTypeId.equals(other.telephoneTypeId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getTelephoneTypeText();
  }

}
