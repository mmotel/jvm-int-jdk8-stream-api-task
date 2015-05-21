package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
		List<Product> products = new ArrayList<Product>();
		customers.stream().map(Customer::getBoughtProducts).
			forEach(products::addAll);
		return products.stream().anyMatch((bp) -> bp.getId()==p.getId());
	}

	@Override
	public List<Product> mostPopularProduct() {
		List<Product> products = new ArrayList<Product>();
		customers.stream().map(Customer::getBoughtProducts).
			forEach(products::addAll);
		
		Map<Integer, List<Product>> grouppedProducts = products.stream().
			collect(Collectors.groupingBy(Product::getId));
		
		List<Integer> sizes = new ArrayList<Integer>();
		grouppedProducts.forEach((i, l) -> sizes.add(l.size()));
		
		Optional<Integer> maxSize = sizes.stream().max(Integer::compare);
		
		if(!maxSize.isPresent()){
			return null;	
		}
		else {
			List<Product> res = new ArrayList<Product>();
			grouppedProducts.forEach((i, l) -> {
				if(l.size()==maxSize.get()) {
					res.add(l.get(0));
				}
			});
			return res;
		}
		
	}

	@Override
	public int countBuys(Product p) {
		return (int) customers.stream().mapToLong((c) -> c.getBoughtProducts().stream().
				filter((bp) -> bp.getId()==p.getId()).count()).sum();
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
