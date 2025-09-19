package UberProject_AuthService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_review" ,
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id" , "booking_id"})})
@Inheritance(strategy = InheritanceType.JOINED) // Defines how JPA maps an inheritance hierarchy into DB tables.
public class BookingReview extends BaseModel{

    //cascade decides how operations applied on one entity should be propagated/affected to related entities .
    @OneToOne(cascade = {CascadeType.PERSIST , CascadeType.REMOVE} , fetch = FetchType.LAZY)
    private Booking booking ;

    @Column(nullable = false) //Ensures that the column must always have a value when inserting or updating a record.
    private String content ;

    @Column(nullable = false)
    private Double rating ;

}
