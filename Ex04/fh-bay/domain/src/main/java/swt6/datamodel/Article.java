package swt6.datamodel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Article {
    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double startPrice;

    private double finalPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer trader;

    @ManyToOne
    private Customer buyer;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private BiddingState state;

    public Article(){}

    public Article(String name, String description, double startPrice, LocalDateTime startTime, LocalDateTime endTime, Customer trader, Category category, BiddingState state) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trader = trader;
        this.category = category;
        this.state = state;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BiddingState getState() {
        return state;
    }

    public void setState(BiddingState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startPrice=" + startPrice +
                ", finalPrice=" + finalPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trader=" + trader +
                ", buyer=" + buyer +
                ", category=" + category +
                ", state=" + state +
                '}';
    }
}
