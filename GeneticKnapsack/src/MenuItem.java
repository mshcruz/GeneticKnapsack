import java.util.Comparator;
import java.util.Random;;

public class MenuItem implements Comparable<MenuItem> {

	private String itemName;
	private float itemValue;
	private float itemTime;
	private int itemQuantity;
	private int maxItemQuantity;

	public MenuItem() {
		super();
		this.itemName = new String();
		this.itemValue = 0;
		this.itemTime = 0;
		this.itemQuantity = 0;
		this.maxItemQuantity = 0;
	}
	public MenuItem(MenuItem item) {
		super();
		this.itemName = item.itemName;
		this.itemValue = item.itemValue;
		this.itemTime = item.itemTime;
		this.itemQuantity = item.itemQuantity;
		this.maxItemQuantity = item.maxItemQuantity;
	}
	
	//Randomizes quantity of item
	public void randomizeQuantity(){
		Random rn = new Random();
		this.itemQuantity = rn.nextInt(this.maxItemQuantity + 1);
	}
	
	//Compares items by their ratios between Value and Time
	public static class ItemsByRatio implements Comparator<MenuItem> {
	    @Override
	    public int compare(MenuItem item1, MenuItem item2) {
	    	return (Float.valueOf(item1.getItemValue()/item1.getItemTime()).compareTo(Float.valueOf(item2.getItemValue()/item2.getItemTime())));
	    }
	}
	@Override
    public int compareTo(MenuItem item) {
    	return (Float.valueOf(this.itemValue/this.itemTime).compareTo(item.getItemValue()/item.getItemTime()));
    }
	
	//Getters and Setters
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getItemValue() {
		return itemValue;
	}
	public void setItemValue(float itemValue) {
		this.itemValue = itemValue;
	}
	public float getItemTime() {
		return itemTime;
	}
	public void setItemTime(float itemTime) {
		this.itemTime = itemTime;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public int getMaxItemQuantity() {
		return maxItemQuantity;
	}
	public void setMaxItemQuantity(float totalBudget) {
		this.maxItemQuantity = Double.valueOf(Math.floor(totalBudget/this.itemValue)).intValue();
	}
}
