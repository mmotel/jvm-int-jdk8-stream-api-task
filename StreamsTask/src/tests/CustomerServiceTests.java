package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;

public class CustomerServiceTests {

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
	

}
