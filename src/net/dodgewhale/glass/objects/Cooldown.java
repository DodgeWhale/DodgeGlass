package net.dodgewhale.glass.objects;

public class Cooldown {
	
	private long start;
	private double duration;
	
	public Cooldown(double duration) {
		this.updateStart();
		this.duration = duration;
	}
	
	public long getStart() {
		return this.start;
	}
	
	public void updateStart() {
		this.start = System.currentTimeMillis();
	}
	
	public double getDuration() {
		return this.duration;
	}

	public long calculateRemainder() {
		return (long) ((this.getStart() + (this.getDuration() * 1000L)) - System.currentTimeMillis());
	}
	
}