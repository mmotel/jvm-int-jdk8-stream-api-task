package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;
import entities.Product;

public class CustomerServiceTests {
	private static final double DELTA = 1e-15;

	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		String name = "Customer: 1";
		String name2 = "Customer: 11";
		
		List<Customer> res = cs.findByName(name);
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
		assertEquals(name, res.get(0).getName());
		
		List<Customer> res2 = cs.findByName(name2);
		
		assertNotNull("Result can't be null", res2);
		assertEquals(0, res2.size());
		
	}

	@Test
	public void testCustomersWhoBoughtMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		int number = 1;
		int number2 = 10;
		
		List<Customer> res = cs.customersWhoBoughtMoreThan(number);
		
		assertNotNull("Result can't be null", res);
		assertEquals(6, res.size());
		
		List<Customer> res2 = cs.customersWhoBoughtMoreThan(number2);
		
		assertNotNull("Result can't be null", res2);
		assertEquals(0, res2.size());
		
	}
	
	@Test
	public void testCustomersWhoSpentMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		int number = 1;
		int number2 = 5;
		
		List<Customer> res = cs.customersWhoSpentMoreThan(number);
		
		assertNotNull("Result can't be null", res);
		assertEquals(5, res.size());
		
		List<Customer> res2 = cs.customersWhoSpentMoreThan(number2);
		
		assertNotNull("Result can't be null", res2);
		assertEquals(0, res2.size());
		
	}
	
	@Test
	public void testCustomersWithNoOrders() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWithNoOrders();
		
		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
		
	}
	
	@Test
	public void testAddProductToAllCustomers() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestDataWithNoProducts(10));
		
		Product p = new Product(101, "Some Product", 101);
		
		cs.addProductToAllCustomers(p);
		
		List<Customer> customers = cs.customersWithNoOrders();
		
		assertNotNull("Result can't be null", customers);
		assertEquals(0, customers.size());
		
	}
	
	@Test
	public void testAvgOrders() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		Double res = cs.avgOrders(true);
		
		assertNotNull("Result can't be null", res);
		assertEquals(2.8, res, DELTA);
		
		Double res2 = cs.avgOrders(false);
		
		assertNotNull("Result can't be null", res2);
		assertEquals(4, res2, DELTA);
		
		CustomerServiceInterface cs2 = new CustomerService(DataProducer.getTestDataWithNoProducts(10));

		Double res3 = cs2.avgOrders(true);
		
		assertNotNull("Result can't be null", res3);
		assertEquals(0, res3, DELTA);
		
		Double res4 = cs2.avgOrders(false);
		
		assertNotNull("Result can't be null", res4);
		assertEquals(0, res4, DELTA);
		
	}
	
	@Test
	public void testWasProductBought() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		Product p = new Product(4, "Product: 4", 0.4);
		
		boolean res = cs.wasProductBought(p);
		
		assertNotNull("Result can't be null", res);
		assertEquals(true, res);
		
		Product p2 = new Product(11, "Product: 11", 1.1);
		
		boolean res2 = cs.wasProductBought(p2);
		
		assertNotNull("Result can't be null", res2);
		assertEquals(false, res2);
		
	}
	

}
