
package net.paramount.auth.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.paramount.framework.entity.ObjectBase;

/**
 * 
 * @author ducbq
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "aux_authority")
public class Authority extends ObjectBase implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 64, message = "{LongString}")
	@Column(name = "name")
	private String name;

	@NotNull
	@Size(min = 1, max = 150)
	@Column(name = "display_name")
	private String displayName;

	@Builder.Default
	@Basic(optional = false)
	@NotNull
	@Column(name = "active")
	private Boolean active = Boolean.FALSE;

	@Column(name = "info")
	private String info;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Authority parent;

	@Column(name = "category_id")
	private Long categoryId;
	
	@Builder.Default
	@Column(name = "is_administration")
	private Boolean isAdministration = Boolean.FALSE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Authority [ id=" + getId() + " ]";
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Authority getParent() {
		return parent;
	}

	public void setParent(Authority parent) {
		this.parent = parent;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Boolean getIsAdministration() {
		return isAdministration;
	}

	public void setIsAdministration(Boolean isAdministration) {
		this.isAdministration = isAdministration;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}
