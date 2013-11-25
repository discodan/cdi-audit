package com.test.audit;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import java.io.Serializable;

@SessionScoped
@Named
public class UserController implements Serializable {

	private static final long serialVersionUID = 4242997770624620926L;

	private String username;

	@Produces
	@Named
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String login() {

		return "customers";
	}
}
