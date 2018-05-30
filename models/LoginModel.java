package br.com.app.tanamao.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@ToString
@Entity(name = "logins")
public class LoginModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @Email
    @NotNull(message = "Email não pode ser nulo!")
    @Column(unique = true)
    private String email;
    @NotNull(message = "Campo senha não pode ser nulo!")
    private String password;
    @NotNull(message = "Campo role não pode ser nulo")
    private String role;
    @Column(unique = true)
    private String token; // token para refatorar senha
    @Column(nullable = false)
    private String autorizado;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
