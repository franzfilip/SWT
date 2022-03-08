package swt6.orm.jpa;

import org.hibernate.Criteria;
import org.hibernate.StaleStateException;
import org.hibernate.annotations.common.util.impl.Log;
import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorkLogManager {
    private static void insertEmployee1(Employee empl){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WorkLogPU");
        EntityManager em = null;
        EntityTransaction tx = null;

        try{
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(empl);
            tx.commit();
        }
        catch (Exception e){
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        }
        finally {
            if(em != null){
                em.close();
            }
            emf.close();
        }
    }

    private static void insertEmployee(Employee empl){
        try{
          EntityManager em = JpaUtil.getTransactedEntityManager();
          em.persist(empl);
          JpaUtil.commit();
        } catch (Exception ex){
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static <T> void insertEntity(T entity){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static <T> T saveEntity(T entity){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            entity = em.merge(entity);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }

        return entity;
    }

    private static void listEmployees(){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            List<Employee> employeeList = em.createQuery("select e from Employee e", Employee.class).getResultList();
            employeeList.forEach(e -> {
                System.out.println(e);
                if(e.getLogbookEntries().size() > 0){
                    System.out.println(" logBookEntries:");
                    e.getLogbookEntries().forEach(lbe -> {
                        System.out.println("  " + lbe);
                    });
                }

                if(e.getProjects().size() > 0){
                    System.out.println(" projects:");
                    e.getProjects().forEach(p -> {
                        System.out.println(p.getName());
                    });
                }
            });
            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static <T> T updateEntity(Serializable id, T entity, Class<T> entityType){
        T storedEntity = null;
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            if(id == null){
                throw new RuntimeException("ID must not be null");
            }

            storedEntity = em.find(entityType, id);
            if(storedEntity == null){
                throw new RuntimeException("Entity does not exist");
            }
            storedEntity = em.merge(entity);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return storedEntity;
    }

    private static Employee addLogBookEntries(Employee empl, LogbookEntry... entries){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            empl = em.merge(empl);
            for(var entry : entries){
                empl.addLogBookEntry(entry);
            }
            //empl = em.merge(empl);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }

        return empl;
    }

    private static void deleteEmployee(Employee empl){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            empl = em.merge(empl);
            for(LogbookEntry entry : new java.util.ArrayList<>(empl.getLogbookEntries())){
                empl.removeLogBookEntry(entry);
                //em.remove(entry);
            }
            //em.remove(empl);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void testFetchingStrategies() {

        // prepare: fetch valid ids for employee and logbookentry
        Long entryId = null;
        Long emplId = null;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            Optional<LogbookEntry> entry =
                    em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (!entry.isPresent()) return;
            entryId = entry.get().getId();

            Optional<Employee> empl =
                    em.createQuery("select e from Employee e", Employee.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (!empl.isPresent()) return;
            emplId = empl.get().getId();

            JpaUtil.commit();
        }
        catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.println("###> Fetching LogbookEntry ...");
            LogbookEntry entry = em.find(LogbookEntry.class, entryId);
            System.out.println("###> Fetched associated Employee");
            Employee empl1 = entry.getEmployee();
            System.out.println(empl1);
            System.out.println("");

            JpaUtil.commit();
        }
        catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.println("###> Fetching Employee ...");
            Employee emp2 = em.find(Employee.class, emplId);
            System.out.println("###> Fetched Employee ...");
            Set<LogbookEntry> entries = emp2.getLogbookEntries();
            System.out.println("###> Fetched associated entries");
            entries.forEach(System.out::println);
            System.out.println("###> Accessed associated entries");
            JpaUtil.commit();
        }
        catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");
    }

    private static void listEntriesOfEmployee(Employee employee){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.printf("logbook entries of employee: %s (%d)%n", employee.getLastName(), employee.getId());

            /*
            variante 1 aber so mach ma ds fix nd
            TypedQuery<LogbookEntry> qry = em.createQuery("from LogbookEntry where employee.id = id" + employee.getId(), LogbookEntry.class);
            */

            //Variante 2
            /*
            TypedQuery<LogbookEntry> qry = em.createQuery("from LogbookEntry where employee.id = ?1", LogbookEntry.class);
            qry.setParameter(1, employee.getId());
            */

            //Variante 3
            /*
            TypedQuery<LogbookEntry> qry = em.createQuery("from LogbookEntry where employee.id = :emplId", LogbookEntry.class);
            qry.setParameter("emplId", employee.getId());
            */
            TypedQuery<LogbookEntry> qry = em.createQuery("from LogbookEntry  where employee = :empl", LogbookEntry.class);
            qry.setParameter("empl", employee);

            qry.getResultList().forEach(System.out::println);


            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void listEmployeesResidingIn(String zipCode){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Employee> qry = em.createQuery("select e from Employee e where e.address.zipCode = :zipCode", Employee.class);
            qry.setParameter("zipCode", zipCode);
            qry.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void loadEmployeesWithEntries(){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Employee> qry = em.createQuery(
                    "select distinct e from Employee e join e.logbookEntries le where le.activity = :activity", Employee.class);
            qry.setParameter("activity", "Analysis");
            qry.getResultList().forEach(System.out::println);

            qry = em.createQuery("select distinct e from Employee e join fetch e.logbookEntries", Employee.class);
            List<Employee> empls = qry.getResultList();

            JpaUtil.commit();

            empls.forEach(System.out::println);
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void listEntriesofEmployeesCQ(Employee empl){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<LogbookEntry> entryCQ = cb.createQuery(LogbookEntry.class);
            Root<LogbookEntry> entry = entryCQ.from(LogbookEntry.class);
            ParameterExpression<Employee> p = cb.parameter(Employee.class);

            entryCQ.where(cb.equal(entry.get("employee"), p)).select(entry);
            //entryCQ.where(cb.equal(entry.get(LogbookEntry_.employee), p)).select(entry);

            TypedQuery<LogbookEntry> entriesOfEmplQry = em.createQuery(entryCQ);
            entriesOfEmplQry.setParameter(p, empl);
            entriesOfEmplQry.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    private static Employee addPhones(Employee empl, String... phones){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            empl = em.merge(empl);
            for(String phone : phones){
                empl.addPhone(phone);
            }

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }

        return empl;
    }

    private static Employee assignToProject(Employee empl, Project project){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();

            empl = em.merge(empl);
            empl.assignToProject(project);

            JpaUtil.commit();
        } catch(Exception e){
            JpaUtil.rollback();
            throw e;
        }

        return empl;
    }

    public static void main(String[] args) {
        try{
            System.out.println("----create schema----");
            JpaUtil.getEntityManager();

            PermanentEmployee pe = new PermanentEmployee("Max", "Mustermann", LocalDate.of(1980, 1, 10));
            pe.setSalary(5000.0);
            Employee empl1 = pe;
            empl1.setAddress(new Address("4040", "Linz", "Hauptstraße 1"));

            TemporaryEmployee te = new TemporaryEmployee("Anna", "Musterfrau", LocalDate.of(1970, 2, 2));
            te.setRenter("Microsoft");
            te.setHourlyRate(80.0);
            te.setStartDate(LocalDate.of(2022, 2, 1));
            te.setEndDate(LocalDate.of(2022, 3, 1));
            Employee empl2 = te;
            empl2.setAddress(new Address("4040", "Linz", "Hauptstraße 3"));

            LogbookEntry entry1 = new LogbookEntry("Analysis", LocalDateTime.of(2022, 2, 1, 9, 0), LocalDateTime.of(2022, 2, 1, 15, 0));
            LogbookEntry entry2 = new LogbookEntry("Implementation", LocalDateTime.of(2022, 2, 1, 8, 0), LocalDateTime.of(2022, 2, 1, 15, 0));
            LogbookEntry entry3 = new LogbookEntry("snusn", LocalDateTime.of(2022, 2, 1, 13, 0), LocalDateTime.of(2022, 2, 1, 14, 0));

            System.out.println("----insertEmployee----");
            empl1 = saveEntity(empl1);
            //empl1.setFirstName("DIESE_TEST");
            //empl1 = saveEntity(empl1);
            empl2 = saveEntity(empl2);

            System.out.println("----listEmployees----");
            listEmployees();

            System.out.println("----updateEntity----");
            empl1.setFirstName("UPDATE");
            empl1 = updateEntity(empl1.getId(), empl1, Employee.class);

            System.out.println("----listEmployees----");
            listEmployees();

            System.out.println("----addLogBookEntries----");
            //entry1 = saveEntity(entry1);
            //entry2 = saveEntity(entry2);
            //entry3 = saveEntity(entry3);

            empl1 = addLogBookEntries(empl1, entry1, entry2);
            empl2 = addLogBookEntries(empl2, entry3);

            System.out.println("----listEmployees----");
            listEmployees();

            System.out.println("----deleteEmployee----");
            deleteEmployee(empl2);

            System.out.println("----listEmployees----");
            listEmployees();

            System.out.println("----testFetchingStrategies----");
            testFetchingStrategies();

            System.out.println("----listEntriesOfEmployee----");
            listEntriesOfEmployee(empl1);

            System.out.println("----listEmployeesResidingIn----");
            listEmployeesResidingIn("4040");

            System.out.println("----loadEmployeesWithEntries----");
            loadEmployeesWithEntries();

            System.out.println("----listEntriesOfEmployeeCQ----");
            listEntriesofEmployeesCQ(empl1);

            System.out.println("----addPhones----");
            empl1 = addPhones(empl1, "021", "123", "133");

            System.out.println("----assignProjects----");
            empl1 = assignToProject(empl1, new Project("SWT"));

            System.out.println("----listEmployees----");
            listEmployees();
        }
        finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }
}
