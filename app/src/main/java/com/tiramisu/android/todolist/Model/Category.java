package com.tiramisu.android.todolist.Model;

import com.orm.SugarRecord;

/**
 * Created by Pranal on 19-08-2017.
 */

public class Category extends SugarRecord {
   public Long categoryID;
   public String categoryName;


   public Category(){

   }

   public Category(String category,Long cID) {
      this.categoryName = category;
      this.categoryID=cID;
   }



   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String category) {
      this.categoryName = category;
   }

   public Long getCategoryID() {
      return categoryID;
   }

   public void setCategoryID(Long cID) {
      this.categoryID = cID;
   }




}
