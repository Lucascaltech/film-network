package com.luca.film.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer size;
    private Long totalElements;
    private long totalPages;
    private boolean first;
    private boolean last;
}
