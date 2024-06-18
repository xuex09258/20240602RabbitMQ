package com.example.demo;

import com.example.demo.dao.OrderDao;

public class TestOrderDao {

	public static void main(String[] args) {
		
		OrderDao orderdao =  new OrderDao();
		orderdao.findAll().forEach(System.out::println);
		System.out.println();
		System.out.println(orderdao.findById(1).get());
		
	}

}
