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

import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.access.aspect.Group;
import com.controlj.green.addonsupport.access.aspect.SetPoint;
import com.controlj.green.addonsupport.access.util.Acceptors;
import com.controlj.green.addonsupport.access.value.InvalidValueException;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

public class GroupSetpoint
{
   private String groupPath;
   private SystemConnection connection;
   private float occHeat;
   private float occCool;
   private float unoccHeat;
   private float unoccCool;
   private String groupName;

   public GroupSetpoint(String groupPath, SystemConnection connection) throws SystemException, ActionExecutionException
   {
      this.groupPath = groupPath;
      this.connection = connection;
      loadName();
   }

   public float getOccHeat()
   {
      return occHeat;
   }

   public void setOccHeat(float occHeat)
   {
      this.occHeat = occHeat;
   }

   public float getOccCool()
   {
      return occCool;
   }

   public void setOccCool(float occCool)
   {
      this.occCool = occCool;
   }

   public float getUnoccHeat()
   {
      return unoccHeat;
   }

   public void setUnoccHeat(float unoccHeat)
   {
      this.unoccHeat = unoccHeat;
   }

   public float getUnoccCool()
   {
      return unoccCool;
   }

   public void setUnoccCool(float unoccCool)
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

   private Collection<SetPoint> findSetpoints(SystemAccess access) throws UnresolvableException, NoSuchAspectException
   {
      Location groupLocation = access.getTree(SystemTree.Schedule_Group).resolve(groupPath);
      Group group = groupLocation.getAspect(Group.class);
      List<Location> members = group.getAffectedEquipment();

      return access.find(members, SetPoint.class, Acceptors.acceptAll());
   }

   private void writeToComponents(SystemAccess access) throws UnresolvableException, InvalidValueException, NoSuchAspectException
   {
      Collection<SetPoint> setpoints = findSetpoints(access);
      for (SetPoint setpoint : setpoints)
      {
         setpoint.getOccupiedHeating().set(occHeat);
         setpoint.getOccupiedCooling().set(occCool);
         setpoint.getUnoccupiedHeating().set(unoccHeat);
         setpoint.getUnoccupiedCooling().set(unoccCool);
         setpoint.validate();
      }
   }

   public void save() throws SystemException, ActionExecutionException, WriteAbortedException
   {
      connection.runWriteAction(FieldAccessFactory.newFieldAccess(), "Changing all setpoints in group "+groupName, new WriteAction()
      {
         public void execute(WritableSystemAccess access) throws Exception
         {
            writeToComponents(access);

            DataStore store = access.getDataStore(access.getTree(SystemTree.Schedule_Group).resolve(groupPath), "setpoint");
            PrintWriter writer = store.getWriter();
            writer.println(occHeat);
            writer.println(occCool);
            writer.println(unoccHeat);
            writer.println(unoccCool);
            writer.close();
         }
      });
   }

   public void load() throws SystemException, ActionExecutionException
   {
      connection.runReadAction(new ReadAction()
      {
         public void execute(SystemAccess systemAccess) throws Exception
         {
            DataStore store = systemAccess.getDataStore(systemAccess.getTree(SystemTree.Schedule_Group).resolve(groupPath), "setpoint");
            BufferedReader reader = store.getReader();
            String next;

            next = reader.readLine();
            if (next == null)
               return;
            occHeat = Float.parseFloat(next);

            next = reader.readLine();
            if (next == null)
               return;
            occCool = Float.parseFloat(next);

            next = reader.readLine();
            if (next == null)
               return;
            unoccHeat = Float.parseFloat(next);

            next = reader.readLine();
            if (next == null)
               return;
            unoccCool = Float.parseFloat(next);

         }
      });
   }

   private void loadName() throws SystemException, ActionExecutionException
   {
      connection.runReadAction(new ReadAction()
      {
         public void execute(SystemAccess systemAccess) throws Exception
         {
            Location groupLocation = systemAccess.getTree(SystemTree.Schedule_Group).resolve(groupPath);
            groupName = groupLocation.getDisplayName();
         }
      });
   }
}
