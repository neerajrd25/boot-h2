package com.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.cache.Cache;
import com.assignment.cache.RequestObject;

@RestController
@RequestMapping("/cache")
@CrossOrigin("*")
public class CacheController {
	
	@Autowired
	private Cache cacheService;
	
    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody RequestObject request) {
         cacheService.add(request.getKey(), request.getValue(), 5);
         return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{key}")
    public ResponseEntity<Object> get(@PathVariable("key") String key) {
    	System.out.println(cacheService.get(key));
    	return new ResponseEntity<>(cacheService.get(key),HttpStatus.OK);
    }
}
