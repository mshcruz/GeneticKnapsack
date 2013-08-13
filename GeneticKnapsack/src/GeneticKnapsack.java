import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class GeneticKnapsack {
	
	//Algorithm parameters
	private static final int POPULATION_SIZE = 10;
	private static final int GENERATIONS = 50;
	private static final float ELITISM_RATIO = (float)0.1;
	private static final float MUTATION_RATIO = (float)0.1;
	private static final float TOURNAMENT_RATIO = (float)0.1;
	
	public static void main(String[] args) {		
		BufferedReader br = null;
		try {
			//Receives problem set as a parameter
 			String sCurrentLine;
 			br = new BufferedReader(new FileReader(args[0]));
 			if (br.ready()){
 				Menu menu = new Menu(Float.parseFloat(br.readLine()));
 				//Builds a menu structure based on the input, ignoring input items that are more expensive than the budget
 				while ((sCurrentLine = br.readLine()) != null) {
 	 				if (Float.parseFloat(sCurrentLine.split(",")[1]) <= menu.getTotalBudget()){
 	 					MenuItem menuItem = new MenuItem();
 	 					menuItem.setItemName(sCurrentLine.split(",")[0]);
 	 					menuItem.setItemValue(Float.parseFloat(sCurrentLine.split(",")[1]));
 	 					float inputTime = Float.parseFloat(sCurrentLine.split(",")[2]);
 	 					if (inputTime < 0){
 	 						inputTime = inputTime * (-1);
 	 					}
 	 					menuItem.setItemTime(inputTime);
 	 					menuItem.setMaxItemQuantity(menu.getTotalBudget());
 	 					menu.getItems().add(menuItem);
 	 				}
 				}
 				
 				//Creates the population, performs elitism and tournament crossover, and mutates all the populations except the last one
 				Collections.sort(menu.getItems(), new MenuItem.ItemsByRatio()); 
 				Population population = new Population(POPULATION_SIZE, menu);
 				population.setEliteSize(ELITISM_RATIO);
 				population.setMutationRatio(MUTATION_RATIO);
 				population.setTournamentSize(TOURNAMENT_RATIO);
 				for (int i = 0; i < GENERATIONS; i++) {
 					population.selectElite();
 					population.performCrossover();
 					if (i < GENERATIONS-1) {
 						population.performMutation();
 					}
 				}
// 				System.out.println("Elite Histogram: ");
// 				for (HistogramBucket bucket : population.getEliteHistogram()){
// 					System.out.print("Genome: ");
// 					for (MenuItem item : bucket.getGenome().getOrder()){
// 						System.out.print(item.getItemQuantity()+" ");
// 					}
// 					System.out.println(" - Quantity: "+bucket.getEntryCount()+" - Total time: "+bucket.getGenome().getOrderTime()+" - Total Value: "+bucket.getGenome().getOrderValue());
// 				}
 				
 				
 				//Selects the best individual (highest frequency in the elite histogram) and output its details
 				Collections.sort(population.getEliteHistogram(), new HistogramBucket.BucketsByValue());
 				Genome bestIndividual = population.getEliteHistogram().get(population.getEliteHistogram().size()-1).getGenome();
 				System.out.println("**BEST SOLUTION FOUND**");
 				System.out.println("Fitness: "+bestIndividual.getFitness());
 				System.out.println("Order value: "+bestIndividual.getOrderValue());
 				System.out.println("Order time: "+bestIndividual.getOrderTime());
 				for (MenuItem item : bestIndividual.getOrder()){
 					System.out.println("Item: "+item.getItemName()+" - Quantity: "+item.getItemQuantity());
 				}
 			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
