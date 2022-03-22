package prj2;
import java.util.*;
import java.io.*;

public class Contestant {
	private int id;
	private int score;
	
	public Contestant(int id, int score) {
		this.id = id;
		this.score = score;
	}
	
	public Contestant(int id) {
		this.id = id;
		this.score = 0;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void removeScore(int score) {
		this.score -= score;
	}
	
	public int getID() {
		return this.id;
	}
}
