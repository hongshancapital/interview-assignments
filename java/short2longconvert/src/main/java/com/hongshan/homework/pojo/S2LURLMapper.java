package com.hongshan.homework.pojo;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="S2L_URL_Mapper")
public class S2LURLMapper {

    @Id
    @Column(name = "shortURL")
    private String shortURL;

    @Column(name = "longURL")
    private String longURL;
}
