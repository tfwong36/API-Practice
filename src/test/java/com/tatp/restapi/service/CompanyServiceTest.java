package com.tatp.restapi.service;

        import com.tatp.restapi.repository.CompanyRepository;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.springframework.test.context.junit.jupiter.SpringExtension;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyServiceTest companyServiceTest;


}
