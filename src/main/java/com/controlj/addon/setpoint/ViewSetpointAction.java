package com.controlj.addon.setpoint;

import com.controlj.green.addonsupport.access.DirectAccess;
import com.controlj.green.addonsupport.access.SystemConnection;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewSetpointAction extends Action
{
   @Override
   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      final String path = request.getParameter("grouppath");

      SystemConnection connection = DirectAccess.getDirectAccess().getUserSystemConnection(request);
      GroupSetpoint gs = new GroupSetpoint(path, connection);
      gs.load();
                                
      ((SetpointForm)actionForm).set(gs);      

      return mapping.findForward("success");
   }
}
