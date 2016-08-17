package net.dodgewhale.glass.objects;

import lombok.Getter;

public class Cooldown {
	
	@Getter
	private long start;
	@Getter
	private double duration;
	
	public Cooldown(double duration) {
		this.updateStart();
		this.duration = duration;
	}
	
	public void updateStart() {
		this.start = System.currentTimeMillis();
	}

	public long calculateRemainder() {
		return (long) ((this.getStart() + (this.getDuration() * 1000L)) - System.currentTimeMillis());
	}
	
}