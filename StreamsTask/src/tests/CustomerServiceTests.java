package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
	public void testFindByField(){
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		Customer c = new Customer(1, "Customer: " + 1);
		
		List<Customer> foundCustomers = cs.findByField("id", c.getId());
		
		assertEquals(1, foundCustomers.size());
		assertEquals(c.getId(), foundCustomers.get(0).getId());
		
		foundCustomers = cs.findByField("name", c.getName());
		
		assertEquals(1, foundCustomers.size());
		assertEquals(c.getId(), foundCustomers.get(0).getId());
		assertEquals(c.getName(), foundCustomers.get(0).getName());
		
		foundCustomers = cs.findByField("email", c.getEmail());
		
		assertEquals(1, foundCustomers.size());
		assertEquals(c.getId(), foundCustomers.get(0).getId());
		assertEquals(c.getEmail(), foundCustomers.get(0).getEmail());

		foundCustomers = cs.findByField("phoneNo", c.getPhoneNo());
		
		assertEquals(1, foundCustomers.size());
		assertEquals(c.getId(), foundCustomers.get(0).getId());
		assertEquals(c.getPhoneNo(), foundCustomers.get(0).getPhoneNo());
		
		foundCustomers = cs.findByField("taxId", c.getTaxId());
		
		assertEquals(1, foundCustomers.size());
		assertEquals(c.getId(), foundCustomers.get(0).getId());
		assertEquals(c.getTaxId(), foundCustomers.get(0).getTaxId());		
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
	
	@Test
	public void testMostPopularProduct() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestDataWithProducts(10));
		
		Product p  = new Product(20, "Product: 20", 2);
		Product p2 = new Product(22, "Product: 22", 2.2);
		
		for(int i = 0; i < 5; i+=1){
			cs.addProductToAllCustomers(p);
		}
		List<Product> products = new ArrayList<Product>();
		products.add(p);
		List<Product> mostPopularProduct = cs.mostPopularProduct();
		
		assertEquals(1, mostPopularProduct.size());
		assertTrue(mostPopularProduct.containsAll(products));
		
		for(int i = 0; i < 5; i+=1){
			cs.addProductToAllCustomers(p2);
		}
		products.add(p2);
		List<Product> mostPopularProduct2 = cs.mostPopularProduct();
		
		assertEquals(2, mostPopularProduct2.size());
		assertTrue(mostPopularProduct2.containsAll(products));
		
	}
	
	@Test
	public void testCountBuys() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestDataWithNoProducts(10));
		
		Product p  = new Product(20, "Product: 20", 2);
		Product p2 = new Product(22, "Product: 22", 2.2);
		
		cs.addProductToAllCustomers(p);
		
		int count = cs.countBuys(p);
		int count2 = cs.countBuys(p2);
		
		assertEquals(10, count);
		assertEquals(0, count2);
		
	}
	
	@Test
	public void testCountCustomersWhoBought() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestDataWithNoProducts(10));
		
		Product p  = new Product(20, "Product: 20", 2);
		Product p2 = new Product(22, "Product: 22", 2.2);
		
		cs.addProductToAllCustomers(p);
		
		int count = cs.countBuys(p);
		int count2 = cs.countBuys(p2);
		
		assertEquals(10, count);
		assertEquals(0, count2);
	}

}
