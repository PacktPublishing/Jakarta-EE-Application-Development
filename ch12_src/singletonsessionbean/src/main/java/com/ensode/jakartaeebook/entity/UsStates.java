package com.ensode.jakartaeebook.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "US_STATES")
@NamedQueries({
  @NamedQuery(name = "UsStates.findAll", query = "SELECT u FROM UsStates u"),
  @NamedQuery(name = "UsStates.findByUsStateId", query = "SELECT u FROM UsStates u WHERE u.usStateId = :usStateId"),
  @NamedQuery(name = "UsStates.findByUsStateCd", query = "SELECT u FROM UsStates u WHERE u.usStateCd = :usStateCd"),
  @NamedQuery(name = "UsStates.findByUsStateNm", query = "SELECT u FROM UsStates u WHERE u.usStateNm = :usStateNm")})
public class UsStates implements Serializable {

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

  public UsStates() {
  }

  public UsStates(Integer usStateId) {
    this.usStateId = usStateId;
  }

  public UsStates(Integer usStateId, String usStateCd, String usStateNm) {
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (usStateId != null ? usStateId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsStates)) {
      return false;
    }
    UsStates other = (UsStates) object;
    if ((this.usStateId == null && other.usStateId != null) || (this.usStateId != null && !this.usStateId.equals(other.usStateId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "net.ensode.glassfishbook.entity.UsStates[usStateId=" + usStateId + "]";
  }

}
