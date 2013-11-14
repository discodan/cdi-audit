package com.test.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;

import com.test.audit.entity.Customer;

@SessionScoped
@Stateful
@LocalBean
@Named("customerController")
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerController implements Serializable {

	private static final long serialVersionUID = -6762408715002674143L;

	@PersistenceContext(name = "customerDB", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	@Inject
	private UserTransaction txn;
	private List<Customer> customerList;
	private List<Customer> historyList = new ArrayList<>();
	private Customer customer;
	private Mode mode;

	@PostConstruct
	public void refresh() {

		em.clear();
		try {
			txn.begin();
			customerList = em.createQuery("from Customer", Customer.class)
					.getResultList();
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Customer> getCustomerList() {

		return customerList;
	}

	public List<Customer> getHistoryList() {

		return historyList;
	}

	public Customer getCustomer() {

		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isEditing() {

		return mode == Mode.ADD || mode == Mode.EDIT;
	}
	
	public boolean isHistory() {
		
		return mode == Mode.HISTORY;
	}

	public void startAdd() {

		customer = new Customer();
		mode = Mode.ADD;
		beginTxn();
	}

	public void startEdit() {

		beginTxn();
		mode = Mode.EDIT;
	}

	public void showHistory() {

		mode = Mode.HISTORY;
	}
	
	public void hideHistory() {
		
		historyList.clear();
		mode = null;
	}

	public void finishEdit() {

		// Save
		if (mode == Mode.ADD) {
			em.persist(customer);
		}
		commitTxn();
		cleanup();
	}

	public void cancelEdit() {

		rollbackTxn();
		cleanup();
	}

	public void delete() {

		// Delete
		beginTxn();
		em.remove(customer);
		commitTxn();
		cleanup();
	}

	private void cleanup() {

		customer = null;
		mode = null;
		refresh();
	}

	private void beginTxn() {

		try {
			txn.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void commitTxn() {

		try {
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rollbackTxn() {

		try {
			txn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static enum Mode {

		ADD, EDIT, HISTORY
	}
}
