package com.assignment.repository;

import com.assignment.model.Cache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends CrudRepository<Cache, String> {
}
