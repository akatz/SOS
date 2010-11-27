import java.util.LinkedList;


public class IOManager {
	 public  LinkedList<Job> IOQueue;

	 public IOManager(){
		 IOQueue = new LinkedList<Job>();
	 }
	 public Job requestIO(Job job) {
		 IOQueue.add(job);
//		 System.out.println("Job Added table looks like :");
//		 for(Job j : IOQueue) {
//
//			 
//			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			 System.out.println("| Job # " + j.getNumber() + "\t\t\t\t\t\t|");
//		 }
//		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		 if(IOQueue.size() == 1) {
			 sos.siodisk(job.getNumber());
			 return job;
		 } else {
			 return IOQueue.peekFirst();
		 }
	 }
	 public Job finishIO(Job job) {
		 job.setLatched(false);
		 IOQueue.removeFirstOccurrence(job);
//		 if(job.getNumber() == 6) {
//			 System.out.println("------------------------------------------------------------------------------------");
//
//		 }
//		 System.out.println("Job removed table looks like :");
//
//		 for(Job j : IOQueue) {
//			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			 System.out.println("| Job # " + j.getNumber() + "\t\t\t\t\t\t|");
//		 }
//		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");	 

		 
		 if(job.isBlocked() && !(IOQueue.contains(job))) {
			 job.setBlocked(false);
		 }
		 if(!job.isBlocked()) {
//			 System.out.println("Job # " + job.getNumber() +" is now unblocked");
		 }
		 job = manageIO();
		 return job;
	 }
	 public Job manageIO() {
		 if( IOQueue.size() > 0 ) {
			 Job newJob = IOQueue.getFirst();
			 sos.siodisk(newJob.getNumber());
			 newJob.setLatched(true);
			 return newJob;
		 }
		 return new Job();
	 }
}
