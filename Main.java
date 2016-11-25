package yingbo_zheng_cs471_project_fall_2016;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import static yingbo_zheng_cs471_project_fall_2016.Dispatcher.readyQueue;

/*
*The Main program is the starter program which calls and launches the dispatcher class.
*It builds the GUI.
*/

public class Main {

   static int ProcessNo = 1;
   static long System_Start_Time=0;
   long second=1000;
   static long arrivalTime=0;
   
   //MyProcess m = new MyProcess();
   
   public static void main(String args[]) {
	   
       Dispatcher d = new Dispatcher();
       final Main test = new Main();

       JFrame frame = new JFrame();
       frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
       Container cp = frame.getContentPane();

       cp.setLayout(new BorderLayout());
       
       JPanel input = new JPanel();
       input.setLayout(new FlowLayout());
       
       JLabel lblName = new JLabel("Name:");
       JTextField txtName = new JTextField(10);
       JLabel lblPriority = new JLabel("Priority:");
       JTextField txtPriority = new JTextField(3);       
       JLabel lblBurstTime = new JLabel("Burst Time:");
       JTextField txtBurstTime = new JTextField(3); 
       JButton btnCreate = new JButton("Create Process");
       JLabel lblID = new JLabel("New Process ID:");
       JLabel lblTerminate = new JLabel("Process to Terminate");
       JTextField txtID = new JTextField(3);     
       JTextField txtProcesstoTerminate = new JTextField(3);
       
       JButton btnAutoCreate = new JButton("Auto Create");
       JButton btnTerminate = new JButton("Terminate");
       
       //Add inputs for all the GUI controls
       input.add(lblName);
       input.add(txtName);
       input.add(lblPriority);
       input.add(txtPriority);
       input.add(lblBurstTime);
       input.add(txtBurstTime);       
       input.add(btnCreate);
       input.add(lblID);
       input.add(txtID);  
       input.add(btnAutoCreate);
       input.add(lblTerminate);
       input.add(txtProcesstoTerminate);
       input.add(btnTerminate);
       
       cp.add(input,BorderLayout.NORTH);
       
       //JTable for the ready queue
       final JTable jtReadyQueue = new JTable();  
       
       DefaultTableModel dtmReadyQueue = new DefaultTableModel(0, 0);

       //Ready Queue
       String[] colReadyQueue = { "ID", "Name", "Priority", "Burst Time", "Arrival Time","Waiting Time"}; //,, "Complete", "Running Time" , "Turnaround Time"};

      // add header in table model     
      dtmReadyQueue.setColumnIdentifiers(colReadyQueue);

      jtReadyQueue.setModel(dtmReadyQueue);

           // add row dynamically into the table      
      for (int count = 1; count <= 30; count++) {
    	  dtmReadyQueue.addRow(new Object[] { "data", "data", "data",
                      "data", "data", "data" });
       }       
       JScrollPane jspReadyQueue =new JScrollPane(jtReadyQueue);
       jspReadyQueue.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
               "Ready Queue -- Ordered by Priority then Arrival Time",
               TitledBorder.LEFT,
               TitledBorder.TOP));
       
       jspReadyQueue.setPreferredSize(new Dimension(760, 300));
  	      
  	   final JTable jtGantt = new JTable();  
       DefaultTableModel dtmGantt = new DefaultTableModel(0, 0);  
       
       String[] colGantt ={ "ID", "Name", "Priority","Start Time"};
       // add header in table model     
       dtmGantt.setColumnIdentifiers(colGantt);

       jtGantt.setModel(dtmGantt);

            // add row dynamically into the Gantt chart      
       for (int count = 1; count <= 30; count++) {
     	  dtmGantt.addRow(new Object[] { "data", "data", "data",
                       "data", "data", "data" });
        } 
       
       //New scroll pane for Gantt chart
       JScrollPane jspGantt =new JScrollPane(jtGantt);
       jspGantt.setPreferredSize(new Dimension(600, 300));
       jspGantt.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
               "Gantt Chart",
               TitledBorder.LEFT,
               TitledBorder.TOP));
       
       cp.add(jspReadyQueue, BorderLayout.LINE_START);
       cp.add(jspGantt, BorderLayout.LINE_END);
       
       JTextArea jtaLog =new JTextArea();
       jtaLog.setPreferredSize(new Dimension(730, 300));
       jtaLog.setEditable(false);
       JScrollPane jspLog = new JScrollPane(jtaLog, 
    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       jspLog.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
               "Running Process & Blocked List",
               TitledBorder.LEFT,
               TitledBorder.TOP));
       
       JPanel jpBottom= new JPanel();
       jpBottom.setLayout(new BorderLayout());
       jpBottom.add(jspLog, BorderLayout.LINE_START);
 
       JTextArea jtaContext =new JTextArea();
       jtaContext.setPreferredSize(new Dimension(570, 300));
       jtaContext.setEditable(false);
       JScrollPane jspContext = new JScrollPane(jtaContext, 
    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       jspContext.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
               "Context Switch Information",
               TitledBorder.LEFT,
               TitledBorder.TOP));
       jpBottom.add(jspContext, BorderLayout.LINE_END);
       cp.add(jpBottom, BorderLayout.PAGE_END);
       
       //Action listener for the "create" button
       btnCreate.addActionListener(new ActionListener() {
		
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			
			int priority = Integer.parseInt(txtPriority.getText());
			int burstTime = Integer.parseInt(txtBurstTime.getText());
			String name = txtName.getText();
			txtID.setText(ProcessNo + "");
			
			if (System_Start_Time==0) 
			{
				System_Start_Time = System.currentTimeMillis()/1000;			
			}
			else
			{
				arrivalTime = System.currentTimeMillis()/1000 - System_Start_Time;
			}
			
			d.AddProcess(new MyProcess(ProcessNo, name, priority, burstTime, arrivalTime),false);
			ProcessNo ++;
		
		}
	  });
       
       btnTerminate.addActionListener(new ActionListener() {
           @Override
           public synchronized void actionPerformed(ActionEvent e) 
           {
               //Get process ID and then compare with what the user typed in the textbox
               int ProcessIDTerminate;
               
               //Convert the user input to process ID integer
               ProcessIDTerminate = Integer.parseInt(txtProcesstoTerminate.getText());
               
               //If process is running
               if(ProcessIDTerminate == Dispatcher.runningProcess.getID())
               {
                   Dispatcher.runningProcess.terminate();
               }
               else
               {
                    Iterator<MyProcess> it = Dispatcher.readyQueue.iterator();

                    while (it.hasNext()){
                        MyProcess p = it.next();
                        if (p.getID() == ProcessIDTerminate)
                        {
                            Dispatcher.readyQueue.remove(p);
                            break;
                        }                   
                    }                   
               }
           }
       });
       
       //Action Listener for the AutoCreate button
       btnAutoCreate.addActionListener(new ActionListener() {
   		
		@Override
		public synchronized void actionPerformed(ActionEvent e) 
                {
			if (System_Start_Time==0) 
			{
				System_Start_Time = System.currentTimeMillis()/1000;			
			}
			
			Random randomGenerator = new Random();

			for (int i=0; i<10; i++)
			{
				int priority = randomGenerator.nextInt(10)+1;
				int burstTime = randomGenerator.nextInt(10)+10;
				String name = "P"+ProcessNo;
				txtID.setText(ProcessNo + "");
				arrivalTime=System.currentTimeMillis()/1000-System_Start_Time;
				d.AddProcess(new MyProcess(ProcessNo, name, priority, burstTime, arrivalTime),true);

				ProcessNo ++;
			}
			d.checkRunning();
		}
	  });
       
       frame.pack();
       frame.setLocationRelativeTo ( null );
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setTitle("Priority Queue Dispatcher with Preemption");

       frame.setVisible(true);
       
       //Timer
       Timer timer = new Timer();
       timer.schedule(new TimerTask() {

           @Override
           public synchronized void run() {
        	   if (ProcessNo>1)
        	   {
        		   d.checkRunning();
        		   
        		   jtaLog.setText(d.toString()); 
        		   JScrollBar vbLog = jspLog.getVerticalScrollBar();
        		   vbLog.setValue( vbLog.getMaximum() );  
        		   
        		   //Update context switch
        		   jtaContext.setText(d.Context());
        		   JScrollBar vbContext = jspContext.getVerticalScrollBar();
        		   vbContext.setValue( vbContext.getMaximum() );
  
        		   dtmReadyQueue.setDataVector(d.getReadyQueue(), colReadyQueue);
        		   dtmGantt.setDataVector(d.getGantt(), colGantt);
        		   
        		   frame.repaint();
        	   }
           }
       }, 0, test.second);	          
   }
}
