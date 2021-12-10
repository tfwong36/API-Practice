package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;


    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Spring"));
        given(companyRepository.findAll())
                .willReturn(companies);
        //when
        List<Company> actual = companyService.findAll();
        //then
        assertEquals(companies, actual);
    }
    @Test
    void should_return_a_company_when_get_company_given_company_id() {

        List<Company> companies = new ArrayList<>();
        Company company1 = new Company("Spring1");
        Company company2 = new Company("Spring2");
        companies.add(company1);
        companies.add(company2);
        given(companyRepository.findById(any()))
                .willReturn(java.util.Optional.of(company1));
        //when
        Company actual =  companyService.findById(company1.getId());
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company(){
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("company1"));
        companies.add(new Company("company2"));
        given(companyRepository.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(companies, PageRequest.of(1, 2), 2));
        //when
        List<Company> actual =  companyService.findByPage(1, 2);
        //then
        assertEquals(companies, actual);
    }
    @Test
    void should_return_company_when_perform_post_given_company(){
        //given
        Company company = new Company("Spring");

        given(companyRepository.save(company))
                .willReturn(company);
        //when
        Company actual = companyService.create(company);
        //then
        assertEquals(company, actual);
    }
    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = new Company("Spring");
        willDoNothing().given(companyRepository).deleteById(company.getId());

        //when
        companyService.remove(company.getId());
        //then
        verify(companyRepository).deleteById(company.getId());
        assertEquals(0, companyRepository.findAll().size());
    }
  @Test
    void should_return_update_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company = new Company("Spring");
        Company updatedCompany = new Company("Spring2");
        company.setName(updatedCompany.getName());

        given(companyRepository.save(company))
            .willReturn(company);

      given(companyRepository.findById(any()))
              .willReturn(java.util.Optional.of(company));
        //when
        Company actual = companyService.edit(any(), updatedCompany);
        //then
      assertEquals(company, actual);
        assertEquals(company.getId(), actual.getId());
    }
}
