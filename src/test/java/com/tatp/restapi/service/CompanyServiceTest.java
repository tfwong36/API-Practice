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
    @InjectMocks
    CompanyService companyService;

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

}
