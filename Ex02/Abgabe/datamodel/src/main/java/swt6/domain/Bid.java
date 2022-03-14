package swt6.domain;

import java.time.LocalDateTime;

public class Bid {
    private long id;
    private double amount;
    private Customer bidder;
    private Article article;
    private LocalDateTime timestamp;
}
