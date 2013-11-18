import java.util.*;

public class HeuristicSearch {
	private Node initialState;
	private ArrayList<Node> explored;
	private PriorityQueue<Node> queue;
	
	// Constructor
	public HeuristicSearch(String state) {
		initialState = new Node(state);
		explored = new ArrayList<Node>();
		queue = new PriorityQueue<Node>();
	}
	
	// Setters
	
	// Getters
	
	// Helper methods
	public void run() {
		
	}
	
	private void runSearch() {
		Node currentNode = initialState;
		ArrayList<Node> currentNodeChildren;
		queue.add(currentNode);
		
		while (currentNode.getHofN() != 0 || !queue.isEmpty()) {
			currentNode = queue.remove();
			explored.add(currentNode);
			
			currentNode.expand();
			currentNodeChildren = currentNode.getChildren();
			
			for (int i = 0; i < currentNodeChildren.size(); i++) {
				if (explored.contains(currentNodeChildren.get(i)) )
					continue;
				queue.add(currentNodeChildren.get(i));
			}
		}
	}
	
	private Stack<Node> findPath() {
		Stack<Node> path;
		
		return path;
	}
	
	// Node class
	private class Node implements Comparable<Node> {
		private String state;
		private ArrayList<Node> children;
		private int hValues[];
		private int cost;
		
		// Constructor
		public Node(String state) {
			this.state = state;
			this.cost = 0;

			calculateHofN();
		}

		// Setters
		public void setState(String state) {
			this.state = state;
		}
		
		public void setCost(int cost) {
			this.cost = cost;
		}
		
		// Getters
		public String getState() {
			return state;
		}
		
		public ArrayList<Node> getChildren() {
			return children;
		}
		
		public int getCost() {
			return cost;
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
			Node child;
			char newState[] = state.toCharArray();
			int indexOfSpace = state.indexOf('_');
			
			for (int i = -3; indexOfSpace + i < state.length() && i < 4; i++) {
				if (indexOfSpace + i < 0 || i == 0)
					continue;
				
				// Swap characters
				newState[indexOfSpace] = state.charAt(i);
				newState[i] = '_';
				
				// Create the new child
				child = new Node(newState.toString());
				
				// Set child path cost
				if (Math.abs(i) > 2)
					child.setCost(2);
				else
					child.setCost(1);
				
				// Add it to the parent	
				children.add(child);
			}
		}

		@Override
		public int compareTo(Node arg0) {
			if (this.cost < arg0.getCost())
				return -1;
			else if (this.cost > arg0.getCost())
				return 1;
			else
				return 0;	
		}
		
		public boolean equals(Node node) {
			return state.equals(node.getState());
		}
	}
	
	public static void main(String[] args) {
		HeuristicSearch search = new HeuristicSearch("AAABBB_");
	}
}
