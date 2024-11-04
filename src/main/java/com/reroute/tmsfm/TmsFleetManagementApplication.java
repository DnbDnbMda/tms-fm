package com.reroute.tmsfm;

import com.reroute.tmsfm.repository.OrganizationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TmsFleetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmsFleetManagementApplication.class, args);
    }
}
