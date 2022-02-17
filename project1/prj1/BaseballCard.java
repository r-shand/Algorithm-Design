package prj1;
import java.util.*;
import java.io.*;

public class BaseballCard {
    //attributes
    private String name;
    private int price;
    
    public BaseballCard(String s, int n) {
	this.name = s;
	this.price = n;
    }

    public String getName() {
	return this.name;
    }
    
    public int getPrice() {
	return this.price;
    }
    
    public String toString() {
	return "Name: " + this.name + " | Price: " + this.price;
    }
}
