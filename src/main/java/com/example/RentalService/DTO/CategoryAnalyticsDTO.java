package com.example.RentalService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryAnalyticsDTO {
	
	private long totalEquipmentsAssociatedWithCategory;
	
	private String categoryName;
}
