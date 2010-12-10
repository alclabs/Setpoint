package com.controlj.addon.setpoint;

import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.InvalidConnectionRequestException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewGroupAction extends Action
{
   @Override
   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      request.setAttribute("groups", getGroupList(request));

      return mapping.findForward("success");
   }

   public List<GroupWrapper> getGroupList(HttpServletRequest request) throws InvalidConnectionRequestException, SystemException, ActionExecutionException
   {
      SystemConnection connection = DirectAccess.getDirectAccess().getUserSystemConnection(request);
      final List<GroupWrapper> result = new ArrayList<GroupWrapper>();

      connection.runReadAction(new ReadAction()
      {
         public void execute(SystemAccess systemAccess) throws Exception
         {
            Collection<Location> groups = systemAccess.getTree(SystemTree.Schedule_Group).getRoot().getChildren();
            for (Location group : groups)
            {
               result.add(new GroupWrapper(group.getDisplayName(), group.getTransientLookupString()));
            }
         }
      });

      return result;
   }

   public List<GroupWrapper> getFakeGroupList(HttpServletRequest request)
   {
      List<GroupWrapper> list = new ArrayList<GroupWrapper>();
      list.add(new GroupWrapper("High Schools", null));
      list.add(new GroupWrapper("Middle Schools", null));
      list.add(new GroupWrapper("Elementary Schools", null));

      return list;
   }
}
