# Yingbo_Zheng_cs471_project_fall_2016
Process Dispatcher Using a Priority Queue System

ODU CS471 Operating System Project

Simulation of a dispatcher using priority queue system

Yingbo Zheng

November 2016 

Contents:
1. Overview
2. Background
2.1 Context Switches
2.2 Priority Scheduling
2.3 Blocked List
3. Program Behavior
3.1 Class Description
 
1. Overview
The CS 471 Fall 2016 project is envisioned as a system to be a simulation of a dispatcher that is based on priority queue scheduling. Processes aren’t real processes, but merely simulations of processes.

The user interface of the simulation is shown in the following figure. 
The screen is divided into 5 main blocks. The screen is automatically refreshing approximately every second.
Here is list of functions a user can do in this screen.
•	Using the text boxes and “Create Process” button at the top, a user can a process manually by entering the name, priority and burst time of the process.
•	Using “Auto Create” button, a user can automatically create 10 processes. The priority is randomly populated in the range 1 to 10. The burst time is randomly populated in the range 10 to 20.
•	A user can view all the processes in the ready queue in the top left window. The processes in ready queue are sorted by the order of their priority and arrival time.
•	A user can view the Gantt Chart the top right of the window.  The processes in this window are shown in the order of their start time. Some processes may start multiple times.
•	A user can view the running processes and the information of blocked process list in the bottom left of the window.
•	A user can view the context switch logs in the bottom right of the window. A log will be generated every time a process is preempted.

2. Background

2.1 Context Switches
When a currently running process is interrupted, swapped out, and another ready is brought in for execution on CPU, it is called “context switch.”

The context switch occurs in the following conditions.
•	When the running process has a low priority than that of a process newly created.
•	When the running process completes.

2.2 Priority Scheduling
Typically, each process is associated with a priority that is represented by an integer. 

Priority scheduling allocates CPU to a process with the highest priority (smaller integer means higher priority) in the ready queue.

Priority scheduling can be of two types: Non-preemptive and preemptive. In this project, preemption is used. However, it can be changed to implement Non-preemptive algorithm.

Non-preemptive: Algorithms where tasks are not interrupted come under non-preemptive
SJF is an example of priority scheduling where priority is the inverse of predicted next CPU burst time.

Preemptive: Whenever a process arrives in to the ready queue, the current process is halted. If it is found that the new process has higher priority than the one currently running, then it is preempted and CPU assigned to the new process. 

2.3 Blocked Process

A process that is blocked is a process that is waiting for some event, including a resource becoming available or the completion of an I/O operation.
In this simulation, a process is blocked if the processes with high priority are continuously being added to the dispatcher. High priority processes take all the CPU resource, then low priority process are starving and blocked.
This case can be simulated by manually entering high priority processes into the system. 

3. Program Behavior

3.1 Class Description

•	Main Class
The Main class implements the user interface for a user to create different test cases in the simulation. It creates an instance of dispatcher and call its methods.
•	Dispatcher class
The Dispatcher class simulates a scheduling system priority queue, i.e., the ready queue. All processes wait in the queue based on priority number and then arrival time.
It also tracks the current running process and process running history (Gantt Chart).  It checks whether a running process is complete or not. It schedules a newly added process by either putting it into running state or preempting the current running process or simply put it into the ready queue. It also creates the context switch logs.
•	MyProcess
The MyProcess class creates virtual processes, it provides a simulation of a process, not an actual real process. A MyProcess can be started, stopped or terminated. A stopped process can be restarted, while a terminated process cannot be restarted, it is considered dead.
A Myprocess has the attributes of ID, name, priority, burst time, start time, and arrival time. Also, it has the attributes of turnaround time, running time and remaining time are calculated in the simulation.
•	ProcessComparator
The ProcessComparator class is used in the priory queue sorting, especially in the ready queue of the dispatcher. We can change the order logic to implement other algorithms such as Shortest Job First, First Come First Serve, etc.


