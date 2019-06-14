package com.sergey.zhuravlev.pizza.dto.pageable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class PageDto<T> {

    public static Integer DEFAULT_PAGE_SIZE = 20;

    private Integer size;

    private Integer number;

    private Integer totalPages;

    private Integer numberOfElements;

    private Boolean first;

    private Boolean last;

    private Collection<T> content;

    public PageDto(Page<T> page) {
        this.size = page.getSize();
        this.number = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.content = page.getContent();
    }

}
