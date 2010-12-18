import java.util.ArrayList;
import java.util.LinkedList;


public class DrumManager {
	 public  LinkedList<Job> drumQueue;
	 public ArrayList<Job> jobs;
	 public DrumManager(ArrayList<Job> jobs){
		 drumQueue = new LinkedList<Job>();
		 this.jobs = jobs;
	 }
	 public Job queueJob(Job job, String direction) {
		 job.setDirection(direction);
		 drumQueue.add(job);
		 return job;
	 }
	 public Job swapped(ArrayList<Job> jobs) {
		 Job job = drumQueue.pop();
		 if(job.getDirection().equalsIgnoreCase("in")) {
			 job.setInMemory(true);
		 }
		 if(job.getDirection().equalsIgnoreCase("out")) {
			 job.getMemory().removeJob(job.getLocation(), job.getSize());
			 job.setLocation(-1);
			 job.setInMemory(false);

		 }
		 return job;
		 
	 }
	 public Job manageDrum(Job drumJob) {
		 for (Job j : jobs) {
			 if (j.getLocation() == -1 ) {
//				 System.out.println("job #" + j.getNumber() + " needs a memory location. \n it is of size" + j.getSize());
				 if(j.findMemoryLocation()){
					 System.out.println("found memory location at " +j.getLocation() + "for size " + j.getSize());
					 queueJob(j, "in");
					 j.getMemory().displayMemoryTable();
				 }
			 }
		 }
		 if( drumQueue.size() > 0 && drumQueue.getFirst() != drumJob) {
			 Job newJob = drumQueue.getFirst();
			 if(newJob.getDirection().equalsIgnoreCase("in")) {
				 sos.siodrum(newJob.getNumber(),newJob.getSize(),newJob.getLocation(),0);
			 }
			 if(newJob.getDirection().equalsIgnoreCase("out")) {
			 	sos.siodrum(newJob.getNumber(),newJob.getSize(),newJob.getLocation(),1);
			 }
			 return newJob;
		 }
		 return drumJob;
	 }
	 public void moveToQueue(ArrayList<Job> jobs) {
		 for(Job j : jobs) {
			 if(j.getLocation() == -1) {
				if (j.findMemoryLocation()) {
					queueJob(j, "in");
				}
			 }
			 
		}
//		 System.out.println("Drum Queue looks like :");
//		 for(Job d : drumQueue) {
//
//			 
//			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			 System.out.println("| Job # " + d.getNumber() + "\t\t\t\t\t\t|");
//		 }
//		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	 }

	 public void displayDrumQueue() {
		 System.out.println("Drum Queue looks like :");
		 for(Job d : drumQueue) {

			 
			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			 System.out.println("| Job # " + d.getNumber() + "\t\t\t\t\t\t|");
		 }
		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	 }
}
