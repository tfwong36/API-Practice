package com.tatp.restapi.mapper;

import com.tatp.restapi.dto.CompanyRequest;
import com.tatp.restapi.dto.CompanyResponse;
import com.tatp.restapi.dto.EmployeeResponse;
import com.tatp.restapi.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {
    public Company toEntity(CompanyRequest companyRequest){
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }
    public CompanyResponse toResponse(Company company, List<EmployeeResponse> employeeResponses){
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        companyResponse.setEmployeeResponseList(employeeResponses);
        return companyResponse;
    }

}
