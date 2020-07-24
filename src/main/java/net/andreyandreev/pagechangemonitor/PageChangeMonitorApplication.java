package net.andreyandreev.pagechangemonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(proxyBeanMethods = false)
@EnableScheduling
public class PageChangeMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PageChangeMonitorApplication.class, args);
	}

}
