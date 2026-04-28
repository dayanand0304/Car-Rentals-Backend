package com.CarRentalSystem.CarRentals.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Paginated response")
public class PageResponse<T> {

    @Schema(description = "List of items in current page")
    private List<T> content;

    @Schema(example = "0", description = "Current page number")
    private int page;

    @Schema(example = "10", description = "Page size")
    private int size;

    @Schema(example = "100", description = "Total number of elements")
    private long totalElements;

    @Schema(example = "10", description = "Total number of pages")
    private int totalPages;

    @Schema(example = "false", description = "Is this the last page")
    private boolean last;
}