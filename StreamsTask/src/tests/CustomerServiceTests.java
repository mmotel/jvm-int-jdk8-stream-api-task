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
	
	

}
