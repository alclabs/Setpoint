package com.controlj.addon.setpoint;

public class GroupWrapper
{
   private String name;
   private String path;

   public GroupWrapper(String name, String path)
   {
      this.name = name;
      this.path = path;
   }

   public String getName()
   {
      return name;
   }

   public String getPath()
   {
      return path;
   }
}
