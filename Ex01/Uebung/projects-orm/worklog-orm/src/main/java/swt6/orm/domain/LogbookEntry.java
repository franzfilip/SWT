package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.common.util.impl.Log;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class LogbookEntry implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //@Fetch(FetchMode.SELECT) // das hier wäre auch eager loading aber es wird in einzelenen selects ausgeführt
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public LogbookEntry(){

    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void attachEmployee(Employee empl){
        if(this.employee != null){
            this.employee.getLogbookEntries().remove(this);
        }
        this.employee = empl;
        this.employee.getLogbookEntries().add(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return activity + ": " + startTime.format(formatter) + " - " + endTime.format(formatter);
    }
}
