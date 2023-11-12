package tn.esprit.devops_project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DevOpsProjectSpringBootApplicationIntegrationTest {

    @Test
    public void contextLoads() {
        // This test method ensures that the Spring application context loads without errors.
    }
}
