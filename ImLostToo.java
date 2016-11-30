/**
 * what do I do?
 * 
 * @author 
 *
 */
import java.io.*;
/**
 * The ImLostToo main class creates a maze and uses queue to find end. 
 * @author Yucen Zhang
 * @date March 9th 2016
 */
public class ImLostToo {
	
	private static int stepToFinish;
	private static LinkedPriorityQueue<Hexagon> mazeQueue = new LinkedPriorityQueue<Hexagon>();  //maze queue stores hexagon
	private static boolean foundE = false; // foundE will be set to true when the end tile is found
	private static int count = 0; // counter that counts the step
	private static Maze maze = null; // Maze 
	private static Hexagon newNeighbour = null ; //neighbor tiles

	/**
	 * @param args
	 */
	public static void main (String[] args) {

		try {
			if (args.length < 1) {
				throw new IllegalArgumentException("Please provide a Maze file as a command line argument");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		// create maze by supplying a maze file
		try {
			String mazeFile = args[0];
			maze = new Maze(mazeFile);

		} catch (FileNotFoundException e) {
			System.out.println("File is not found: " + e.getMessage());
			System.out.println("======SYSTEM TERMINATED======");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("General I/O exception: " + e.getMessage());
			System.out.println("======SYSTEM TERMINATED======");
			System.exit(0);
		} catch (UnknownMazeCharacterException e) {
			System.out.println("Error in Maze file: " + e.getMessage());
			System.out.println("======SYSTEM TERMINATED======");
			System.exit(0);
		}

		// pushing start tile into queue
		Hexagon startT = maze.getStart(); 
		maze.setTimeDelay(50);  // use it to increase the speed
		startT.setSteps(0); //initial the steps
		startT.setStarted(); 
		mazeQueue.enqueue(startT,(startT.getSteps() + startT.distanceToEnd(maze))); //enqueue start tile 

		// Start searching the end.
		while (!mazeQueue.isEmpty() && !foundE && maze.getEnd() != null) {
			count++;

			// dequeue to get current tile
			Hexagon currentT = mazeQueue.dequeue();

			// set color for current tile 
			if(!currentT.isStart()){
				currentT.setCurrent();
				maze.repaint();
			}

			// if the current tile is end, set finished
			if (currentT.isEnd()) {
				foundE = true;
				stepToFinish = (int)currentT.getSteps();
				maze.repaint();
				currentT.setFinished();
			}else{
				// finding neighbor and enqueue all of them based on its priority. 
				for(int i=0; i<6; i++){
					try{
						newNeighbour = currentT.getNeighbour(i);
						if (newNeighbour != null && !newNeighbour.isWall() && !newNeighbour.isEnqueued()
								&& !newNeighbour.isDequeued() && !newNeighbour.isStart()) {
							newNeighbour.setSteps(currentT.getSteps()+1); //update steps
							mazeQueue.enqueue(newNeighbour,newNeighbour.getSteps() + newNeighbour.distanceToEnd(maze));
							newNeighbour.setEnqueued();
							maze.repaint();							
						}
					}catch(EmptyCollectionException e){
						System.out.println(e.getMessage());
					}catch(InvalidNeighbourIndexException e){
						System.out.println(e.getMessage());
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				if(!currentT.isStart()){
					currentT.setDequeued();
				}
			}		
		}
		// print out result 

		if (foundE) {
			System.out.println("The end is FOUND");
		} else {
			System.out.println("The end is NOT FOUND");
		}
		System.out.println("==================================================");
		System.out.println("Number of steps to get to finish: " + stepToFinish);
		System.out.println("Number of steps that it took: " + count);
		System.out.println("Number of tiles are still in the queue: " + mazeQueue.size());

	}
}
