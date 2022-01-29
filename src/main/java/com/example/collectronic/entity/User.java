package com.example.collectronic.entity;

import com.example.collectronic.entity.enums.ERole;
//import com.example.collectronic.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name="collectronicuser")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, updatable = false)
    private String username;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String lastname;
    @Column( unique = true)
    private String email;
    @Column (length = 3000)
    private String password;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(  name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "collectronicuser", orphanRemoval = true)
    private List<UserCollection> userCollections = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(){

    }
    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Security
     */

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
