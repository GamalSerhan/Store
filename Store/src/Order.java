
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order 
{

	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) 
	{
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() 
	{
		return customer;
	}

	public Salesman getSalesman() 
	{
		return salesman;
	}

	public Date getOrderedOn() 
	{
		return orderedOn;
	}

	public String getDeliveryStreet() 
	{
		return deliveryStreet;
	}

	public String getDeliveryCity() 
	{
		return deliveryCity;
	}

	public String getDeliveryCountry() 
	{
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() 
	{
		return items;
	}

	public float total() 
	{
		float totalItems = 0;
		for (OrderItem item : items) 
		{
			float totalItem=0;
			
			float itemAmount = getItemAmount(item);
			if (ProductCategoryIsAccessories(item)) 
			{
				float booksDiscount = 0;
				if (itemAmount >= 100) 
				{
					booksDiscount = ItemDiscount(itemAmount,10);
				}
				totalItem = itemAmount - booksDiscount;
			}
			if (ProductCategoryIsBikes(item)) 
			{
				// 20% discount for Bikes
				totalItem = ItemWith_20percentDiscount(itemAmount);
			}
			if (ProductCategoryIsCloathing(item)) 
			{
				float cloathingDiscount = 0;
				if (item.getQuantity() > 2) 
				{
					cloathingDiscount = item.getProduct().getUnitPrice();
				}
				totalItem = itemAmount - cloathingDiscount;
			}
			totalItems += totalItem;
		}

		if (this.deliveryCountry == "USA")
		{
			// total=totalItems + tax + 0 shipping
			return totalItems + totalItems * 5 / 100;
		}

		// total=totalItemst + tax + 15 shipping
		return totalItems + totalItems * 5 / 100 + 15;
	}

	private float ItemWith_20percentDiscount(float itemAmount) {
		return itemAmount - ItemDiscount(itemAmount,20);
	}

	private float ItemDiscount(float itemAmount, int Discount) {
		return itemAmount * Discount / 100;
	}

	private boolean ProductCategoryIsCloathing(OrderItem item) 
	{
		return item.getProduct().getCategory() == ProductCategory.Cloathing;
	}

	private boolean ProductCategoryIsBikes(OrderItem item) 
	{
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private boolean ProductCategoryIsAccessories(OrderItem item) 
	{
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}

	private float getItemAmount(OrderItem item) 
	{
		return item.getProduct().getUnitPrice() * item.getQuantity();
	}
}
