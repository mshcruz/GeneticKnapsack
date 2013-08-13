import java.util.*;

public class Genome implements Comparable<Genome> {
	private float totalBudget;
	private float orderValue;
	private float orderTime;
	private float fitness;
	private ArrayList<MenuItem> order;
	
	//Wight of time and value in the calculation of fitness.
	//private static final float VALUE_WEIGHT = (float)0.9;
	//rivate static final float TIME_WEIGHT = (float)0.1;

	//Initiates a genome: the quantities of each item will be random.
	public Genome(Menu menu) {
		super();
		this.orderValue = 0;
		this.orderTime = 0;
		this.totalBudget = menu.getTotalBudget();
		//this.order = new ArrayList<MenuItem>(menu.getItems());
		this.order = new ArrayList<MenuItem>();
		for (MenuItem item : menu.getItems()){
//			MenuItem newItem = new MenuItem();
//			newItem.setItemName(item.getItemName());
//			newItem.setItemQuantity(item.getItemQuantity());
//			newItem.setItemTime(item.getItemTime());
//			newItem.setItemValue(item.getItemValue());
//			newItem.setMaxItemQuantity(item.getMaxItemQuantity());
			MenuItem newItem = new MenuItem(item);
			this.order.add(newItem);
		}
		do {
			for (MenuItem item : this.order) { 
				item.randomizeQuantity();
			}
			this.calculateOrderValue();
		} while (this.orderValue > this.totalBudget);
		//Calculates the value and the time of the order, as well as the fitness of the genome
		this.updateGenome();
		/*for (MenuItem item : this.order){
			System.out.println(item.getItemQuantity());
		}
		System.out.println();*/
	}
	
	public void updateGenome(){
		this.calculateOrderValue();
		this.calculateOrderTime();
		this.calculateFitness();
	}
	
	private void calculateOrderValue (){ 
		this.orderValue = 0;
		for (MenuItem item : this.order) {
			this.orderValue += (item.getItemValue() * item.getItemQuantity());
		}
	}
	
	private void calculateOrderTime (){ 
		this.orderTime = 0;
		for (MenuItem item : this.order) {
			this.orderTime += (item.getItemTime() * item.getItemQuantity());
		}
	}
	
	private void calculateFitness(){
		if (this.orderValue > this.totalBudget){
			this.fitness = 0;
		} else {
			this.fitness = this.orderValue / this.totalBudget;
		}
	}
	
	//Compares two genomes by fitness and then by time taken to perform the order.
	public static class GenomeByFitness implements Comparator<Genome> {
	    @Override
	    public int compare(Genome genome1, Genome genome2) {
	    	if (Float.valueOf(genome1.getFitness()).compareTo(Float.valueOf(genome2.getFitness())) == 0){
	    		return Float.valueOf(genome1.getOrderTime()).compareTo(Float.valueOf(genome2.getOrderTime()));
	    	} else {
	    		return Float.valueOf(genome1.getFitness()).compareTo(Float.valueOf(genome2.getFitness()));
	    	}
	    }
	}
	@Override
    public int compareTo(Genome genome) {
		boolean isEqual = true;
		for (int i=0; i < genome.getOrder().size(); i++){
			if (genome.getOrder().get(i).getItemQuantity() != this.order.get(i).getItemQuantity()){
				isEqual = false;
				break;
			}
		}
		if (isEqual){
			return 0;
		}
		if (this.getFitness() > genome.getFitness()) {
			return 1;
		} 
		return -1;
    }
	
	public float getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(float totalBudget) {
		this.totalBudget = totalBudget;
	}
	
	public ArrayList<MenuItem> getOrder() {
		return order;
	}

	public void setOrder(ArrayList<MenuItem> order) {
		this.order = order;
	}
	
	public float getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(float orderValue) {
		this.orderValue = orderValue;
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}

	public float getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(float orderTime) {
		this.orderTime = orderTime;
	}
	
	
}