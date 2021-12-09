package com.tatp.restapi.repository;

import com.tatp.restapi.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CompanyRepositoryMongo extends MongoRepository<Company, String> {
    default List<Company> findByPage(Integer page, Integer pageSize){
        return this.findAll().stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    };
}
