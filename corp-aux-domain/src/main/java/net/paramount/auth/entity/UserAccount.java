/*
\* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.paramount.auth.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.paramount.common.DateTimeUtility;
import net.paramount.common.ListUtility;
import net.paramount.domain.entity.Attachment;
import net.paramount.framework.entity.SsoEntityBase;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.global.GlobalConstants;
import net.paramount.model.DateTimePatterns;

/**
 * A user.
 * 
 * @author bqduc
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aux_user_account")
@ToString(exclude = { "privileges" })
@EqualsAndHashCode(callSuper = true)
public class UserAccount extends SsoEntityBase implements AuthenticationDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3124167777154600539L;

	@Size(max = 50)
	@Column(name = "first_name")
	private String firstName;

	@Size(max = 50)
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "company_name", length=120)
	private String companyName;

	@Column(name = "occupation_code", length=15)
	private String occupationCode;

	@Column(name = "phone_number", length=50)
	private String phoneNumber;

	@Column(name = "state_province", length=50)
	private String stateProvince;
	
	@Column(name = "country_code", length=12)
	private String countryCode;

	@Column(name = "business_unit_code", length=GlobalConstants.SIZE_SERIAL)
	private String businessUnitCode;

	@ManyToOne(targetEntity=Attachment.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "attachment_id")
	private Attachment attachment;

	@Transient
	private UserDetails userDetails;
	
	@Transient
	@Builder.Default
	private Map<String, Authority> authorityMap = new HashMap<>();

	@Builder.Default
  @OneToMany(mappedBy="userAccount", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
  private List<UserAccountPrivilege> privileges = ListUtility.createDataList();
	
	@Column(name = "info", columnDefinition="text")
	private String info;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Transient
	public String getDisplayName() {
		return this.firstName + " " + this.lastName;
	}

	public Map<String, Authority> getAuthorityMap() {
		return authorityMap;
	}

	public void setAuthorityMap(Map<String, Authority> authorityMap) {
		this.authorityMap = authorityMap;
	}

	@Transient
	public String getDisplayIssueDate() {
		return DateTimeUtility.dateToString(this.getIssueDate(), DateTimePatterns.ddMMyyyy_SLASH.getDateTimePattern());
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOccupationCode() {
		return occupationCode;
	}

	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBusinessUnitCode() {
		return businessUnitCode;
	}

	public void setBusinessUnitCode(String businessUnitCode) {
		this.businessUnitCode = businessUnitCode;
	}

	public UserAccount addPrivilege(UserAccountPrivilege userAccountPrivilege) {
		this.privileges.add(userAccountPrivilege);
		return this;
	}

	public UserAccount addPrivilege(Authority authority) {
		this.privileges.add(UserAccountPrivilege.builder()
				.authority(authority)
				.userAccount(this)
				.build());
		return this;
	}

	public List<UserAccountPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<UserAccountPrivilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = ListUtility.createHashSet();
		for (UserAccountPrivilege userAccountPrivilege :this.privileges) {
			authorities.add(userAccountPrivilege.getAuthority());
		}
		return authorities;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}