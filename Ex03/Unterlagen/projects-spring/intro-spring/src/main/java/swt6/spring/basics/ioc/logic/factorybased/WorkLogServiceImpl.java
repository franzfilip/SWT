package swt6.spring.basics.ioc.logic.factorybased;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import swt6.spring.basics.ioc.domain.Employee;

public class WorkLogServiceImpl {
  private Map<Long, Employee> employees = new HashMap<Long, Employee>();

  private void initLogger() {
    Properties props = new Properties();
   
    try {
      ClassLoader cl = this.getClass().getClassLoader();
      props.load(cl.getResourceAsStream(
        "swt6/spring/basics/ioc/logic/factorybased/worklog.properties"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    //
    // TODO: read logger type from configuration
    //
  }
  
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }
  
  public WorkLogServiceImpl() {
    initLogger();
    init();
  }


  public Employee findEmployeeById(Long id) {
    // TODO: log method invocation
    return employees.get(id);
  }

  public List<Employee> findAllEmployees() {
    // TODO: log method invocation
    return new ArrayList<Employee>(employees.values());
  }
}
