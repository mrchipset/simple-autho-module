package simple.autho.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private int userId;

    @Column(name="username", length = 128, nullable = false, unique = true)
    private String userName;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(name = "mobile", length = 16, nullable = false, unique = true)
    private String mobilePhone;
    private Date createDate;
    @Column(name="last_login")
    private Date lastLoginDate;
    @Column(name="passwd", length = 32, nullable = false, unique = true)
    private String passWd;
}
