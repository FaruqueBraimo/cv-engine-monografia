package Engine.Application.Form.cv.engine.model;


import javax.persistence.*;
import java.util.UUID;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private UUID roleId;
    @Column
    private String name;

    public UUID getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
