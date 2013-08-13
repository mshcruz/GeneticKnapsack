import java.util.*;

public class Population {
	private ArrayList<Genome> individuals;
	private ArrayList<Genome> eliteIndividuals;
	private ArrayList<HistogramBucket> eliteHistogram;
	private int populationSize;
	private int eliteSize;
	private int tournamentSize;
	private float mutationRatio;

	//Initializes the population and randomly generate individuals
	public Population(int populationSize, Menu menu) {
		super();
		this.populationSize = populationSize;
		this.eliteSize = 0;
		this.tournamentSize = 0;
		this.mutationRatio = 0;
		this.eliteIndividuals = new ArrayList<Genome>();
		this.individuals = new ArrayList<Genome>();
		this.eliteHistogram = new ArrayList<HistogramBucket>();
//		System.out.println("***MONTANDO GENOME****");
		for (int i=0; i < this.populationSize; i++) {
			Genome genome = new Genome(menu);
//			System.out.println("Individual "+i+":");
//			for (MenuItem item : genome.getOrder()){
//				System.out.println(item.getItemQuantity());
//			}
//			System.out.println();
			this.individuals.add(genome);
		}
//		int count=0;
//		System.out.println("***SAINDO DA MONTAGEM****");
//		for (Genome gen : this.individuals){
//			System.out.println("Individual "+count+":");
//			for (MenuItem item : gen.getOrder() ) {
//				System.out.println(item.getItemQuantity());
//			}
//			count++;
//		}
//		System.out.println();
	}
	
	//Sorts individuals by fitness
//	public void sortByFitness(){
//		int count=0;
//		System.out.println("***RECEBENDO EM FITNESS****");
//		for (Genome genome : this.individuals){
//			System.out.println("Individual "+count+":");
//			for (MenuItem item : genome.getOrder() ) {
//				System.out.println(item.getItemQuantity());
//			}
//			count++;
//		}
//		System.out.println();
//		Collections.sort(this.individuals, new Genome.GenomeByFitness()); 
//	}
	
	//Selects the E% best individuals
	public void selectElite(){
		Collections.sort(this.individuals, new Genome.GenomeByFitness()); 
		this.eliteIndividuals = new ArrayList<Genome>(this.individuals.subList(this.individuals.size()-this.eliteSize, this.individuals.size()));
		this.updateEliteHistogram();
	}
	
	public void performCrossover(){
		for (int j = 0; j < Double.valueOf(Math.floor((this.populationSize-this.eliteSize)/2)).intValue(); j++){
			Genome parentX = this.selectByTournament();
			Genome parentY = this.selectByTournament();
			if(parentX != parentY){
				Random rand = new Random();
				int pointToCross = rand.nextInt(parentX.getOrder().size());
				for (int i=pointToCross; i<parentX.getOrder().size();i++){
					MenuItem tempItem = new MenuItem();
					tempItem = parentX.getOrder().get(i);
					parentX.getOrder().set(i, parentY.getOrder().get(i));
					parentY.getOrder().set(i, tempItem);
				}
				parentX.updateGenome();
				parentY.updateGenome();
			}
		}
	}
	
	public void performMutation(){
//		System.out.println("-Mutating~");
		for (Genome genome : this.individuals){
			Random rand = new Random();
			if (rand.nextFloat() < this.mutationRatio){
//				System.out.print("Mutating ");
//				for (MenuItem item : genome.getOrder()){
//					System.out.print(item.getItemQuantity()+" ");
//				}
				do {
					for (MenuItem item : genome.getOrder()){
						item.randomizeQuantity();
					}
					genome.updateGenome();
				} while (genome.getOrderValue() > genome.getTotalBudget());
//				System.out.print("to ");
//				for (MenuItem item : genome.getOrder()){
//					System.out.print(item.getItemQuantity()+" ");
//				}
//				System.out.println();
			}
		}
	}
	
	//Updates the elite histogram
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
	
	private Genome selectByTournament(){
		Random rand = new Random();
		ArrayList<Genome> tournamentBuffer = new ArrayList<Genome>();
		for (int i = 0; i<this.tournamentSize; i++){
			tournamentBuffer.add(this.individuals.get(rand.nextInt(this.individuals.size()-this.eliteSize+1)));
		}
		Collections.sort(tournamentBuffer, new Genome.GenomeByFitness()); 
		return tournamentBuffer.get(tournamentBuffer.size()-1);
	}

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
	
}
