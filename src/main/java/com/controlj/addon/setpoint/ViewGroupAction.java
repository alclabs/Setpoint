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
import com.controlj.green.addonsupport.InvalidConnectionRequestException;
import com.controlj.green.addonsupport.access.aspect.Group;
import com.controlj.green.addonsupport.access.util.Acceptors;
import com.controlj.green.addonsupport.access.util.LocationSort;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ViewGroupAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("groups", getGroupList(request));

        return mapping.findForward("success");
    }

    public List<GroupWrapper> getGroupList(HttpServletRequest request) throws InvalidConnectionRequestException, SystemException, ActionExecutionException {
        SystemConnection connection = DirectAccess.getDirectAccess().getUserSystemConnection(request);
        final List<GroupWrapper> result = new ArrayList<GroupWrapper>();

        connection.runReadAction(new ReadAction() {
            public void execute(SystemAccess systemAccess) throws Exception {
                Location groupRoot = systemAccess.getTree(SystemTree.Schedule_Group).getRoot();
                Collection<Group> groups = groupRoot.find(Group.class, Acceptors.<Aspect>acceptAll());

                for (Group group : groups) {
                    result.add(new GroupWrapper(getGroupLabel(group.getLocation(), groupRoot), group.getLocation().getTransientLookupString()));
                }
            }
        });

        Collections.sort(result, new Comparator<GroupWrapper>() {
            @Override
            public int compare(GroupWrapper gw1, GroupWrapper gw2) {
                return gw1.getName().compareTo(gw2.getName());
            }
        });
        return result;
    }

    private String getGroupLabel(Location groupLocation, Location groupRoot) {
        String displayPath = groupLocation.getDisplayPath();
        String rootPath = groupRoot.getDisplayPath();
        return displayPath.substring(rootPath.length()+2);
    }

    public List<GroupWrapper> getFakeGroupList(HttpServletRequest request) {
        List<GroupWrapper> list = new ArrayList<GroupWrapper>();
        list.add(new GroupWrapper("High Schools", null));
        list.add(new GroupWrapper("Middle Schools", null));
        list.add(new GroupWrapper("Elementary Schools", null));

        return list;
    }
}
