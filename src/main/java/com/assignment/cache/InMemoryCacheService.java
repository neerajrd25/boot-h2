package com.assignment.cache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.assignment.model.Cache;
import com.assignment.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCacheService {

    @Autowired
    private CacheRepository cacheRepository;

	private static final int CLEAN_UP_PERIOD_IN_SEC = 1000;

	private static final int PERIODS_IN_MILIS = 50000;

	private final ConcurrentHashMap<String, SoftReference<CacheObject>> cacheMap = new ConcurrentHashMap<>();

	public InMemoryCacheService() {
		Thread cleanerThread = new Thread(
				() -> {
					while (!Thread.currentThread().isInterrupted()) {
						try {
							Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
                            cacheMap.entrySet().removeIf(
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

	public void add(String key, String value) {
		if (key == null) {
			return;
		}
		if (value == null) {
            cacheMap.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + PERIODS_IN_MILIS;
            cacheMap.put(key, new SoftReference<>(new CacheObject(value,
					expiryTime)));
            cacheRepository.save(new Cache(key,value));
		}
	}

	public void remove(String key) {
	    cacheMap.remove(key);
        cacheRepository.deleteById(key);
	}

	public Object get(String key) {
		return Optional.ofNullable(cacheMap.get(key)).map(SoftReference::get)
				.filter(cacheObject -> !cacheObject.isExpired())
				.map(CacheObject::getValue).orElse(null);
	}

	public void put(Cache object) {
        long expiryTime = System.currentTimeMillis() + PERIODS_IN_MILIS;
        cacheMap.put(object.getKey(), new SoftReference<>(new CacheObject(object.getValue(),
                expiryTime)));
        cacheRepository.save(new Cache(object.getKey(),object.getValue()));
	}

	public List<Cache> getAll() {
        return cacheMap.entrySet().stream().map(e -> new Cache(e.getKey(),e.getValue().get().getValue())).collect(Collectors.toList());
	}

	public void putAll(List<Cache> list) {
        list.forEach(data -> put(data));
	}

	public void removeAll(List<String> list) {
        list.forEach(data -> remove(data));
	}

	public void clear() {
        cacheMap.clear();
	}

	public long size() {
		return cacheMap
				.entrySet()
				.stream()
				.filter(entry -> Optional.ofNullable(entry.getValue())
						.map(SoftReference::get)
						.map(cacheObject -> !cacheObject.isExpired())
						.orElse(false)).count();
	}

	
}
