package net.andreyandreev.pagechangemonitor.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

  @Column(name = "name")
  @Size(min = 3, max = 255)
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
