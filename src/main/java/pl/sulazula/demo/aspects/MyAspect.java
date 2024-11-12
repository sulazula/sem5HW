package pl.sulazula.demo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Before("execution(* pl.sulazula.demo.service.UserService.*(..))")
    public void before() {
        System.out.println("UserService method used...");
    }
}
