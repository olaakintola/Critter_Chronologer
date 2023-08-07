package com.udacity.jdnd.course3.critter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
@ComponentScan({"package com.udacity.jdnd.course3.critter.pet, package com.udacity.jdnd.course3.critter.activities, package com.udacity.jdnd.course3.critter.schedule, package com.udacity.jdnd.course3.critter.user"})
public class CritterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}

}
