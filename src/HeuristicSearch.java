import java.util.*;

public class HeuristicSearch {
	
	private class Node {
		private String state;
		private ArrayList<Node> children;
		private int hValues[];
		
		// Setters
		public void setState(String state) {
			this.state = state;
		}
		
		// Getters
		public String getState() {
			return state;
		}
		
		public ArrayList<Node> getChildren() {
			return children;
		}
		
		public int getHofN() {
			int sum = 0;
			
			for (int i = 0; i < hValues.length; i++)
				sum += hValues[i];
			
			return sum;			
		}
		
		// Methods
		public void calculateHofN() {
			int totalB = 0;
			
			for (int i = 0; i < state.length(); i++)
				if (state.charAt(i) == 'B' || state.charAt(i) == 'b')
					totalB++;
			
			for (int i = 0; i < state.length(); i++) {
				if (state.charAt(i) == 'A' || state.charAt(i) == 'a')
					hValues[i] = totalB;
				else {
					if (state.charAt(i) == 'B' || state.charAt(i) == 'b')
						totalB--;
					hValues[i] = 0;
				}
			}
		}
		
		public void expand() {
			// implement
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
