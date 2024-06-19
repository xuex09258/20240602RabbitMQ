package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	// 查詢指定訂單  111 1:59
	@GetMapping("/{orderId}")
	public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Integer orderId) {
		OrderDto orderDto = orderService.getOrderById(orderId);
		ApiResponse<OrderDto> apiResponse = null;
		if(orderDto == null) {
			apiResponse = new ApiResponse<>(false, "查無訂單資料", null);
		} else {
			apiResponse = new ApiResponse<>(true, "有訂單資料", orderDto);
		}
		return ResponseEntity.ok(apiResponse);
	}
	
	// 查詢全部訂單
	//@GetMapping()
	//public ResponseEntity<ApiResponse<List<OrderDto>>> findAll() {
		//List<OrderDto> orderDtos = orderService.findAll();
		//ApiResponse<List<OrderDto>> apiResponse = null;
		//if(orderDtos.isEmpty()) {
			//apiResponse = new ApiResponse<>(false, "查無訂單資料", null);
		//} else {
			//apiResponse = new ApiResponse<>(true, "資料筆數:" + orderDtos.size(), orderDtos);
		//}
		//return ResponseEntity.ok(apiResponse);
	//} 
	
}
