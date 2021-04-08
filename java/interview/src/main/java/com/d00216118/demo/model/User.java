package com.d00216118.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 10:58 上午 2021/3/29
 **/
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
