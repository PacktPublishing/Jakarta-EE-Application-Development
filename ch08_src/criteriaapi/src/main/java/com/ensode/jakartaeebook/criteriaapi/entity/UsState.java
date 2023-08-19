package com.ensode.jakartaeebook.criteriaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "US_STATES")
public class UsState implements Serializable {

  @Column(name = "US_STATE_ID")
  @Id
  private Long usStateId;
  @Column(name = "US_STATE_CD")
  private String usStateCd;
  @Column(name = "US_STATE_NM")
  private String usStateNm;

  public String getUsStateCd() {
    return usStateCd;
  }

  public void setUsStateCd(String usStateCd) {
    this.usStateCd = usStateCd;
  }

  public Long getUsStateId() {
    return usStateId;
  }

  public void setUsStateId(Long usStateId) {
    this.usStateId = usStateId;
  }

  public String getUsStateNm() {
    return usStateNm;
  }

  public void setUsStateNm(String usStateNm) {
    this.usStateNm = usStateNm;
  }
}
