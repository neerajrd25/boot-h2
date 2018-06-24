package com.assignment.controller;

import com.assignment.cache.InMemoryCacheService;
import com.assignment.model.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
@CrossOrigin("*")
public class CacheController {
	
	@Autowired
	private InMemoryCacheService cacheService;
	
    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody Cache request) {
         cacheService.add(request.getKey(), request.getValue());
         return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{key}")
    public ResponseEntity<Object> get(@PathVariable("key") String key) {
    	System.out.println(cacheService.get(key));
    	return new ResponseEntity<>(cacheService.get(key),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody Cache request) {
        cacheService.put(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("key") String key) {
        cacheService.remove(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(cacheService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<HttpStatus> addAll(@RequestBody List<Cache> requestList) {
        cacheService.putAll(requestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/all")
    public ResponseEntity<HttpStatus> updateAll(@RequestBody List<Cache> requestList) {
        cacheService.putAll(requestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(@RequestBody List<String> requestList) {
        cacheService.removeAll(requestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
