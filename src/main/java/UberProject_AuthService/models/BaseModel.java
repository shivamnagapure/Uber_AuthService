package UberProject_AuthService.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseModel {

    // @Id this annotation make Id field to primary key , and Every entity/table must have primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY : DataBase auto-generates Id , hibernate will not provide to database
    private Long id ;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt ;

    @Column(nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP) //For storing different type of DATA(DATE , TIME , DATE + TIME)
    private Date updatedAt ;

}

