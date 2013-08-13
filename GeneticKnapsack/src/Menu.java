import java.util.*;

public class Menu {
	private float totalBudget;
	private ArrayList<MenuItem> items;

	public Menu(float totalBudget) {
		super();
		this.items = new ArrayList<MenuItem>();
		this.totalBudget = totalBudget;		
	}
	public float getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(float totalBudget) {
		this.totalBudget = totalBudget;
	}
	public ArrayList<MenuItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<MenuItem> items) {
		this.items = items;
	}
}


