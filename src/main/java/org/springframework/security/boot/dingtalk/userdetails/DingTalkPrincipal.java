/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.boot.dingtalk.userdetails;

import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * TODO
 * 
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@SuppressWarnings("serial")
/**
 * <p>DingTalkPrincipal implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkPrincipal extends SecurityPrincipal {

	/**
	 * employeeinenterprise uniqueidentifier，staffId。enterprisein，，
	 */
	protected String userid;
	/**
	 * employeeinenterprise uniqueidentifier，，，
	 */
	protected String unionid;
	/**
	 * employee
	 */
	protected String name;
	/**
	 * （enterpriseinternal）
	 */
	protected String tel;
	/**
	 * 
	 */
	protected String workPlace;
	/**
	 * 
	 */
	protected String remark;
	/**
	 * 
	 */
	protected String mobile;
	/**
	 * employee
	 */
	protected String email;
	/**
	 * employee enterprise，employeeenterprise，，
	 */
	protected String orgEmail;
	/**
	 * ，true，false
	 */
	protected String active;
	/**
	 * in ，Map json，key Id，valuein
	 */
	protected String orderInDepts;
	/**
	 * enterprise management，true，false
	 */
	protected boolean admin;
	/**
	 * enterprise ，true，false
	 */
	protected boolean boss;
	/**
	 * in ：Map json，key Id，valuein，true，false
	 */
	protected boolean leaderInDepts;
	/**
	 * ，true，false
	 */
	protected boolean hide;
	/**
	 * id
	 */
	protected String department;
	/**
	 * information
	 */
	protected String position;
	/**
	 * url
	 */
	protected String avatar;
	/**
	 * 。Unix （inOAbackend employeeinformation)
	 */
	protected String hiredDate;
	/**
	 * employee
	 */
	protected String jobnumber;
	/**
	 * properties，properties（10properties，properties，OAmanagementbackend-&gt;-&gt;informationandOAmanagementbackend-&gt;-&gt;information）
	 */
	protected String extattr;
	/**
	 * 
	 */
	protected boolean senior;
	/**
	 * 
	 */
	protected String stateCode;

	public DingTalkPrincipal(String username, String password, String... roles) {
		super(username, password, roles);
	}

	public DingTalkPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public DingTalkPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	/** Returns the userid.
	 * @return the result
	 */
	public String getUserid() {
		return userid;
	}

	/** Returns the unionid.
	 * @return the result
	 */
	public String getUnionid() {
		return unionid;
	}

	/** Returns the name.
	 * @return the result
	 */
	public String getName() {
		return name;
	}

	/** Returns the tel.
	 * @return the result
	 */
	public String getTel() {
		return tel;
	}

	/** Returns the work place.
	 * @return the result
	 */
	public String getWorkPlace() {
		return workPlace;
	}

	/** Returns the remark.
	 * @return the result
	 */
	public String getRemark() {
		return remark;
	}

	/** Returns the mobile.
	 * @return the result
	 */
	public String getMobile() {
		return mobile;
	}

	/** Returns the email.
	 * @return the result
	 */
	public String getEmail() {
		return email;
	}

	/** Returns the org email.
	 * @return the result
	 */
	public String getOrgEmail() {
		return orgEmail;
	}

	/** Returns the active.
	 * @return the result
	 */
	public String getActive() {
		return active;
	}

	/** Returns the order in depts.
	 * @return the result
	 */
	public String getOrderInDepts() {
		return orderInDepts;
	}

	/** Returns whether the admin is enabled.
	 * @return the result
	 */
	public boolean isAdmin() {
		return admin;
	}

	/** Returns whether the boss is enabled.
	 * @return the result
	 */
	public boolean isBoss() {
		return boss;
	}

	/** Returns whether the leader in depts is enabled.
	 * @return the result
	 */
	public boolean isLeaderInDepts() {
		return leaderInDepts;
	}

	/** Returns whether the hide is enabled.
	 * @return the result
	 */
	public boolean isHide() {
		return hide;
	}

	/** Returns the department.
	 * @return the result
	 */
	public String getDepartment() {
		return department;
	}

	/** Returns the position.
	 * @return the result
	 */
	public String getPosition() {
		return position;
	}

	/** Returns the avatar.
	 * @return the result
	 */
	public String getAvatar() {
		return avatar;
	}

	/** Returns the hired date.
	 * @return the result
	 */
	public String getHiredDate() {
		return hiredDate;
	}

	/** Returns the jobnumber.
	 * @return the result
	 */
	public String getJobnumber() {
		return jobnumber;
	}

	/** Returns the extattr.
	 * @return the result
	 */
	public String getExtattr() {
		return extattr;
	}

	/** Returns whether the senior is enabled.
	 * @return the result
	 */
	public boolean isSenior() {
		return senior;
	}

	/** Returns the state code.
	 * @return the result
	 */
	public String getStateCode() {
		return stateCode;
	}

	/** Sets the userid.
	 * @param userid the userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/** Sets the unionid.
	 * @param unionid the unionid
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/** Sets the name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Sets the tel.
	 * @param tel the tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/** Sets the work place.
	 * @param workPlace the workPlace
	 */
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	/** Sets the remark.
	 * @param remark the remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** Sets the mobile.
	 * @param mobile the mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** Sets the email.
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Sets the org email.
	 * @param orgEmail the orgEmail
	 */
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	/** Sets the active.
	 * @param active the active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/** Sets the order in depts.
	 * @param orderInDepts the orderInDepts
	 */
	public void setOrderInDepts(String orderInDepts) {
		this.orderInDepts = orderInDepts;
	}

	/** Sets the admin.
	 * @param admin the admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/** Sets the boss.
	 * @param boss the boss
	 */
	public void setBoss(boolean boss) {
		this.boss = boss;
	}

	/** Sets the leader in depts.
	 * @param leaderInDepts the leaderInDepts
	 */
	public void setLeaderInDepts(boolean leaderInDepts) {
		this.leaderInDepts = leaderInDepts;
	}

	/** Sets the hide.
	 * @param hide the hide
	 */
	public void setHide(boolean hide) {
		this.hide = hide;
	}

	/** Sets the department.
	 * @param department the department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/** Sets the position.
	 * @param position the position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/** Sets the avatar.
	 * @param avatar the avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/** Sets the hired date.
	 * @param hiredDate the hiredDate
	 */
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
	}

	/** Sets the jobnumber.
	 * @param jobnumber the jobnumber
	 */
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	/** Sets the extattr.
	 * @param extattr the extattr
	 */
	public void setExtattr(String extattr) {
		this.extattr = extattr;
	}

	/** Sets the senior.
	 * @param senior the senior
	 */
	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	/** Sets the state code.
	 * @param stateCode the stateCode
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

}
