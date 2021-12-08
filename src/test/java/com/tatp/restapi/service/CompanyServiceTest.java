package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CompanyService companyService;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1,"Spring", null));
        given(companyRepository.findAll())
                .willReturn(companies);

        //when
        List<Company> actual = companyService.findAll();

        //then
        assertEquals(companies, actual);
    }
    @Test
    void should_return_a_company_when_get_company_given_company_id() {
        //given
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company(1,"Spring", null);
        Company company2 = new Company(2,"Spring2", null);

        companies.add(company1);
        companies.add(company2);
        given(companyRepository.findById(1))
                        .willReturn(company1);
        //when
        Company actual = companyService.findById(company1.getId());
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        //given
        List<Company> companies = new ArrayList<>();
        Company company1 = new Company(1,"Spring", null);
        Company company2 = new Company(2,"Spring2", null);

        Integer page = 1;
        Integer pageSize = 2;

        given(companyRepository.findByPage(page, pageSize))
                .willReturn(companies);

        //when`
        List<Company> actual = companyService.findByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }

//    @Test
//    void should_return_employees_when_findEmployeesByCompanyId_given_employees_and_companies_and_id() {
//        //given
//        List<Company> companies = new ArrayList<>();
//        List<Employee> employees = new ArrayList<>();
//        companies.add(new Company(1,"Spring", null));
//        companies.add(new Company(2,"Spring2", null));
//        Employee employee1 = new Employee(1, "Jason1", 18, "male", 5, 1);
//        Employee employee2 = new Employee(2, "Jason2", 18, "male", 5, 1);
//        employees.add(employee1);
//        employees.add(employee2);
//        employees.add(new Employee(3, "Jason3", 19, "male", 5, 2));
//        given(companyService.findEmployeesByCompanyId(1))
//                .willReturn(Arrays.asList(employee1,employee2));
//
//        //when
//        List<Employee> actual = companyService.findEmployeesByCompanyId(1);
//        //then
//        assertEquals(companies, actual);
//    }
}
