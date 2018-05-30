package br.com.app.tanamao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    //@NotNull(message = "Nome do usuario não pode ser nulo")
    private String name;
    //@NotNull(message = "CPF não pode ser nulo")
    @Size(min = 11, max = 20, message = "CPF precisa ter 11 digitos")
    private String cpf;
    //@NotNull(message = "Data de nascimento não pode ser nulo")
    private String birth;
    //@NotNull(message = "telefone não pode ser nulo")
    private String phone;
    //@NotNull(message = "Data de registro não pode ser nula")
    private String registration_date;

    @OneToOne(cascade = CascadeType.ALL)
    private AvatarModel avatar;

    @OneToOne(cascade = CascadeType.ALL)
    private LoginModel login;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressModel address;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE)
    private List<DrugstoreModel> favorites_drugstore;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE)
    private List<OrderModel> my_orders;

}
