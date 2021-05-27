package org.js.azdanov.springfresh.models;

import java.text.MessageFormat;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Hibernate;
import org.js.azdanov.springfresh.security.RoleAuthority;

@Table(name = "roles")
@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 50, unique = true, nullable = false)
  private RoleAuthority role;

  @ManyToMany(mappedBy = "roles")
  @Column(nullable = false)
  private Collection<User> users;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public RoleAuthority getRole() {
    return role;
  }

  public void setRole(RoleAuthority role) {
    this.role = role;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public void setUsers(Collection<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Role role = (Role) o;

    return id != null && id.equals(role.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return MessageFormat.format("{0}(id = {1}, role = {2})", getClass().getSimpleName(), id, role);
  }
}
