package sisventaGroup.sisventaArtifact.modules.roles;



import jakarta.persistence.*;
import sisventaGroup.sisventaArtifact.modules.users.User;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    private Set<User> users=new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers(){
        return users;
    }
    public void setUsers(Set<User> users){
        this.users=users;
    }
}
