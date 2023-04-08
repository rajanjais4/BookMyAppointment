package com.indra.bookMyAppointment.model.common;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements UserDetails {
    @Id
    private String _id;
    @NonNull
    private String name;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @Indexed(unique = true)
    @NonNull
    private String phoneNumber;
    @NonNull
    private String password;
    private List<Address> address;
    private Role role;

    //    @Enumerated(EnumType.STRING)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.ADMIN.name()),
                new SimpleGrantedAuthority(Role.PROFESSIONAL.name()),
                new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getUsername() {
        return getPhoneNumber();
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
