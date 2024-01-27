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
@Table(name = "US_STATES")
@NamedQueries({
  @NamedQuery(name = "UsState.findAll", query = "SELECT u FROM UsState u"),
  @NamedQuery(name = "UsState.findByUsStateId", query = "SELECT u FROM UsState u WHERE u.usStateId = :usStateId"),
  @NamedQuery(name = "UsState.findByUsStateCd", query = "SELECT u FROM UsState u WHERE u.usStateCd = :usStateCd"),
  @NamedQuery(name = "UsState.findByUsStateNm", query = "SELECT u FROM UsState u WHERE u.usStateNm = :usStateNm")})
public class UsState implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "US_STATE_ID")
  private Integer usStateId;
  @Basic(optional = false)
  @Column(name = "US_STATE_CD")
  private String usStateCd;
  @Basic(optional = false)
  @Column(name = "US_STATE_NM")
  private String usStateNm;
  @OneToMany(mappedBy = "usState")
  private List<Address> addressList;

  public UsState() {
  }

  public UsState(Integer usStateId) {
    this.usStateId = usStateId;
  }

  public UsState(Integer usStateId, String usStateCd, String usStateNm) {
    this.usStateId = usStateId;
    this.usStateCd = usStateCd;
    this.usStateNm = usStateNm;
  }

  public Integer getUsStateId() {
    return usStateId;
  }

  public void setUsStateId(Integer usStateId) {
    this.usStateId = usStateId;
  }

  public String getUsStateCd() {
    return usStateCd;
  }

  public void setUsStateCd(String usStateCd) {
    this.usStateCd = usStateCd;
  }

  public String getUsStateNm() {
    return usStateNm;
  }

  public void setUsStateNm(String usStateNm) {
    this.usStateNm = usStateNm;
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
    hash += (usStateId != null ? usStateId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsState)) {
      return false;
    }
    UsState other = (UsState) object;
    if ((this.usStateId == null && other.usStateId != null) || (this.usStateId != null && !this.usStateId.equals(other.usStateId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return usStateNm;
  }

}
