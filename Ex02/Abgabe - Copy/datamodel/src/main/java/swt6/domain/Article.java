package swt6.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Article {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String description;
    private double startPrice;
    private double finalPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer trader;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Customer buyer;

    public Article(){}

    public Article(String name, String description, double startPrice, LocalDateTime startTime, LocalDateTime endTime, Customer trader) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trader = trader;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
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

    public Customer getTrader() {
        return trader;
    }

    public void setTrader(Customer trader) {
        this.trader = trader;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }
}
