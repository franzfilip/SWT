package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.common.util.impl.Log;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
//@Table(name = "EMPL_TABLE")
public class Employee implements Serializable {
    @Id @GeneratedValue
    private long id;
    //@Column(name = "F_N", nullable = false)
    private String firstName;
    private String lastName;
    //@Transient
    private LocalDate dateOfBirth;
    //@Fetch(FetchMode.SELECT) //statt join wird in select statements aufgeteilt
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY) //Lazyloading funktioniert nur in derselben Transaktion
    //@JoinColumn(name = "emplId")
    private Set<LogbookEntry> logbookEntries = new HashSet<>();
    //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn
    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "address_zipCode"))
    private Address address;

    @ElementCollection
    @CollectionTable(name = "EMPL_PHONES", joinColumns = @JoinColumn(name = "EMPL_ID"))
    @Column(name = "PHONE_NR")
    private Set<String> phones = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogBookEntry(LogbookEntry entry){
        if(entry.getEmployee() != null){
            entry.getEmployee().getLogbookEntries().remove(entry);
        }
        this.logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    public void removeLogBookEntry(LogbookEntry entry){
        if(entry.getEmployee() != null){
            entry.setEmployee(null);
        }
        this.logbookEntries.remove(entry);
    }

    public void addPhone(String phone) {
        this.phones.add(phone);
    }

    public void assignToProject(Project project){
        this.projects.add(project);
        project.getMembers().add(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(id + ": " + lastName + ", "+ firstName + " (" + dateOfBirth.format(formatter) + ")");
        return sb.toString();
    }
}
