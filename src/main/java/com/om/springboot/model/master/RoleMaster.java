package com.om.springboot.model.master;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "role_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role"
        })
})
public class RoleMaster extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @NotBlank
    @Size(max = 45)
    private String role;
}

