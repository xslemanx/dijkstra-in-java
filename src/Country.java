import java.util.LinkedList;

import javafx.scene.control.Button;

public class Country implements Comparable<Country>{
	float x;//Latitude 
	float y;// longitude
	String name;//country name
	LinkedList<Edge> edges = new LinkedList<>();//to store all adjacents
	 private double max = Double.MAX_VALUE;//the distance
	 private Country tempPrev;//Previous country
	 Button button = new Button();//button
		public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
		public Country( String name,float x, float y) {
			this.x = x;
			this.y = y;
			this.name = name;
		}
		public Country() {

		}
		public void addAdjacentCity(Country country, double distance) {//to add adjacent country
			edges.add(new Edge(country, distance));
	    }
		
		 public void resetTemps() {//reset all values to zero
		    	max = Double.MAX_VALUE;
		        tempPrev = null;
		    }
		 public LinkedList<Edge> getAdjacentsList() {
		        return edges;
		    }
		 public void setTempCost(double tempCost) {
		        this.max = tempCost;
		    }
		 public double getTempCost() {
		        return max;
		    }
		 public void setTempPrev(Country tempPrev) {
		        this.tempPrev = tempPrev;
		    }
		 public String getFullName() {
		        return name;
		    }
		  public Country getTempPrev() {
		        return tempPrev;
		    }
		@Override
		public String toString() {
			return "Country [x=" + x + ", y=" + y + ", name=" + name + "]";
		}
		@Override
	    public int compareTo(Country o) {
	        if (this.max > o.max) return 1;
	        else if (this.max < o.max) return -1;
	        return 0;
	    }
		
		

}
