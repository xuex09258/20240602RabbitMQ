package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	// 若有錯誤發生會由處理特定的全局異常來處理 2:41
	@GetMapping("/{empId}")
	public Employee getEmployee(@PathVariable Integer empId) throws InterruptedException {
		//222 2:27若有錯誤發生會由處理特定的全局異常來處理 2:36 333
		if(empId < 1 ) {
			throw new RuntimeException("無此員編");
		} else if(empId >= 10) {
			throw new RuntimeException("網路負荷過重連線失敗...");
		}
		
		// 模擬業務處理延遲  111
		Thread.sleep(2000);
		
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmpName("John" + empId);
		emp.setDescription("Manager" + empId);
		emp.setSalary(30000.0 * empId);
		
		return emp;
	}
	
	// 2:41 444 若有錯誤發生可以自行處理中斷
	@CircuitBreaker(name = "employeeCircuitBreaker", fallbackMethod = "getEmployeeFallback")
	@GetMapping("/breaker/{empId}")
	public Employee getEmployeeBreaker(@PathVariable Integer empId) throws InterruptedException {
		// 若有錯誤發生會觸發 CircuitBreaker 而不會由全局異常來處理
		if(empId < 1 ) {
			throw new RuntimeException("無此員編");
		} else if(empId >= 10) {
			throw new RuntimeException("網路負荷過重連線失敗...");
		}
		
		// 模擬業務處理延遲
		Thread.sleep(2000);
		
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmpName("John" + empId);
		emp.setDescription("Manager" + empId);
		emp.setSalary(30000.0 * empId);
		
		return emp;
	}
	
	// 回退方法 2:46
	public Employee getEmployeeFallback(Integer empId, Throwable t) {
		// 2:52 此錯誤會由全局異常處理-------------------
		if(empId == 0) {
			throw new RuntimeException("無此員編 0");
		}
		//-----------------------------------------
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmpName("Fallback");
		
		//emp.setDescription("Fallback Description...");//4444
		//5555 18
		emp.setDescription(t.getMessage());
		
		emp.setSalary(null);
		return emp;
	}
}
