package org.js.azdanov.springfresh.models;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.js.azdanov.springfresh.models.listeners.CategoryListener;
import pl.exsio.nestedj.model.NestedNode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(CategoryListener.class)
@Table(
    name = "categories",
    indexes = {@Index(columnList = "slug", unique = true)})
public class Category implements NestedNode<Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String slug;

  @Column(nullable = false)
  private BigDecimal price = BigDecimal.ZERO;

  private boolean usable;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Listing> listings = new ArrayList<>();

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  private Integer parentId;

  @Column(nullable = false)
  private Long treeLeft;

  @Column(nullable = false)
  private Long treeRight;

  @Column(nullable = false)
  private Long treeLevel;

  public Category(String name) {
    this.name = name;
  }

  public void addListing(Listing listing) {
    listings.add(listing);
    listing.setCategory(this);
  }

  public void removeListing(Listing listing) {
    listings.remove(listing);
    listing.setCategory(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Category category = (Category) o;

    return id != null && id.equals(category.id);
  }

  @Override
  public int hashCode() {
    return 1596826009;
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "{0}(id = {1}, name = {2}, slug = {3}, createdAt = {4}, updatedAt = {5},"
            + " parentId = {6}, treeLeft = {7}, treeRight = {8}, treeLevel = {9})",
        getClass().getSimpleName(),
        id,
        name,
        slug,
        createdAt,
        updatedAt,
        parentId,
        treeLeft,
        treeRight,
        treeLevel);
  }
}
