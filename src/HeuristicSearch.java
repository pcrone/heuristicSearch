import java.util.*;

public class HeuristicSearch {
	private Node initialState;
	private ArrayList<Node> explored;
	private PriorityQueue<Node> queue;
	private Stack<Node> path;
	
	// Constructor
	public HeuristicSearch(String state) {
		initialState = new Node(state);
		explored = new ArrayList<Node>();
		queue = new PriorityQueue<Node>();
	}
	
	// Helper methods
	public void run() {
		runSearch();
		path = findPath();
	}
	
	private void runSearch() {
		Node currentNode = initialState;
		ArrayList<Node> currentNodeChildren;
		queue.add(currentNode);
		
		while (currentNode.getHofN() != 0 && !queue.isEmpty()) {
			currentNode = queue.remove();
			explored.add(currentNode);
			
			currentNode.expand();
			currentNodeChildren = currentNode.getChildren();
			
			for (int i = 0; i < currentNodeChildren.size(); i++) {
				if (!explored.contains(currentNodeChildren.get(i)) )
					queue.add(currentNodeChildren.get(i));
			}
		}
		
		if (currentNode.getHofN() == 0)
			System.out.println("Solution found!");
		else
			System.out.println("No Solution found!");
	}
	
	private Stack<Node> findPath() {
		Node currentNode = explored.get(explored.size() - 1);
		Stack<Node> path = new Stack<Node>();
		
		while (currentNode != null) {
			path.push(currentNode);
			currentNode = currentNode.parent;
		}
		
		return path;
	}
	
	public void pathToString() {
		Node currentNode;
		int totalCost = 0;
		
		while (!path.isEmpty()) {
			currentNode = path.pop();
			totalCost += currentNode.getCost();
			
			// Output the path
			System.out.println(currentNode.getState());
			
			if (!path.isEmpty()) {
				System.out.println("   |");
				System.out.println("   V"); 
			}
		}
		
		System.out.println("Total cost: " + totalCost);
	}
	
	// Node class
	private class Node implements Comparable<Node> {
		private String state;
		private Node parent;
		private ArrayList<Node> children;
		private int hValues[];
		private int cost;
		
		// Constructor
		public Node(String state) {
			this.state = state;
			this.parent = null;
			this.children = new ArrayList<Node>();
			this.hValues = new int[this.state.length()];
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
		
		public int getFofN() {
			return this.cost + getHofN();
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
				newState[indexOfSpace] = state.charAt(indexOfSpace + i);
				newState[indexOfSpace + i] = '_';
				
				// Create the new child
				child = new Node(String.valueOf(newState));
				
				// Set child path cost
				if (Math.abs(i) > 2)
					child.setCost(2);
				else
					child.setCost(1);
				
				// Add the parent to the child
				child.parent = this;
				
				// Add it to the parent	
				children.add(child);				
				
				// reset newState
				newState = state.toCharArray();
			}
		}

		@Override
		public int compareTo(Node arg0) {
			if (this.getFofN() < arg0.getFofN())
				return -1;
			else if (this.getFofN() > arg0.getFofN())
				return 1;
			else
				return 0;	
		}

		@Override
		public boolean equals(Object arg0) {
			if (arg0 instanceof Node) {
				Node node = (Node)arg0;
				
				return (this.getState().equals(node.getState()));
			}
				
			return super.equals(arg0);
		}
	}
	
	public static void main(String[] args) {
		HeuristicSearch search = new HeuristicSearch("AAABBB_");
		
		search.run();
		search.pathToString();
	}
}
