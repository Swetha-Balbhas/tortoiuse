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
@Table(name = "resource_manager_profile", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"
        })
})

public class ResourceManagerProfile extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Size(max= 75)
    private String email;

    @NotNull
    @Size(max= 200)
    private String password;

    private Long roleId;

}
