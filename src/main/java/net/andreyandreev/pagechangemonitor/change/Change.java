package net.andreyandreev.pagechangemonitor.change;

import net.andreyandreev.pagechangemonitor.model.BaseEntity;
import net.andreyandreev.pagechangemonitor.page.Page;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "changes")
public class Change extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "page_id")
	@NotNull
	private Page page;

	@Column(name = "diff")
	private String diff;

	@Column(name = "error_response")
	private String errorResponse;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
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

	public String getExtendedTitle() {
		ArrayList<String> parts = new ArrayList<>();

		if (this.page.getCategory() != null) {
			parts.add(this.page.getCategory().getName());
		}

		parts.add(this.page.getName() != null ? this.page.getName() : this.page.getUrl());

		return String.join(" / ", parts);
	}

	public String getTextContent() {
		if (getErrorResponse() != null) {
			return getErrorResponse();
		}

		return getDiff();
	}

}
