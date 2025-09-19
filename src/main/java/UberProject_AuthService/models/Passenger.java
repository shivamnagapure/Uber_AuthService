package UberProject_AuthService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Passenger extends BaseModel{

    private String name ;

    @Column(nullable = false)
    private String email ;

    @Column(nullable = false)
    private String password; // encrypted password

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "passenger" )
    private List<Booking> bookings ; // 1 : m relationship
}