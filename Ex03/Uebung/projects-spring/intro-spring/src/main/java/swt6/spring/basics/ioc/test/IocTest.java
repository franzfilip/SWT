package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogConfig;
import swt6.util.PrintUtil;

public class IocTest {

  private static void testSimple() {
    WorkLogServiceImpl workLog = new WorkLogServiceImpl();
    workLog.findAllEmployees();
    workLog.findEmployeeById(3L);
  }

  private static void testXmlConfiguration(){
    try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")){
      System.out.println("***> worklog-setter-injection");
      WorkLogService workLog1 = factory.getBean("workLog-setter-injection", WorkLogService.class);
      workLog1.findAllEmployees();
      workLog1.findEmployeeById(3L);

      System.out.println("***> workLog-constructor-injection");
      WorkLogService workLog2 = factory.getBean("workLog-constructor-injection", WorkLogService.class);
      workLog2.findAllEmployees();
      workLog2.findEmployeeById(3L);
    }
  }

  private static void testAnnotationConfig(){
    try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml")){
      System.out.println("***> worklog-setter-injection");
      WorkLogService workLog = factory.getBean("workLog", WorkLogService.class);
      workLog.findAllEmployees();
      workLog.findEmployeeById(3L);
    }
  }

  private static void testJavaConfig(){
    try(AbstractApplicationContext context = new AnnotationConfigApplicationContext(WorkLogConfig.class)){
      System.out.println("***> worklog-setter-injection");
      WorkLogService workLog = context.getBean("workLog", WorkLogService.class);
//      WorkLogService workLog = context.getBean(WorkLogService.class);
      workLog.findAllEmployees();
      workLog.findEmployeeById(3L);
    }
  }

  public static void main(String[] args) {
    PrintUtil.printTitle("testSimple", 60);
    testSimple();

    PrintUtil.printTitle("testXmlConfig", 60);
    testXmlConfiguration();

    PrintUtil.printTitle("testAnnotationConfig", 60);
    testAnnotationConfig();

    PrintUtil.printTitle("testJavaConfig", 60);
    testJavaConfig();

    PrintUtil.printSeparator(60);
  }
}
