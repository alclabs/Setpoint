Setpoint
========

The Setpoint Add-On is intended to demonstrate reading / writing of setpoints and determining schedule group
membership. It allows a user to specify setpoint values that will be applied to all the members of a schedule
group. It is using schedule groups in an unconventional way to group equipment for a purpose other than scheduling.

This Add-On is implemented using a popular web application framework, Struts.

Try it out
----------

Deploy the Setpoint sample add-on by executing the 'deploy' task and starting (if necessary) the server.

Browse to `http://yourserver/setpoint`. This should present a login page. Log in with any valid operator and
password. The left hand side should list the available schedule groups. Click on a group, then specify occupied and
unoccupied setpoints on the right hand side. When you click apply, these setpoints will be applied to all equipment
in the setpoint group.

Important Lessons
-----------------

`GroupSetpoint.writeToComponents` is the method that actually writes to all of the SetPoint objects. Note that this
method is called from `GroupSetpoint.save` (from inside of a `SystemConnection.runWriteAction`). This save method
also gets a `DataStore` and saves the setpoints so they can be recalled for this group later.