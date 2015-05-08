package services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

	private List<Customer> customers;

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public List<Customer> findByName(String name) {
		return customers.stream()
				.filter((c) -> c.getName().compareTo(name) == 0)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findByField(String fieldName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> customersWhoBoughtMoreThan(int number) {
		return customers.stream()
				.filter((c) -> c.getBoughtProducts().size() > number)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoSpentMoreThan(double price) {
		return customers.stream()
				.filter((c) -> c.getBoughtProducts().stream().
						mapToDouble(Product::getPrice).sum() > price )
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWithNoOrders() {
		return customers.stream()
			.filter((c) -> c.getBoughtProducts().isEmpty())
			.collect(Collectors.toList());
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		customers.stream().forEach((c) -> c.addProduct(p));
	}

	@Override
	public double avgOrders(boolean includeEmpty) {
		Stream<Customer> customerz = customers.stream();
		if(!includeEmpty){
			customerz = customerz.filter((c) -> !c.getBoughtProducts().isEmpty());
		}
		
		try {
			return customerz
					.mapToInt((c) -> c.getBoughtProducts().size())
					.average().getAsDouble();
		}
		catch(NoSuchElementException e){
			return 0;
		}
		
	}

	@Override
	public boolean wasProductBought(Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> mostPopularProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countBuys(Product p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
