import java.util.Comparator;


public class HistogramBucket implements Comparable<HistogramBucket>{
	private int entryCount;
	private Genome genome;
	
	public HistogramBucket(Genome genome) {
		super();
		this.entryCount = 0;
		this.genome = genome;
	}
	
	public int getEntryCount() {
		return entryCount;
	}
	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}
	public Genome getGenome() {
		return genome;
	}
	public void setGenome(Genome genome) {
		this.genome = genome;
	}
	public void incrementBucket(){
		this.entryCount++;
	}
	public static class BucketsByValue implements Comparator<HistogramBucket> {
	    @Override
	    public int compare(HistogramBucket bucket1, HistogramBucket bucket2) {
	    	return (Integer.valueOf(bucket1.getEntryCount()).compareTo(Integer.valueOf(bucket2.getEntryCount())));
	    }
	}
	@Override
    public int compareTo(HistogramBucket bucket) {
		return (Integer.valueOf(this.entryCount).compareTo(bucket.getEntryCount()));
    }
}
