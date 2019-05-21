package __Test;

public class Main {
	
	public static double calculateAmdahl(double value) {
		
		return (1 / (1 - value + (value / 100)));
	}
	
	public static void main(String[] args) {
		
		double[] values = { 0.5,  0.6, 0.7, 0.8, 0.9, 0.95,
	                        0.96, 0.97, 0.98, 0.99, 1.0 };
		
		for (double value : values) {
			
			System.out.println(Math.floor(calculateAmdahl(value)));
		}
	}
}
