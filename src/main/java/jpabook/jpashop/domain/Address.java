package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
public class Address {

    private Long id;
    private String city;
    private String street;
}
