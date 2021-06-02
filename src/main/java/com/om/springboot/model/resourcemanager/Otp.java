package com.om.springboot.model.resourcemanager;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "otp", uniqueConstraints = {

        @UniqueConstraint(columnNames = {
                "email"
        })
})

public class Otp extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String email;

    @NotBlank
    @Size(max = 11)
    private String otp;

    @CreatedDate
    private Instant resentAt;

    @Size(max = 11)
    private Integer resentCount;

}
