package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkLogServiceImpl implements swt6.spring.basics.ioc.logic.WorkLogService {
  private Map<Long, Employee> employees = new HashMap<Long, Employee>();

  @Autowired
  @Log
  private Logger logger;
  
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public WorkLogServiceImpl() {
    init();
  }

  public WorkLogServiceImpl(Logger logger){
    this.logger = logger;
  }

  public Employee findEmployeeById(Long id) {
    Employee employee = employees.get(id);
    logger.log(String.format("findEmployeeById(%d) --> %s", id, (employee != null) ? employee.toString() : "<null>"));
    return employee;
  }

  public List<Employee> findAllEmployees() {
    logger.log("findAllEmployees()");
    return new ArrayList<Employee>(employees.values());
  }
}
