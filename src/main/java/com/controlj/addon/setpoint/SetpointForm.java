package com.controlj.addon.setpoint;

import org.apache.struts.action.ActionForm;

public class SetpointForm extends ActionForm
{
   private String occHeat;
   private String occCool;
   private String unoccHeat;
   private String unoccCool;
   private String groupName;
   private String groupPath;

   public SetpointForm() {}
   
   public void set(GroupSetpoint gs)
   {
      occHeat = Float.toString(gs.getOccHeat());
      occCool = Float.toString(gs.getOccCool());
      unoccHeat = Float.toString((gs.getUnoccHeat()));
      unoccCool = Float.toString(gs.getUnoccCool());
      groupName = gs.getGroupName();
      groupPath = gs.getGroupPath();
   }
   
   public String getOccHeat()
   {
      return occHeat;
   }

   public void setOccHeat(String occHeat)
   {
      this.occHeat = occHeat;
   }

   public String getOccCool()
   {
      return occCool;
   }

   public void setOccCool(String occCool)
   {
      this.occCool = occCool;
   }

   public String getUnoccHeat()
   {
      return unoccHeat;
   }

   public void setUnoccHeat(String unoccHeat)
   {
      this.unoccHeat = unoccHeat;
   }

   public String getUnoccCool()
   {
      return unoccCool;
   }

   public void setUnoccCool(String unoccCool)
   {
      this.unoccCool = unoccCool;
   }

   public String getGroupName()
   {
      return groupName;
   }

   public void setGroupName(String groupName)
   {
      this.groupName = groupName;
   }

   public String getGroupPath()
   {
      return groupPath;
   }

   public void setGroupPath(String groupPath)
   {
      this.groupPath = groupPath;
   }
}
