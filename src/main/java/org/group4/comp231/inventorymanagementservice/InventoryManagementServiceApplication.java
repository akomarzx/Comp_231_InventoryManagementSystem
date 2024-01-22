package org.group4.comp231.inventorymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//TODO: Disable security during early stages to facilitate development
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class InventoryManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementServiceApplication.class, args);
	}

}
