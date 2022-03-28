package swt6.spring.basics.aop.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.util.PrintUtil;

public class AopTest {

  private static void testAOP(String configFileName) {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(configFileName)) {

    }
  }

  public static void main(String[] args) {
		PrintUtil.printTitle("testAOP (config based)", 60);
    testAOP("<< insert spring config file here >>");
		PrintUtil.printSeparator(60);

		// PrintUtil.printTitle("testAOP (annotation based)", 60);
    // testAOP("<< insert spring config file here >>");
		// PrintUtil.printSeparator(60);
  }

}
