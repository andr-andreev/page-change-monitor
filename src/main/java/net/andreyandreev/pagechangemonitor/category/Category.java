package net.andreyandreev.pagechangemonitor.category;

import net.andreyandreev.pagechangemonitor.model.NamedEntity;
import net.andreyandreev.pagechangemonitor.page.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "categories")
public class Category extends NamedEntity implements Serializable {
  @OneToMany(
      cascade = {CascadeType.PERSIST},
      mappedBy = "category")
  Collection<Page> pages;

  @PreRemove
  private void preRemove() {
    pages.forEach(page -> page.setCategory(null));
  }
}
