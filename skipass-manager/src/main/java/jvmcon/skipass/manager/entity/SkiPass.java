package jvmcon.skipass.manager.entity;


import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SkiPass {
  @Id
  private String id;
  private LocalDateTime validFrom;
  private LocalDateTime validTo;
  private boolean blocked;

  public SkiPass(LocalDateTime validFrom, LocalDateTime validTo) {
    this.validFrom = validFrom;
    this.validTo = validTo;

    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return this.id;
  }

  public LocalDateTime getValidFrom() {
    return this.validFrom;
  }

  public LocalDateTime getValidTo() {
    return this.validTo;
  }

  public boolean isBlocked() {
    return this.blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public boolean isValid() {
    LocalDateTime now = LocalDateTime.now();
    return !this.blocked
        && !now.isBefore(this.validFrom)
        && now.isBefore(this.validTo);
  }

  protected SkiPass() {
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SkiPass other = (SkiPass) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SkiPass [id=" + this.id + ", validFrom=" + this.validFrom + ", validTo=" + this.validTo + ", blocked=" + this.blocked + "]";
  }

}
