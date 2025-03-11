// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.RentalService.DTO;

import java.math.BigDecimal;

import com.example.RentalService.model.Equipment;

public class EquipmentDTO {
   private int quantity;
   private String description;
   private BigDecimal pricePerDay;
   private int equipmentId;
   private String name;
   private String imageUrl;

   public EquipmentDTO() {
   }

   public EquipmentDTO(Equipment equipment) {
      this.setQuantity(equipment.getQuantity());
      this.setDescription(equipment.getDescription());
      this.setPricePerDay(equipment.getPricePerDay());
      this.setName(equipment.getName());
      this.setEquipmentId(equipment.getEquipmentId());
      this.setImageUrl(equipment.getImageUrl());
   }

   public int getQuantity() {
      return this.quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public BigDecimal getPricePerDay() {
      return this.pricePerDay;
   }

   public void setPricePerDay(BigDecimal pricePerDay) {
      this.pricePerDay = pricePerDay;
   }

   public int getEquipmentId() {
      return this.equipmentId;
   }

   public void setEquipmentId(int equipmentId) {
      this.equipmentId = equipmentId;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImageUrl() {
      return this.imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }
}
