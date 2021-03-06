package com.singfung.blackfriday.controller;

import com.singfung.blackfriday.common.Result;
import com.singfung.blackfriday.service.OrderService;
import com.singfung.blackfriday.service.RedisOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blackfriday")
public class OrderController
{
	@Autowired
	private RedisOrderService redisOrderService = null;
	@Autowired
	private OrderService orderService = null;

	@PostMapping(value = "/{stockId}/redis-order/{userId}")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Result<Object>> generateAnOrderInRedis(@PathVariable int stockId, @PathVariable int userId)
	{
		boolean flag = redisOrderService.generateAnOrderInRedis(stockId, userId);

		if(flag == true)
		{
			return ResponseEntity.status(HttpStatus.OK).body(Result.success());
		}

		return ResponseEntity.status(HttpStatus.GONE).body(Result.failure());
	}

	@PostMapping(value = "/{stockId}/plock-order/{userId}")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<Result<Object>> generateAnOrderWithPessimisticLocking(@PathVariable int stockId, @PathVariable int userId)
	{
		boolean flag = orderService.generateAnOrderWithPessimisticLocking(stockId, userId);

		if(flag == true)
		{
			return ResponseEntity.status(HttpStatus.OK).body(Result.success());
		}

		return ResponseEntity.status(HttpStatus.GONE).body(Result.failure());
	}
}