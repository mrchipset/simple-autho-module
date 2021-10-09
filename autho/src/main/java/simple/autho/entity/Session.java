package simple.autho.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session implements Serializable{
    private String userName;
    private String authencation;
    private Date expiredDate;
}

