package com.tatp.restapi.repository;

import com.tatp.restapi.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface EmployeeRepositoryMongo extends MongoRepository<Employee, String> {
    List<Employee> findByGender(String gender);
    List<Employee> findByCompanyID(String companyID);

    default List<Employee> findByPage(Integer page, Integer pageSize){
        return this.findAll().stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    };
}
