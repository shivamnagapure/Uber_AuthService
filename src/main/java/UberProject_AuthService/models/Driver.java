package UberProject_AuthService.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Driver extends BaseModel {

    private String name ;

    @Column(nullable = false , unique = true)
    private String licenseNumber ;

    private String phoneNumber ;



    /*
            The driver_id column in booking table creates the link.
            When Hibernate loads a Driver, it looks in the booking table for rows where driver_id = driver.id, and fills the bookings list.
            When you set booking.setDriver(driver) in code, Hibernate stores the driver’s ID in the booking.driver_id column.
     */
    @OneToMany(mappedBy = "driver" )
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings ;

}

