package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CustomerClient;
import com.example.demo.client.ProductClient;
import com.example.demo.dao.OrderDao;
import com.example.demo.model.dto.ItemDto;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.po.Customer;
import com.example.demo.model.po.Item;
import com.example.demo.model.po.Order;
import com.example.demo.model.po.Product;

@Service
public class OrderService {
	
	@Autowired //111
	private OrderDao orderDao;
	
	@Autowired   //1:56
	private CustomerClient customerClient;
	
	@Autowired   //bb  2"26
	private ProductClient productClient;
	
	//bb222 2:19  Item 單筆訂單項目 po 轉 dto
	private ItemDto convertToDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setId(item.getId());
		itemDto.setQuantity(item.getQuantity());
		
		//2:22  透過 Feign 取得遠端商品資料    productClient===>client
		Product product = productClient.getProductById(item.getProductId()).getData();
		itemDto.setProduct(product);
		
		return itemDto;
	}
	
	// 222 1=49   Order 單筆訂單 po 轉 dto
	private OrderDto convertToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setOrderDate(order.getOrderDate());
		
		//透過 Feign 取得遠端客戶資料    1:56   333  customerClient======>client
		Customer customer = customerClient.getCustomerById(order.getId()).getData();
		orderDto.setCustomer(customer);
		
		//下半場 bb111 2:17 
		// 透過 Feign 取得遠端商品資料
		for(Item item : order.getItems()) {
			 //Item po 轉 dto
			ItemDto itemDto = convertToDto(item);
			orderDto.getItemDtos().add(itemDto);
		}
		
		return orderDto;
	}
	
	// cc222 2:34 Orders 多筆訂單 po 轉 dto
	private List<OrderDto> convertToDto(List<Order> orders) {
	//return orders.stream().map(order -> convertToDto(order)).collect(Collectors.toList());
		return orders.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	// 單筆訂單  111  1:45
	public OrderDto getOrderById(Integer orderId) {
		Optional<Order> orderOpt = orderDao.findById(orderId);
		if(orderOpt.isEmpty()) {
			return null;
		}
		Order order = orderOpt.get();
		// 將 po 轉 dto
		return convertToDto(order);//222
	}
	
	// cc111 2:31 所有訂單
	public List<OrderDto> findAll() {
		List<Order> orders = orderDao.findAll();
		 //將 List po 轉 List dto
		return convertToDto(orders);
	}
	
}
