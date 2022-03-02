package swt6.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmployeeManager {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  private static void saveEmployee1(Employee empl){
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    session.save(empl);

    tx.commit();
    session.close();
    sessionFactory.close();
  }

  private static void saveEmployee2(Employee empl){
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    session.save(empl);

    tx.commit();
    sessionFactory.close();
  }

  private static void saveEmployee(Employee empl){
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    session.save(empl);
    tx.commit();
  }

  private static List<Employee> getAllEmployees(){
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    List<Employee> empls = session.createQuery("select e from Employee e", Employee.class).getResultList(); //HQL geht immer gegen die Klasse nicht gegen die Datenbank
    tx.commit();

    return empls;
  }

  private static boolean updateEmployee(long emplId, String firstName, String lastName, LocalDate dob){
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Employee empl = session.find(Employee.class, emplId);
    if(empl != null){
      empl.setFirstName(firstName);
      empl.setLastName(lastName);
      empl.setDateOfBirth(dob);
    }
    tx.commit();

    return empl != null;
  }

  private static boolean deleteEmployee(long emplId){
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();
    /*
    var empl = session.find(Employee.class, emplId);
    if(empl != null){
      session.delete(empl);
    }
    boolean deleted = empl != null;
    */

    Query<?> delQry = session.createQuery("delete from Employee e where e.id = :id");
    delQry.setParameter("id", emplId);
    boolean deleted = delQry.executeUpdate() > 0;
    tx.commit();
    return deleted;
  }

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, insert, list, update, delete";
    
    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    HibernateUtil.getSessionFactory();

    try {

      while (!userCmd.equals("quit")) {

        switch (userCmd) {
          
        case "insert":
          saveEmployee(new Employee(
                  promptFor(in, "firstName"),
                  promptFor(in, "lastName"),
                  LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)
          ));
          break;
        case "list":
          getAllEmployees().forEach(System.out::println);
          break;
        case "update":
          try{
            boolean success = updateEmployee(
                    Long.parseLong(promptFor(in, "id")),
                    promptFor(in, "firstName"),
                    promptFor(in, "lastName"),
                    LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter));
            System.out.println(success ? "employee updated": "employee not found");
          }
          catch (DateTimeParseException ex){
            System.err.println("Invalid Date Format");
          }
          break;

        case "delete":
          boolean success = deleteEmployee(Long.parseLong(promptFor(in, "id")));
          System.out.println(success ? "employee deleted": "employee not found");
          break;

        default:
          System.out.println("ERROR: invalid command");
          break;
        }

        System.out.println(availCmds);
        userCmd = promptFor(in, "");
      } // while

    } // try
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      HibernateUtil.closeSessionFactory();
    }
  }
}
