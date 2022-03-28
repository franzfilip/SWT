package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.FileLogger;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses = {Logger.class, WorkLogServiceImpl.class})
public class WorkLogConfig {
    //ist ein Singletonbean, wird nur einmal aufgerufen
//    @Bean
//    public Logger consoleLogger(){
//        return new ConsoleLogger();
//    }
//
//    @Bean
//    public Logger fileLogger(){
//        return new FileLogger();
//    }
//
//    @Bean
//    public WorkLogService workLog(){
//        return new WorkLogServiceImpl(consoleLogger());
////        return new WorkLogServiceImpl();
//    }
}
