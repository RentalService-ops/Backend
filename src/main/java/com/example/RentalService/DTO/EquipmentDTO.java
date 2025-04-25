// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.RentalService.DTO;

import java.math.BigDecimal;

import com.example.RentalService.model.Equipment;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;



@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EquipmentDTO {
   
   //Equipment quantity
   private int quantity;
   
   //Equipment description.
   private String description;
   
   //Equipment price per day.
   private BigDecimal pricePerDay;
   
   //Id of the equipment.
   private int equipmentId;
   
   //Name of the equipment.
   private String name;
   
   //ImageUrl of the Equipment Image
   private String imageUrl;
   
   //category name of the category the equipment belongs to.
   private String categoryName;
   
   private String userName;
   
   //Default Constructor
   public EquipmentDTO() {
   }
   
   /**
    * Creates a new EquipmentDTO object with the given Equipment entity.
    * @Param equipment: The Equipment entity.
    * */
   public EquipmentDTO(Equipment equipment) {
      this.setQuantity(equipment.getQuantity());
      this.setDescription(equipment.getDescription());
      this.setPricePerDay(equipment.getPricePerDay());
      this.setName(equipment.getName());
      this.setEquipmentId(equipment.getEquipmentId());
      this.setImageUrl(equipment.getImageUrl());
      this.setCategoryName(equipment.getCategory().getName());
      this.setUserName(equipment.getUser().getUsername());
   }
}
