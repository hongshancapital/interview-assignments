package interview.assignments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"interview.assignments"})
public class InterviewAssignmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewAssignmentsApplication.class, args);
    }

}
