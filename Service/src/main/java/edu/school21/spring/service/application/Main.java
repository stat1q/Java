
package edu.school21.spring.service.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import edu.school21.spring.service.config.ApplicationConfig;
import edu.school21.spring.service.services.UserService;
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UserService userService = context.getBean("userService", UserService.class);

        String tempPassword = userService.signUp("created@mail.ru");

        context.close();


    }
}
