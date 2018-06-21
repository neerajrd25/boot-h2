package com.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.model.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

}
