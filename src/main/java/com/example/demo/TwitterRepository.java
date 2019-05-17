package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface TwitterRepository extends CrudRepository<Twitter, Long> {
}
