package com.test.audit.entity.audit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "revision", schema = "audit")
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity implements Serializable {

	private static final long serialVersionUID = 8155784545925519340L;

	@Id
	@GeneratedValue
	@RevisionNumber
	private Integer id;
	@RevisionTimestamp
	@Column(name = "revision_time")
	private Long revisionTime;
	private String username;
	private String application;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getRevisionTime() {
		return revisionTime;
	}

	public void setRevisionTime(Long revisionTime) {
		this.revisionTime = revisionTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}
