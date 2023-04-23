package com.indra.bookMyAppointment.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "person")
public class Person implements UserDetails {
    @Id
    private String _id;
    @NonNull
    @Indexed()
    private String name;
    @NonNull
    private String email;
    @Indexed()
    @NonNull
    private String phoneNumber;
    @NonNull
    @JsonIgnore
    private String password;
    @Indexed()
    private List<Address> address;
    @NonNull
    @Indexed()
    private Role role;




    //    Spring security
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.ADMIN.name()),
                new SimpleGrantedAuthority(Role.PROFESSIONAL.name()),
                new SimpleGrantedAuthority(Role.USER.name()));
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return getPhoneNumber();
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
