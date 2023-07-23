package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "이름은 필수입니다")
    private String name;

    @NotNull(message = "가격은 필수입니다")
    private int price;

    private int stockQuantity;

    private String author;
    private String isbn;
}
