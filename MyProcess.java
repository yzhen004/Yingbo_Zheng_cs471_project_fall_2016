package yingbo_zheng_cs471_project_fall_2016;

/*
*This module creates virtual processes, it provides
*a simulation of a process, not an actual real
*process.
*/

public class MyProcess implements Comparable<MyProcess>
{
	private long id;
	private long priority;
	private long burstTime;
	private long arrivalTime;
	private long waitingTime;
	private long turnarondTime;
	private long runningTime;
	private long completeTime;
	private long remainingTime;
	private long lastCheckingTime;	
	private boolean running;
	private boolean completed = false;
	private boolean started = false;
	private boolean stopped = false;
	private String name;
	private long startTime;
	private long stopTime;
	
	MyProcess(long _id, String  _name, long _priority, long _burstTime, long _arrivalTime)
	{
		id=_id;
		name=_name;
		priority =_priority;
		burstTime=_burstTime;
		arrivalTime=_arrivalTime;
		remainingTime = _burstTime;
		runningTime=0;
		waitingTime=0;
		stopTime=0;
		lastCheckingTime=0;
		running=false;
		started=false;
		stopped=false;
	}
	
	MyProcess(MyProcess p)
	{
		id=p.id;
		name=p.name;
		priority =p.priority;
		burstTime=p.burstTime;
		arrivalTime=p.arrivalTime;
		remainingTime = p.burstTime;
		runningTime=p.runningTime;
		waitingTime=p.waitingTime;
		lastCheckingTime=p.lastCheckingTime;	
		startTime=p.startTime;
	}
	@Override
	public String toString()
	{
		String d= "\t";
		String s ="id\tname\tpriority \tburst Time \tarrival Time \trunning Time \tremaining  \n" + 
				id + d + name + d + priority  + d + burstTime  + d + 
				arrivalTime  + d + runningTime  + d + remainingTime   + "\n";
		return s;
	}
	public long getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
		
	public long getPriority()
	{
		return priority;
	}
	
	public long getBurstTime()
	{
		return burstTime;
	}
	
	public long getStartTime()
	{
		return startTime;
	}
		
	public long getArrivalTime()
	{
		return arrivalTime;
	}

        //Function to simiulate process start
	public void start()
	{
		startTime = System.currentTimeMillis() / 1000-Main.System_Start_Time;
		lastCheckingTime=0;
		running = true;
		started = true;
	}
	
        //Function to simulate process stop and track the time
	public void stop()
	{
                
		stopped=true;
		stopTime = System.currentTimeMillis() / 1000-Main.System_Start_Time;
		lastCheckingTime=0;
		if(lastCheckingTime==0) 
                    lastCheckingTime=startTime;
		runningTime += stopTime-lastCheckingTime;
		lastCheckingTime=0;
		remainingTime = burstTime - runningTime;
		if (remainingTime<=0)
		{
			completed = true;
			completeTime = stopTime;	
			waitingTime = completeTime - arrivalTime-burstTime;
		}
		
		running = false;
	}
	
        public void terminate()
        {
		stopped=true;
		stopTime = System.currentTimeMillis() / 1000-Main.System_Start_Time;
		lastCheckingTime=0;
		if(lastCheckingTime==0) 
                    lastCheckingTime=startTime;
		runningTime += stopTime-lastCheckingTime;
		lastCheckingTime=0;
                burstTime = runningTime;
		remainingTime = 0;

                completed = true;
                completeTime = stopTime;	
                waitingTime = completeTime - arrivalTime-burstTime;

		running = false;           
        }
        
	public boolean getCompleted()
	{
            //If complete
		if(completed) 
                    return true;
                //If not running
		if(!running)
		{
			if(lastCheckingTime==0) 
                            lastCheckingTime=stopTime;
			if(lastCheckingTime==0) 
                            lastCheckingTime=arrivalTime;
			
			long checkTime = System.currentTimeMillis() / 1000-Main.System_Start_Time;	
			waitingTime = waitingTime + checkTime-lastCheckingTime;
			lastCheckingTime=checkTime;			
			return false;
		}
		else
		{
			if(lastCheckingTime==0) 
                            lastCheckingTime=startTime;
			
			long checkTime = System.currentTimeMillis() / 1000-Main.System_Start_Time;	
			runningTime = runningTime + checkTime-lastCheckingTime;
			lastCheckingTime=checkTime;
			remainingTime = burstTime - runningTime;
			if (remainingTime<=0)
			{
				remainingTime =0;
				completed =true;
				completeTime = checkTime;	
				runningTime =burstTime;
				waitingTime = completeTime - arrivalTime-burstTime;
				turnarondTime = completeTime - arrivalTime;
			}
			
			return completed;
		}
	}
        
        //Calculate turnaround time
	public long getTurnaroundTime()
	{
            //If process completed
		if(completed) 
                    return turnarondTime;
		
		return 0;
	}
        
        //Calculate running time
	public long getRunningTime()
	{
		if(!running) 
                    return runningTime;
		
                //
		return runningTime+System.currentTimeMillis() / 1000-startTime-Main.System_Start_Time;
	}
        
        //Function that calculates the remaining time for a process
	public long getRemainingTime()
	{
		if(!running) 
                    return remainingTime;
		
		long r = burstTime-( runningTime+System.currentTimeMillis() / 1000-Main.System_Start_Time-startTime);
		
		if (r>0) return r;
		
		return 0;
	}	
	
        //Function that calculates the waiting time for a process
	public long getWaitingTime()
	{
		if (completed || running) 
                    return waitingTime;
		if (stopped) 
                    return waitingTime + System.currentTimeMillis() / 1000-Main.System_Start_Time-stopTime;
		
                return System.currentTimeMillis() / 1000-Main.System_Start_Time-arrivalTime;
	}

        //Process Comparator
	@Override
	public int compareTo(MyProcess p) 
        {
		//bigger priority number, lower priority
            if (this.getPriority() > p.getPriority())
            {
                return 1;
            }
            
            if (this.getPriority() < p.getPriority())
            {
                return -1;
            }

            if (this.getArrivalTime() > p.getArrivalTime())
            {
                return 1;
            }
        
            return -1;
	}
}
