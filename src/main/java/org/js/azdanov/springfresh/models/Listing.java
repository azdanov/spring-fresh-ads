package org.js.azdanov.springfresh.models;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Table(name = "listings")
@Entity
@SQLDelete(sql = "UPDATE listings SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class Listing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Area area;

  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  private String title;

  private String body;

  private boolean live = Boolean.FALSE;

  private boolean deleted = Boolean.FALSE;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Listing listing = (Listing) o;

    return id != null && id.equals(listing.id);
  }

  @Override
  public int hashCode() {
    return 811658735;
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "{0}(id = {1}, title = {2}, body = {3}, live = {4}, deleted = {5},"
            + " createdAt = {6}, updatedAt = {7})",
        getClass().getSimpleName(), id, title, body, live, deleted, createdAt, updatedAt);
  }
}
