/*
 * Copyright (c) 2010 Automated Logic Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
