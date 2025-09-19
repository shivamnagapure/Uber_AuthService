package UberProject_AuthService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class PassengerReview extends BookingReview {

    @Column(nullable = false)
    private String passengerReviewContent ;

    @Column(nullable = false)
    private Double passengerRating ;

}
