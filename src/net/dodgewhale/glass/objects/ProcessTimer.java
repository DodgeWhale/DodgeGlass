package net.dodgewhale.glass.objects;

public class ProcessTimer {

	private long start;
	
	public ProcessTimer() {
		this.start = System.nanoTime();
	}
	
	/**
	 * Calculates the difference from the original start time to the current
	 * @return Time taken with formatting
	 */
	public String calculate() {
		double ms = (System.nanoTime() - this.start) / 1e6;
		return ms + "ms";
	}
	
}