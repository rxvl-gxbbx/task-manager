package org.rxvlvxr.tasks.integration;

import org.junit.jupiter.api.BeforeAll;
import org.rxvlvxr.tasks.integration.annotation.IT;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "ROLE"})
public class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15:2")
            .withInitScript("db/migration/V1__init.sql");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
