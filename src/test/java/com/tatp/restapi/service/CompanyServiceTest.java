package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.CompanyRepositoryMongo;
import com.tatp.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    CompanyRepositoryMongo companyRepositoryMongo;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    CompanyService companyService;


    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Spring"));
        given(companyRepositoryMongo.findAll())
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
        Company company1 = new Company("1","Spring", null);
        Company company2 = new Company("2","Spring2", null);
        companies.add(company1);
        companies.add(company2);
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("1", "AAA", 10, "male", 10, "1");
        Employee employee2 = new Employee("2", "BBB", 10, "male", 10, "1");
        employees.add(employee1);
        employees.add(employee2);
        given(companyRepository.findById("1"))
                        .willReturn(company1);
        given(employeeService.getEmployeesByCompanyID("1"))
                .willReturn(employees);
        //when
        Company actual = companyService.findById(company1.getId());
        //then
        assertEquals(2, actual.getEmployees().size());
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        //given
        List<Company> companies = new ArrayList<>();
        Company company1 = new Company("1","Spring", null);
        Company company2 = new Company("2","Spring2", null);
        companies.add(company1);
        companies.add(company2);
        int page = 1;
        int pageSize = 2;

        given(companyRepository.findByPage(page, pageSize))
                .willReturn(companies);

        //when`
        List<Company> actual = companyService.findByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }
    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        Company company = new Company("1","Spring", null);

        given(companyRepository.create(company))
                .willReturn(company);
        //when
        Company actual = companyService.create(company);
        //then
        assertEquals(company, actual);
    }
    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = new Company("1","Spring", null);
        willDoNothing().given(companyRepository).remove(company.getId());

        //when
        companyService.remove(company.getId());
        //then
        verify(companyRepository).remove(company.getId());
        assertEquals(0,companyRepository.findAll().size());
    }
  @Test
    void should_return_update_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company = new Company("1","Spring", null);
        Company updatedCompany = new Company("1","Spring2", null);
        company.setName(updatedCompany.getName());

        given(companyRepository.save("1", company))
            .willReturn(company);

      given(companyRepository.findById("1"))
              .willReturn(company);
        //when
        Company actual = companyService.edit("1", updatedCompany);
        //then
        assertEquals(updatedCompany.getId(), actual.getId());
    }
}
