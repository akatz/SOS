import java.util.ArrayList;


public class cpuScheduler {
	private int TIMESLICE = 100;
	private Job jobLastRan = new Job();
	public Job schedule(ArrayList<Job> jobs, int []a, int []p) {

		Job jobToRun = jobLastRan;
		if(jobLastRan.getNumber() == -1 && !jobs.isEmpty() && jobs.get(0).isInMemory()) {
			jobToRun = jobs.get(0);
			jobLastRan = jobToRun;

		} else if (!jobs.isEmpty() && jobs.size() > 1){

			int nextIndex = jobs.indexOf(jobLastRan) + 1;
			if(nextIndex >= jobs.size()) {
				nextIndex = 0;
			} 
			for(int i = nextIndex; i < jobs.size(); i++) {
				if (jobs.get(i) == jobLastRan || !jobs.get(i).isInMemory() || jobs.get(i).isBlocked()) {
					continue;
				} else {
					jobToRun = jobs.get(i);
				}
			}
			if(jobToRun == jobLastRan) {
				for(int i = 0; i < jobs.size(); i++) {

					if(jobs.get(i).isBlocked()) {
//						System.out.println("job " + jobs.get(i).getNumber() + " is blocked");
					}
					if (jobs.get(i) == jobLastRan || !jobs.get(i).isInMemory() || jobs.get(i).isBlocked()) {

						continue;
					} else {
						jobToRun = jobs.get(i);
					}
				}
			}
			
			if(jobToRun.isBlocked() || !jobToRun.isInMemory()) {
				a[0]=1;
				return new Job();
			}

		} else if (jobs.size() == 1 && jobs.get(0).isInMemory() && !jobs.get(0).isBlocked()) {
			jobToRun = jobs.get(0);
			jobLastRan = jobToRun;
		} else{
			a[0]=1;
			return new Job();
		}
//		System.out.print("Max CPU time for job #");
//		System.out.print(jobToRun.getNumber() + " ");
//		System.out.println(jobToRun.getMaxCpu());
//		System.out.print("CPU time for job #");
//		System.out.print(jobToRun.getNumber() + " ");
//		System.out.println(jobToRun.getCurrentTime());
		
		if( jobToRun.getMaxCpu() > jobToRun.getCurrentTime() + TIMESLICE) {
			a[0] = 2;
			p[2] = jobToRun.getLocation();
			p[3] = jobToRun.getSize();
			p[4] = TIMESLICE;
			return jobToRun;

		} else if(!(jobToRun.getCurrentTime() == jobToRun.getMaxCpu() ) ) {
			a[0] = 2;
			p[2] = jobToRun.getLocation();
			p[3] = jobToRun.getSize();
			p[4] = jobToRun.getMaxCpu() - jobToRun.getCurrentTime();
			return jobToRun;
		}
		a[0]=1;
		return new Job();
		
	}
}
