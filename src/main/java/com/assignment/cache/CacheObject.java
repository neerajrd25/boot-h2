package com.assignment.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor 

public class CacheObject {

	@Getter
	private String value;
	private long expiryTime;

	boolean isExpired() {
		return System.currentTimeMillis() > expiryTime;
	}
}
