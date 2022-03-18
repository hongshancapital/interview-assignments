package com.yofei.shortlink.dao.entity;

import com.yofei.shortlink.common.Persistable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "link")
public class LinkEntity extends Persistable {

    @Column(nullable = false)
    private String md5;

    @Column(nullable = false)
    private String url;

}
