package com.addr.node.bean;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

@Data
public class DomainBo {
    private String shortDomain;
    private String longDomain;
}
