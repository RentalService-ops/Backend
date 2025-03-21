// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.RentalService.DTO;

import java.math.BigDecimal;

import com.example.RentalService.model.Equipment;
import com.fasterxml.jackson.annotation.JsonInclude;


//DTO for Equipment entity.
@JsonInclude(JsonInclude.Include.NON_NULL)
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
   }

   /**
    * Gets the quantity of the equipment.
    * 
    * return the quantity of the equipment.
    */
   public int getQuantity() {
      return this.quantity;
   }

   /**
    * Sets the quantity of the equipment.
    * 
    * @param quantity the quantity of the equipment to be set.
    */
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   /**
    * Gets the description of the equipment.
    * 
    * return the description of the equipment.
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * Sets the description of the equipment.
    * 
    * @param description the description of the equipment to be set.
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Gets the price per day for renting the equipment.
    * 
    * return the price per day of the equipment.
    */
   public BigDecimal getPricePerDay() {
      return this.pricePerDay;
   }

   /**
    * Sets the price per day for renting the equipment.
    * 
    * @param pricePerDay the price per day to be set for the equipment.
    */
   public void setPricePerDay(BigDecimal pricePerDay) {
      this.pricePerDay = pricePerDay;
   }

   /**
    * Gets the equipment ID.
    * 
    * return the unique identifier of the equipment.
    */
   public int getEquipmentId() {
      return this.equipmentId;
   }

   /**
    * Sets the equipment ID.
    * 
    * @param equipmentId the unique identifier of the equipment to be set.
    */
   public void setEquipmentId(int equipmentId) {
      this.equipmentId = equipmentId;
   }

   /**
    * Gets the name of the equipment.
    * 
    * return the name of the equipment.
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the name of the equipment.
    * 
    * @param name the name of the equipment to be set.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the image URL of the equipment.
    * 
    * return the image URL of the equipment.
    */
   public String getImageUrl() {
      return this.imageUrl;
   }

   /**
    * Sets the image URL of the equipment.
    * 
    * @param imageUrl the URL of the image to be set for the equipment.
    */
   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

}
