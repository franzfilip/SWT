package swt6.domain;

import java.time.LocalDateTime;

public class Article {
    private long id;
    private String name;
    private String description;
    private double startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Customer trader;
    private Customer buyer;
}
