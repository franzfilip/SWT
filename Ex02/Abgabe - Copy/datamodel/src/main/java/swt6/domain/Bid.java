package swt6.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bid {
    @Id @GeneratedValue
    private long id;
    private double amount;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer bidder;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Article article;
    private LocalDateTime timestamp;

    public Bid(){}

    public Bid(double amount, Customer bidder, Article article, LocalDateTime timestamp) {
        this.amount = amount;
        this.bidder = bidder;
        this.article = article;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
