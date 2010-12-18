


public class Job {
	private int number;
	private int priority;
	private int size;
	private int maxCpu;
	private int currentTime;
	private int location;
	private boolean inMemory=false;
	private boolean latched = false;
	private boolean blocked = false;
	private boolean terminated = false;
	private String direction;
	private MemoryManager memory;

	public Job() {
		number = -1;
	}
	public Job(int number, int priority, int size, int maxCpu, int CurrentTime, MemoryManager memory) {
		this.number=number;
		this.priority=priority;
		this.size=size;
		this.maxCpu=maxCpu;
		this.currentTime=0;
		this.memory = memory;
		location = memory.find_space(size);

	}
	
	public boolean isTerminated() {
		return terminated;
	}
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isInMemory() {
		return inMemory;
	}

	public void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}

	public boolean isLatched() {
		return latched;
	}

	public void setLatched(boolean latched) {
		this.latched = latched;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getMaxCpu() {
		return maxCpu;
	}
	public void setMaxCpu(int maxCpu) {
		this.maxCpu = maxCpu;
	}
	public int getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	public void remove() {
		memory.getDrum().queueJob(this, "out");
	}
	public boolean findMemoryLocation() {
		
		location  = memory.find_space(size);
		if (location == -1) {
			return false;
		} else {
			return true;
		}
	}
	public MemoryManager getMemory() {
		return memory;
	}

}
