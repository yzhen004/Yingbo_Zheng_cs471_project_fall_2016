package yingbo_zheng_cs471_project_fall_2016;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;
import java.util.Iterator;

import javax.swing.JTable;

/*
*This class simulates a scheduling system priority queue, or ready queue,.
*All processes wait in the queue based on arrival time and priority
*number.
*/

public class Dispatcher {

    //Running process object
   static MyProcess runningProcess = null;
   //
   static MyProcess previousProcess = null;

   static PriorityQueue<MyProcess> readyQueue;

   //Gantt chart
   static Vector<MyProcess> Gantt;
   
   long lastRunningID;
   private String contextSwitchInfo="\n";
   
   //
   Dispatcher() 
   {

	   Comparator<MyProcess> comparator = new ProcessComparator();
	   
	   readyQueue = new PriorityQueue<MyProcess>(10, comparator);
	   Gantt = new Vector<MyProcess>();

   }
   //
   public String Context()
   {
	   return contextSwitchInfo;
   }
   
   //ToString function
   @Override
   public String toString()
   {
	   StringBuilder sb = new StringBuilder();
	   
	   sb.append("Running Process:\n");
	   sb.append(runningProcess.toString());
	   
	   // create iterator from the queue
	   Iterator<MyProcess> it = readyQueue.iterator();
	   
	   sb.append("\n\nBlocked List:\n");
	      
	   while (it.hasNext())
           {
		   sb.append(it.next().toString());
	   }
	   
//	   Iterator<MyProcess> itr = Gantt.iterator();
//	   
//	   sb.append("\n\nGantt Chart:\n");
//	      
//	   while (itr.hasNext()){
//		   sb.append(itr.next().toString());
//	   }

	   return sb.toString();
   }
   
   //Update context switch
   private void UpdateContextInfo()
   {
	   StringBuilder sb = new StringBuilder();
	   sb.append("Context Switched:\n");
	   sb.append("New Running Process:\n");
	   sb.append(runningProcess.toString());	 
	   sb.append("Previous Running Process:\n");
	   sb.append(previousProcess.toString());	
	   sb.append("\n\n");
	   sb.append(contextSwitchInfo);
	   contextSwitchInfo = sb.toString();
   }
   
   //Add a new process to dispatcher
   public void AddProcess(MyProcess p, boolean auto)
   {
	   //Add to the queue
	   readyQueue.add(p);
	   
           //If 
	   if (auto) 
               return;
	   
	   //No process is running
	   if (runningProcess==null)
	   {
		   runningProcess= readyQueue.remove();
		   runningProcess.start();
		   
		   Gantt.add(runningProcess);
	   }
	   else
	   {
		   lastRunningID = runningProcess.getID();
		   previousProcess = runningProcess;
				   
		   //Preemptive mode -- running process always stops to compare with the new comer
		   runningProcess.stop();
		   
		   //check the running process completed
		   if(!runningProcess.getCompleted())
			   readyQueue.add(runningProcess);
		   
		   runningProcess= readyQueue.remove();
		   runningProcess.start();
		   
		   //If not the same process restarts
		   if(lastRunningID != runningProcess.getID())
		   {
			   UpdateContextInfo();
			   //create a copy so the start time won't change
		      Gantt.add(new MyProcess(runningProcess) );	
		   }
	   }	   
   }
   
   //Check running process to see whether it is complete
   //Complete return true;
   public boolean checkRunning()
   {
	   //No process is running
	   if (runningProcess== null)
	   {
		   runningProcess= readyQueue.remove();
		   runningProcess.start();
		   
		   Gantt.add(runningProcess);
		   return false;
	   }
	   
	   //Current running process completes
	   if (runningProcess.getCompleted())
	   {
                   //If queue not empty
		   if(readyQueue.size()>0)
		   {
			   previousProcess=runningProcess;
			   //get the next process from queue
			   runningProcess= readyQueue.remove();
			   
			   //if queue not empty
			   if (runningProcess!=null)
			   {
				   runningProcess.start();
				   UpdateContextInfo();
				   //update running history - Gantt chart
				   Gantt.add(runningProcess);	
			   }
		   }
		   return true;
	   }
	   
	   return false;
   }
   
   public Object[][] getReadyQueue()
   {
	 int size = readyQueue.size();
	//If ready queue is empty, return null
        if (size==0) 
            return null;
    	Object rowData[][] = new Object[size][6];
    	
        
    	Object[] mp= readyQueue.toArray();
    	for(int i=0; i<size;i++)
    	{
    		mp[i]=(MyProcess)mp[i];
    	}
        //Sort MyProcess array
    	Arrays.sort(mp);
    	
    	for(int i=0; i<size;i++)
    	{
    		MyProcess p=(MyProcess)mp[i];
    		rowData[i][0]=p.getID();
    		rowData[i][2]=p.getPriority();
    		rowData[i][1]=p.getName();
    		rowData[i][3]=p.getBurstTime();   
    		rowData[i][4]=p.getArrivalTime();
//    		rowData[i][5]=p.getCompleted();
//    		rowData[i][6]=p.getRunningTime();
    		rowData[i][5]=p.getWaitingTime();
//    		rowData[i][7]=p.getTurnaroundTime(); 
    	}
    	return rowData;
   }

   public Object[][] getGantt()
   {
	    int size = Gantt.size();
    	Object rowData[][] = new Object[size][4];
    	Object[] mp= Gantt.toArray();
    	
    	for(int i=0; i<size;i++)
    	{
    		MyProcess p=(MyProcess)mp[i];    		
    		rowData[i][0]=p.getID();
    		rowData[i][1]=p.getName();
    		rowData[i][2]=p.getPriority();
    		rowData[i][3]=p.getStartTime();
    	}
    	
    	return rowData;
    
   }
}