package com.test.audit.entity.audit;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.hibernate.envers.RevisionListener;

@ApplicationScoped
public class CustomRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {

		newRevision((CustomRevisionEntity) revisionEntity);
	}

	private void newRevision(CustomRevisionEntity revisionEntity) {

		revisionEntity.setApplication("Customers_App");
		revisionEntity.setUsername(getUsername());
	}

	private String getUsername() {

		String username = (String) BeanProvider
				.getContextualReference("username");
		return username;
	}
}
