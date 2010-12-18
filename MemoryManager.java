

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


public class MemoryManager {
	 public  LinkedList<int[]> memory;
	 private DrumManager drum;
	 public MemoryManager(DrumManager drum) {
		 memory= new LinkedList<int[]>();
		 int []init =  {0,100};
		 memory.add(init);
		 this.drum = drum;
	 }
	 public int find_space(int size){
		 ListIterator<int[]> itr = memory.listIterator();
		   while(itr.hasNext())
		    {
		     int []entry = itr.next();
		     if(entry[1] > size) {
		    	 int location = entry[0];
		    	 entry[0] += size;
		    	 entry[1] -= size;
		    	 return location;
		     }
		    }
		   return -1;
	 }
	 public int addJob(int size, int []entry) {
    	 int location = entry[0];
    	 entry[0] += size;
    	 entry[1] -= size;
    	 return location;
	 }
	 public void removeJob(int location, int size){
		 ListIterator<int[]> itr = memory.listIterator();
		 boolean spaceFreed = false;
		   while(itr.hasNext() && spaceFreed == false)
		    {
		     int []entry = itr.next();
		     if(entry[0] == (location+size)) {
		    	 entry[0] -= size;
		    	 entry[1] += size;
		    	 spaceFreed = true;
		     } else if(entry[0] + entry[1] == location) {
		    	 entry[1] += size;
		    	 spaceFreed = true;
		     } 
		    }
		   if (!spaceFreed) {
			   ListIterator<int[]> itr1 = memory.listIterator();
			   while(spaceFreed == false && itr1.hasNext()) {
				   int []entry = itr1.next();
				   if(entry[0] > location) {
					   itr1.previous();
					   int []newEntry = {location, size};
					   itr1.add(newEntry);
					   spaceFreed = true;
				   }
			   }
		   }
		   joinEntries();
	 }
	 public void displayMemoryTable() {
		 System.out.println("++++++++++++++++++++++");
		 System.out.println("| location \t| size \t|");

		 for(int[] entry : memory) {
			 System.out.println("| " + entry[0] + "\t\t| " + entry[1] + "\t|");
		 }
		 System.out.println("++++++++++++++++++++++");

	 }
	 public void joinEntries() {
		 ListIterator<int[]> itr = memory.listIterator();
		 int[] lastEntry=null;
		   while(itr.hasNext())
		    {
			  if(lastEntry == null) {
				  lastEntry = itr.next();
				  continue;
			  }
		     int []entry = itr.next();
		     if(lastEntry[0] + lastEntry[1] == entry[0]  ) {
		    	 lastEntry[1] += entry[1];
		    	 itr.remove();
		     } 
		    }
	 }
	 public void takeoverLocation(Job runningJob, Job job, ArrayList<Job> jobs) {
		 Job jobToSwapOut = jobs.get(0);
		 for (Job j : jobs) {
			 if (j.isInMemory() && !j.isLatched() && j.getSize() > job.getSize() ) {
				 if (jobs.indexOf(runningJob) - jobs.indexOf(jobToSwapOut) < jobs.indexOf(j) - jobs.indexOf(runningJob)) {
					 jobToSwapOut = j;
				 }
			 } else {
				 jobToSwapOut = new Job();
			 }
		 }
		 if(! (jobToSwapOut.getNumber() == -1 )) {
			 drum.queueJob(jobToSwapOut, "out");
			 drum.queueJob(job, "in");
		 }

	 }
	public DrumManager getDrum() {
		return drum;
	}
}
