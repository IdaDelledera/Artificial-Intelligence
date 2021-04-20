//package projetS4;

package model;

import view.*;
import controller.*;
import solver.*;

import java.util.ArrayList;
import java.util.Random;

public class Pions {

	// ATTRIBUTS
	protected int [] position;
	protected String color;
	protected String type;
	private ArrayList<Pions> pions;


	public Pions(int x, int y, String color, String type) {
		this.color = color;
		this.type = type;
		this.position = new int [] {x, y};
	}

	// GETTERS
	public String getColor() {return this.color;}
	public int [] getPosition() {return this.position;}
	public String getType(){return this.type;}
	public Case getCase(){return new Case(this.position[0], this.position[1]);}
	
	// SETTERS
	public void setPosition(int [] position){this.position = position;}
	public void setArray(ArrayList<Pions> p) {this.pions = p;}


	public void setPion(ArrayList<Pions> p, Random randomGenerator){
		if(!this.isEmpty(p)){
			this.position[0] = randomGenerator.nextInt(16);
			this.position[1] = randomGenerator.nextInt(16);
		}
	}
	
	// METHODES

	private boolean isEmpty(ArrayList<Pions> pions){
		for(Pions p : pions){
			if(this.equals(p.getPosition())){
				return false;
			}
		}
		return true;
	}

	public boolean equals(int [] position){
		return java.util.Arrays.equals(position, this.position);
	}
	


	public boolean gameOver(ArrayList <Pions> pions){
		if(this.getType() == "target") {
			for(Pions p : pions) {
				if(this.equals(p.getPosition()) && p.getType() == "robot" && p.getColor() == this.getColor())
					return true;
			}
		}		
		return false;
	}


	public boolean isRobotHere(int x, int y) {
		for(Pions p : this.pions) {
			if(p.getType() == "robot" && p.equals(new int [] {x, y}))
				return true;
		}
		return false; 
	}

	// MOVE PIONS 


	public void moveUp(Grille g, boolean pass) throws Exception { 
		if(pass) {
			g.getCase(this.position).setPassageSolver(pass);
			g.getCase(this.position).setColor(this.color);
		}
		boolean stop = false;
		while(!stop) {
			int x = this.position[0] - 1; int y = this.position[1];
			if(g.getCase(this.position).getMurs()[0] || this.isRobotHere(x, y)) 
				stop = true;
			else {
				this.setPosition(new int [] {x, y});
				if(pass) {
					g.getCase(this.position).setPassageSolver(pass);
					g.getCase(this.position).setColor(this.color);
				}
			}
		} 
	}



	public void moveDown(Grille g, boolean pass) throws Exception {
		if(pass) {
			g.getCase(this.position).setPassageSolver(pass);
			g.getCase(this.position).setColor(this.color);
		}
		boolean stop = false;
		while(!stop) {
			int x = this.position[0] + 1; int y = this.position[1];
			if(g.getCase(this.position).getMurs()[2] || this.isRobotHere(x, y))
				stop = true;
			else {
				this.setPosition(new int [] {x, y});
				if(pass) {
					g.getCase(this.position).setPassageSolver(pass);
					g.getCase(this.position).setColor(this.color);
				}
			}
		}
	}
	


	public void moveRight(Grille g, boolean pass) throws Exception {
		boolean stop = false;
		if(pass) {
			g.getCase(this.position).setPassageSolver(pass);
			g.getCase(this.position).setColor(this.color);
		}
		while(!stop) {
			int x = this.position[0]; int y = this.position[1] + 1;
			if(g.getCase(this.position).getMurs()[1] || this.isRobotHere(x, y))
				stop = true;
			else {
				this.setPosition(new int [] {x, y});
				if(pass) {
					g.getCase(this.position).setPassageSolver(pass);
					g.getCase(this.position).setColor(this.color);
				}
			}
		}
	}
	

	public void moveLeft(Grille g, boolean pass) throws Exception {
		if(pass) {
			g.getCase(this.position).setPassageSolver(pass);
			g.getCase(this.position).setColor(this.color);
		}
		boolean stop = false;
		while(!stop) {
			int x = this.position[0]; int y = this.position[1] - 1;
			if(g.getCase(this.position).getMurs()[3] || this.isRobotHere(x, y))
				stop = true;
			else {
				this.setPosition(new int [] {x, y});
				if(pass) {
					g.getCase(this.position).setPassageSolver(pass);
					g.getCase(this.position).setColor(this.color);
				}
			}
		}
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return this.getPosition()[0] + ", " + this.getPosition()[1];
	}
}
