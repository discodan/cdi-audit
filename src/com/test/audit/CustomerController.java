package com.test.audit;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.test.audit.entity.Customer;

@SessionScoped
@Named("customerController")
public class CustomerController implements Serializable {

	private static final long serialVersionUID = -6762408715002674143L;

	@PersistenceUnit(name = "customerDB")
	private EntityManager em;
	private List<Customer> customerList;
	private Customer customer;
	private Mode editMode;

	@PostConstruct
	public void refresh() {

		customerList = em.createQuery("from Customer", Customer.class).getResultList();
	}

	public List<Customer> getList() {

		return customerList;
	}

	public Customer getCustomer() {

		return customer;
	}

	public boolean isEditing() {

		return editMode != null;
	}

	public void startAdd() {

		customer = new Customer();
		editMode = Mode.ADD;
	}

	public void startEdit() {

		editMode = Mode.EDIT;
	}

	public void finishEdit() {

		editMode = null;
	}

	public void cancelEdit() {

		editMode = null;
	}
	
	public void delete() {
		
	}

	private static enum Mode {

		ADD, EDIT
	}
}
