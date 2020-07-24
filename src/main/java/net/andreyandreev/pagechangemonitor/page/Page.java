package net.andreyandreev.pagechangemonitor.page;

import net.andreyandreev.pagechangemonitor.category.Category;
import net.andreyandreev.pagechangemonitor.change.Change;
import net.andreyandreev.pagechangemonitor.model.NamedEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "pages")
public class Page extends NamedEntity {

	@OneToMany(mappedBy = "page")
	@OrderBy(clause = "created_at DESC")
	Collection<Change> changes;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "url")
	@NotEmpty
	@URL
	private String url;

	@Column(name = "filter_from")
	private String filterFrom;

	@Column(name = "filter_to")
	private String filterTo;

	@Column(name = "last_content")
	private String lastContent;

	@Column(name = "is_active", columnDefinition = "BIT")
	@NotNull
	private Boolean isActive;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilterFrom() {
		return filterFrom;
	}

	public void setFilterFrom(String filterFrom) {
		this.filterFrom = filterFrom;
	}

	public String getFilterTo() {
		return filterTo;
	}

	public void setFilterTo(String filterTo) {
		this.filterTo = filterTo;
	}

	public String getLastContent() {
		return lastContent;
	}

	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Collection<Change> getChanges() {
		return changes;
	}

	public void setChanges(Collection<Change> changes) {
		this.changes = changes;
	}

	public Boolean hasFilter() {
		return this.getFilterFrom().length() > 0 && this.getFilterTo().length() > 0;
	}

}
