package com.tatp.restapi.repository;

import com.tatp.restapi.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepositoryMongo extends MongoRepository<Company, String> {
}
