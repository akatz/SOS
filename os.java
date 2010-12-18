import java.util.ArrayList;
import java.util.ListIterator;


public class os {
	static ArrayList<Job> jobs = new ArrayList<Job>();
	static MemoryManager memory;
	static DrumManager drum;
	static cpuScheduler cpu;
	static IOManager io;
	static Job runningJob=new Job(), drumJob=new Job(), ioJob=new Job();
	static int systemTime=0;
	
	public static void startup (){
		sos.ontrace();
		drum = new DrumManager(jobs);
		memory = new MemoryManager(drum);
		
		cpu = new cpuScheduler();
		io = new IOManager();
	}
	public static void Crint (int []a, int []p){
//		System.out.println("Job number " + p[1]);
		bookKeeper(p[5]);
//		interrupt handler
		Job job = new Job(p[1],p[2],p[3],p[4],p[5], memory);
		jobs.add(job);
		if(job.getLocation() != -1) {
			drum.queueJob(job, "in");
		}
		cleanJobTable(jobs);
//		done with interrup handler
		drumJob = drum.manageDrum(drumJob);
//		drum.moveToQueue(jobs);
		runningJob = cpu.schedule(jobs, a, p);
		System.out.println("memory in crint");
		memory.displayMemoryTable();
		return;
		
	}

	public static void Dskint (int []a, int []p){
//		System.out.println("Disk Int");
		bookKeeper(p[5]);
		
		ioJob = io.finishIO(ioJob);
		drumJob = drum.manageDrum(drumJob);
		runningJob = cpu.schedule(jobs, a, p);
		return;
	}

	public static void Drmint (int []a, int []p){
//		System.out.println("Drum Int");
		bookKeeper(p[5]);
//		interrupt handler
		memory.displayMemoryTable();

		displayJobTable();
		drum.displayDrumQueue();
		io.displayIOTable();
		drum.swapped(jobs);
		
//		done with interrupt handler
		drumJob = drum.manageDrum(drumJob);
//		drum.moveToQueue(jobs);
		runningJob = cpu.schedule(jobs, a, p);
		return;
	}

	public static void Tro (int []a, int []p){
//		System.out.println("Timer Int");
		bookKeeper(p[5]);
		if(runningJob.getMaxCpu() - runningJob.getCurrentTime() == 0) {
			terminateJob(runningJob);
		}

		drumJob = drum.manageDrum(drumJob);
//		drum.moveToQueue(jobs);
		runningJob = cpu.schedule(jobs, a, p);
	}

	public static void Svc (int []a, int []p){
//		System.out.println("Service Int");
		bookKeeper(p[5]);
		switch(a[0]){
			case 5: 
				terminateJob(runningJob);
				break;
			case 6:
				ioJob = io.requestIO(runningJob);
				break;
			case 7:
				if(io.IOQueue.contains(runningJob)) {
					runningJob.setBlocked(true); 
				}
				break;
		}
		drumJob = drum.manageDrum(drumJob);
//		drum.moveToQueue(jobs);
		runningJob = cpu.schedule(jobs, a, p);
		return;
	}
	public static void bookKeeper(int time) {
		if(!(runningJob.getNumber() == -1 && runningJob.isLatched())) {
			int difference = time - systemTime;
			runningJob.setCurrentTime(runningJob.getCurrentTime() + difference);
			systemTime = time;
		}

	}
	public static Job getJobByNumber(ArrayList<Job> jobs, int number) {
		ListIterator<Job> itr = jobs.listIterator();
		while(itr.hasNext()) {
			Job job = itr.next();
			if(job.getNumber() == number) {
				return job;
			}
		}
		return new Job();
	}
	public static void terminateJob(Job runningJob) {
		if( !(runningJob.getNumber() == -1) && !io.IOQueue.contains(runningJob)) {

			memory.removeJob(runningJob.getLocation(), runningJob.getSize());
			jobs.remove(runningJob);
//			runningJob.remove();
			memory.displayMemoryTable();
		} else if (!(runningJob.getNumber() == -1)) {
			runningJob.setTerminated(true);
		}
		return;
	}
	public static void cleanJobTable(ArrayList<Job> jobs) {
		ArrayList<Job> toRemove = new ArrayList<Job>();
		for( Job j : jobs ) {
			if (j.isTerminated() && !io.IOQueue.contains(j)) {
				memory.removeJob(j.getLocation(), j.getSize());
				toRemove.add(j);
			}
		}
		for(Job remove : toRemove) {
			jobs.remove(remove);
		}
	}
	 public static void displayJobTable() {
		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		 System.out.println("| number \t| location \t| size \t| in memory \t|");

		 for(Job entry : jobs) {
			 System.out.println("| " + entry.getNumber() + "\t\t| " + entry.getLocation() + "\t\t| "  + entry.getSize() + "\t|" + entry.isInMemory() + "\t\t|");
		 }
		 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	 }
}
