package algotest11;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Vertix {
    private City city;
    private ArrayList<Vertix> Adjacents = new ArrayList<>();
    private double distance = Double.MAX_VALUE;
    private boolean known=false;
    private Vertix prev =null;
   
   
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public Vertix getPrev() {
        return prev;
    }

    public void setPrev(Vertix prev) {
        this.prev = prev;
    }

    public Vertix(City city){
        this.city = city;
    }

    public void addAdjacent(Vertix vertex) {
        this.Adjacents.add(vertex);
    }

    public City getCity(){
    	return city;
    
    }
    public ArrayList<Vertix> getAdjacents(){
    	return Adjacents;
    	}

    public void setCity(City city) {
    	this.city = city;
    	}
    
    public void setAdjacents(ArrayList<Vertix> Adjacents){
    	this.Adjacents = Adjacents;
    	}



}