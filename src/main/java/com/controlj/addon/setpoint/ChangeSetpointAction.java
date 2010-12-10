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