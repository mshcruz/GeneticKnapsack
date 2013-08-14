import java.util.*;

public class Population {
	private ArrayList<Genome> individuals;
	private ArrayList<Genome> eliteIndividuals;
	private ArrayList<HistogramBucket> eliteHistogram;
	private int populationSize;
	private int eliteSize;
	private int tournamentSize;
	private float mutationRatio;
	private float crossoverRatio;

	//Initializes the population and randomly generate individuals
	public Population(int populationSize, Menu menu) {
		super();
		this.populationSize = populationSize;
		this.eliteSize = 0;
		this.tournamentSize = 0;
		this.mutationRatio = 0;
		this.crossoverRatio = 0;
		this.eliteIndividuals = new ArrayList<Genome>();
		this.individuals = new ArrayList<Genome>();
		this.eliteHistogram = new ArrayList<HistogramBucket>();
		for (int i=0; i < this.populationSize; i++) {
			Genome genome = new Genome(menu);
			this.individuals.add(genome);
		}
	}
	
	//Selects the best individuals based on the elite number
	public void selectElite(){
		Collections.sort(this.individuals, new Genome.GenomeByFitness()); 
		this.eliteIndividuals = new ArrayList<Genome>(this.individuals.subList(this.individuals.size()-this.eliteSize, this.individuals.size()));
		this.updateEliteHistogram();
	}
	
	//Performs the crossover of the population
	public void performCrossover(){
		for (int j = 0; j < Double.valueOf(Math.floor((this.populationSize-this.eliteSize)/2)).intValue(); j++){
			Random rand = new Random();
			if (rand.nextFloat() < this.crossoverRatio){
				//Selects individuals by tournament
				Genome genomeX = this.selectByTournament();
				Genome genomeY = this.selectByTournament();
				if(genomeX != genomeY){
					rand = new Random();
					//Performs the crossover at a random point
					int pointToCross = rand.nextInt(genomeX.getOrder().size());
					for (int i=pointToCross; i<genomeX.getOrder().size();i++){
						MenuItem tempItem = new MenuItem();
						tempItem = genomeX.getOrder().get(i);
						genomeX.getOrder().set(i, genomeY.getOrder().get(i));
						genomeY.getOrder().set(i, tempItem);
					}
					//Updates the orders' value, time and fitness
					genomeX.updateGenome();
					genomeY.updateGenome();
					//Reduces the quantities of items until the new orders' values fit in the total budget
					genomeX.repairGenome();
					genomeY.repairGenome();
				}
			}
		}
	}
	
	//Evaluates if a mutation will occur for each genome and repairs the mutated individual
	public void performMutation(){
		for (Genome genome : this.individuals){
			Random rand = new Random();
			if (rand.nextFloat() < this.mutationRatio){
				for (MenuItem item : genome.getOrder()){
					item.randomizeQuantity();
				}
				genome.updateGenome();
				genome.repairGenome();
			}
		}
	}
	
	//Checks if one individual has been selected as elite for a set number of times (threshold)
	public boolean eliteReachedThreshold(int threshold){
		for (HistogramBucket bucket : this.eliteHistogram){
			if (bucket.getEntryCount() >= threshold){
				return true;
			}	
		}
		return false;
	}
	
	//Updates the elite histogram based on the occurrences of the elite individuals
	private void updateEliteHistogram(){
		for (Genome eliteGenome : this.eliteIndividuals){
			boolean foundInHistogram = false;
			for (HistogramBucket bucket : this.eliteHistogram){
				if (eliteGenome.compareTo(bucket.getGenome()) == 0){
					bucket.incrementBucket();
					foundInHistogram = true;
					break;
				}
			}
			if (!foundInHistogram){
				HistogramBucket newBucket = new HistogramBucket(eliteGenome);
				this.eliteHistogram.add(newBucket);
			}
		}
	}
	
	//Selects two individuals by tournament (does not consider the elite individuals for the tournament)
	private Genome selectByTournament(){
		Random rand = new Random();
		ArrayList<Genome> tournamentBuffer = new ArrayList<Genome>();
		for (int i = 0; i<this.tournamentSize; i++){
			tournamentBuffer.add(this.individuals.get(rand.nextInt(this.individuals.size()-this.eliteSize+1)));
		}
		Collections.sort(tournamentBuffer, new Genome.GenomeByFitness()); 
		return tournamentBuffer.get(tournamentBuffer.size()-1);
	}

	//Getters and setters
	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public ArrayList<Genome> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(ArrayList<Genome> individuals) {
		this.individuals = individuals;
	}

	public ArrayList<Genome> getEliteIndividuals() {
		return eliteIndividuals;
	}

	public void setEliteIndividuals(ArrayList<Genome> eliteIndividuals) {
		this.eliteIndividuals = eliteIndividuals;
	}

	public float getMutationRatio() {
		return mutationRatio;
	}

	public void setMutationRatio(float mutationRatio) {
		this.mutationRatio = mutationRatio;
	}

	public ArrayList<HistogramBucket> getEliteHistogram() {
		return eliteHistogram;
	}

	public void setEliteHistogram(ArrayList<HistogramBucket> eliteHistogram) {
		this.eliteHistogram = eliteHistogram;
	}

	public int getEliteSize() {
		return eliteSize;
	}

	public void setEliteSize(float elitismRatio) {
		this.eliteSize = Double.valueOf(Math.floor(this.populationSize * elitismRatio)).intValue();
	}

	public int getTournamentSize() {
		return tournamentSize;
	}

	public void setTournamentSize(float tournamentRatio) {
		this.tournamentSize = Double.valueOf(Math.floor(this.populationSize * tournamentRatio)).intValue();;
	}

	public float getCrossoverRatio() {
		return crossoverRatio;
	}

	public void setCrossoverRatio(float crossoverRatio) {
		this.crossoverRatio = crossoverRatio;
	}
	
}
