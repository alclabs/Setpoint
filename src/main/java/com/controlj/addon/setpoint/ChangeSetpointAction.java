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

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.access.value.InvalidValueException;
import com.controlj.green.addonsupport.access.value.ErrorMessage;

import java.util.List;

public class ChangeSetpointAction extends Action
{
   @Override
   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      SetpointForm form = (SetpointForm)actionForm;
      String path = request.getParameter("grouppath");

      if (path == null)
      {
         path  = form.getGroupPath();
      }

      SystemConnection connection = DirectAccess.getDirectAccess().getUserSystemConnection(request);
      GroupSetpoint gs = new GroupSetpoint(path, connection);

      try
      {
         gs.setOccHeat(Float.parseFloat(form.getOccHeat()));
         gs.setOccCool(Float.parseFloat(form.getOccCool()));
         gs.setUnoccHeat(Float.parseFloat(form.getUnoccHeat()));
         gs.setUnoccCool(Float.parseFloat(form.getUnoccCool()));
         gs.save();
         form.set(gs);
      }
      catch (ActionExecutionException e)
      {
         Throwable cause = e.getCause();
         ActionMessages messages = new ActionMessages();
         if (cause instanceof InvalidValueException)
         {
            List<ErrorMessage> errors = ((InvalidValueException) cause).getErrors();
            for (ErrorMessage error : errors)
            {
               messages.add("value", new ActionMessage(error.toString(), false));
            }
         }
         addErrors(request, messages);         
         return mapping.findForward("error");
      }
      catch (Exception e)
      {

         ActionMessages messages = new ActionMessages();
         messages.add("value", new ActionMessage("Invalid values:"+e.getMessage(), false));
         addErrors(request, messages);
         
         return mapping.findForward("error");
      }

      return mapping.findForward("success");
   }
}