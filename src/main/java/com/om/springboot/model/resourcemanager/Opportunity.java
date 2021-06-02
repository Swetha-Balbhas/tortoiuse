package com.om.springboot.model.resourcemanager;


import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "opportunity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"
        })
})

public class Opportunity extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long token;
    @NotNull
    @Size(max= 75)
    private String role;

    @NotNull
    @Size(max= 75)
    private String department;

    @NotNull
    @Size(max= 500)
    private String jobDescription;

    @NotNull
    private Long positions;

    @NotNull
    @Size(max= 500)
    private String skills;

    @NotNull
    @Size(max= 500)
    private String location;

    @NotNull
    @Size(max= 75)
    private String experience;

    private String status;

private Long applicationCount;

}
