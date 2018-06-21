package com.assignment.cache;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class InMemoryCacheService implements Cache {

	private static final int CLEAN_UP_PERIOD_IN_SEC = 10;

	private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<>();

	public InMemoryCacheService() {
		Thread cleanerThread = new Thread(
				() -> {
					while (!Thread.currentThread().isInterrupted()) {
						try {
							Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
							cache.entrySet().removeIf(
									entry -> Optional
											.ofNullable(entry.getValue())
											.map(SoftReference::get)
											.map(CacheObject::isExpired)
											.orElse(false));
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
				});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

	@Override
	public void add(String key, Object value, long periodInMillis) {
		if (key == null) {
			return;
		}
		if (value == null) {
			cache.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + periodInMillis;
			cache.put(key, new SoftReference<>(new CacheObject(value,
					expiryTime)));
		}
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}

	@Override
	public Object get(String key) {
		return Optional.ofNullable(cache.get(key)).map(SoftReference::get)
				.filter(cacheObject -> !cacheObject.isExpired())
				.map(CacheObject::getValue).orElse(null);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public long size() {
		return cache
				.entrySet()
				.stream()
				.filter(entry -> Optional.ofNullable(entry.getValue())
						.map(SoftReference::get)
						.map(cacheObject -> !cacheObject.isExpired())
						.orElse(false)).count();
	}

	
}
