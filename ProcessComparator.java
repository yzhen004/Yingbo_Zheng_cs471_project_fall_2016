/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yingbo_zheng_cs471_project_fall_2016;

import java.util.Comparator;

/*
*Used by dispatcher to order and sort the processes
*in the ready queue.
*/

public class ProcessComparator implements Comparator<MyProcess>
{

	@Override
	public int compare(MyProcess x, MyProcess y) 
        {	
            //bigger priority number, lower priority
            if (x.getPriority() > y.getPriority())
            {
                return 1;
            }
            if (x.getPriority() < y.getPriority())
            {
                return -1;
            }

            if (x.getArrivalTime() > y.getArrivalTime())
            {
                return 1;
            }

            return -1;
	}
}