package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
