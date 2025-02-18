package com.luca.film.common;

import lombok.*;

import java.util.List;

/**
 * A generic class for paginated responses.
 *
 * @param <T> the type of content contained in the page response
 */
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
