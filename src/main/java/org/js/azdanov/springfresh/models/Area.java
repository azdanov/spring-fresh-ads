package org.js.azdanov.springfresh.models;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.exsio.nestedj.model.NestedNode;

@Table(name = "areas")
@Entity
public class Area implements NestedNode<Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String slug;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private Integer parentId;

  @Column(nullable = false)
  private Long treeLeft;

  @Column(nullable = false)
  private Long treeRight;

  @Column(nullable = false)
  private Long treeLevel;

  private String discriminator;

  public Area() {}

  public Area(String name, String slug) {
    this();
    this.name = name;
    this.slug = slug;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public Long getTreeLeft() {
    return treeLeft;
  }

  @Override
  public void setTreeLeft(Long treeLeft) {
    this.treeLeft = treeLeft;
  }

  @Override
  public Long getTreeRight() {
    return treeRight;
  }

  @Override
  public void setTreeRight(Long treeRight) {
    this.treeRight = treeRight;
  }

  @Override
  public Long getTreeLevel() {
    return treeLevel;
  }

  @Override
  public void setTreeLevel(Long treeLevel) {
    this.treeLevel = treeLevel;
  }

  @Override
  public Integer getParentId() {
    return parentId;
  }

  @Override
  public void setParentId(Integer parent) {
    this.parentId = parent;
  }

  public boolean isRootNode() {
    return this.getParentId() == null;
  }

  public String getDiscriminator() {
    return discriminator;
  }

  public void setDiscriminator(String discriminator) {
    this.discriminator = discriminator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Area area = (Area) o;

    return id != null && id.equals(area.id);
  }

  @Override
  public int hashCode() {
    return 1179343604;
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
