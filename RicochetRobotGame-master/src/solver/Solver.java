//package projetS4;

package solver;

import view.*;
import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solver {
	
	// ATTIBUTS
	private Grille g;
	private ArrayList<Pions> pions;
	private Pions robot;
	private ArrayList<Pions> secondaryPions					 = new ArrayList<>();
	private ArrayList<String> currentPath					 = new ArrayList<>();
	private int depth 									 	 = 0;
	private int noeuds 										 = 0;
	private Case startCase 									 = null;
	private Case endCase 									 = null;
	private Pions primaryPion								 = null;
	private ArrayList<Case> path 						 	 = new ArrayList<>();	
	private HashMap<Case, ArrayList<String>> moveList		 = new HashMap<>();
	private HashMap<Case, ArrayList<Pions>> listRobotsToMove = new HashMap<>();
	
	// CONSTRUCTEUR
	public Solver(Grille g, ArrayList<Pions> p, Pions r) {
		this.g = g;
		this.pions = p;
		this.robot = r;
	}

	// METHODES
	
	/**
	 * JOUE SELON LES DIRECTIONS DONNEES PAR L'ALGO A*
	 * @param finalPathString l'ArrayList des directions a prendre
	 * @param p le pion qui joue
	 * @throws Exception des fonctions move
	 */
	
	public void algoPlay(ArrayList<String> finalPathString, Pions p) throws Exception {
		for(String s : finalPathString){
			//Thread.sleep(500);
			//System.out.println("Color : " + this.robot.getColor() + ", Move : " + s);
			switch(s){
				case "haut": p.moveUp(g, true); break;
				case "bas": p.moveDown(g, true); break;
				case "gauche": p.moveLeft(g, true); break;
				case "droite": p.moveRight(g, true); break;					
				default: break;
			} 
			//draw.update();
		}
	}		

	/**
	 * RESOU LA PARTIE
	 * @param robotsToMove la liste de robots pouvant etre deplace
	 * @param did la liste des cases deja visite
	 * @param depth la profondeur 
	 * @param currentPath la chemin optimal actuel
	 * @param previous la case precedente  
	 * @return le currentPath le plus optimal
	 * @throws Exception de la fonction getCase();
	 */
	
	private ArrayList<String> bfs(ArrayList<Pions> robotsToMove, ArrayList<Case> did, int depth, ArrayList<String> currentPath, Case previous, HashMap<Case, ArrayList<Pions>> listRobotsToMove) throws Exception {
		int max = 0;
		if(this.g.getCase(this.primaryPion.getPosition()).getNumber() > 1) { // SI LE PION PRINCIPAL N'EST PAS EN FACE DE LA CIBLE
			// CONDITION PROFONDEUR MAX (a revoir)
			if(this.depth > 10) {
				max = 11;
			} 
			else if(this.depth > 0) {
				max = this.depth + this.g.getCase(this.primaryPion.getPosition()).getNumber() - 1;
			}
			else if(this.depth == 0) {
				max = 1;
			}
			else {
				max = currentPath.size() - this.g.getCase(this.primaryPion.getPosition()).getNumber() - 1;
			}
			if((currentPath.isEmpty()) || (depth < max)) { //verificare la profondità
				for(Pions current : robotsToMove) {
					Case caseTmp = new Case(current.getPosition()[0], current.getPosition()[1]); // Creare un case tmp
						 
					caseTmp.setColor(current.getColor()); // recuperare il colopre del robot attuale
					caseTmp.setNumber(depth); // permette di sapere l'ordine dei colpi da giocare sapendo la profondità
					caseTmp.previous = previous; // permette di accedere al Case precedente per la continuazione
	 
					boolean move = true; // SE DOBBIAMO MUOVERE I ROBOT O FERMARSI PER QUESTA RICORSIVITA'
					int size = 0;
					
					if(did.isEmpty()) { //SE PRIMO PASSAGGIO NEL LOOP
						did.add(caseTmp);
						size = 1;
						System.out.println(current.getColor()); 
					}
					
					if(depth != 0) { 
						listRobotsToMove.put(caseTmp, robotsToMove);
					}
					
					if(previous != null) { 
						if(current.equals(previous.getPosition())) {
							move = false; 
						}
						if(previous.previous != null && previous.getColor().equals(caseTmp.getColor())) {

							if(previous.previous.getPosition()[0] == previous.getPosition()[0] && previous.getPosition()[0] == caseTmp.getPosition()[0]) {
								move = false;
							}
							if(previous.previous.getPosition()[1] == previous.getPosition()[1] && previous.getPosition()[1] == caseTmp.getPosition()[1]) {
								move = false;
							} 
						}
					}
					
					int x = 0; 
					

					
					if(!this.listRobotsToMove.isEmpty()) { 
						for(Map.Entry<Case, ArrayList<Pions>> e : this.listRobotsToMove.entrySet()) {
							for(Pions p : robotsToMove) {
								for(Pions p2 : e.getValue()) {
									if(p.getColor().equals(p2.getColor()) && p.equals(p2.getPosition())) {
										x++;
									}
								}
							}
							if(x == 3 && depth > e.getKey().getNumber()) {
								move = false;
							}  
							x = 0;
							break;
						}
					}
					
					ArrayList<Case> neighbors = caseTmp.getNeighbors(g, current);
					if(move) {
						for(Case neighbor : neighbors) {
							int [] tmp = current.getPosition();
							neighbor = new Case(neighbor.getPosition()[0], neighbor.getPosition()[1]);
							neighbor.setColor(current.getColor());
							
							boolean already = false;
							
							for(Case c : did) {
								if(c.getColor().equals(neighbor.getColor()) && c.equals(neighbor.getPosition())) {
									already = true; 
								}
							}
								 
							this.noeuds ++;

							if(!already) {
								did.add(neighbor);
								current.setPosition(neighbor.getPosition());
	
								primaryPion.setArray(robotsToMove);
								ArrayList<String> neighborPath = this.execAStar(startCase, endCase, primaryPion);
								
								if((((currentPath.size() + this.depth > neighborPath.size() + depth) && !currentPath.isEmpty()) || currentPath.isEmpty()) && !neighborPath.isEmpty()) {
									System.out.println((currentPath.size() + this.depth) + " > " + (neighborPath.size() + depth) + " vide : " + currentPath.isEmpty());
									currentPath = neighborPath;
									neighbor.setNumber(depth + 1); 
									neighbor.previous = caseTmp;

									this.newWay(neighbor, depth, listRobotsToMove, null); 									
								}

								currentPath = bfs(robotsToMove, did, depth + 1, currentPath, caseTmp, listRobotsToMove);
								did.remove(neighbor);
								current.setPosition(tmp);
							}
						}
					}
					if(size == 1 && depth == 0) {
						did = new ArrayList<>();
						System.out.println("fine del ciclo continuo -> did.size() = " + did.size());
						size = 0;
					}
					
					if(depth != 0) {
						listRobotsToMove.remove(caseTmp);
					}
				}
			}
		}
		return currentPath;
	}
	
	/**
	 * TROUVE LE BON CHEMIN
	 * @param neighbor la case voisine
	 * @param depth la profondeur 
	 * @param listRobotsToMove la liste des robots secondaires
	 * @param reste les cases restantes 
	 */

	private void newWay(Case neighbor, int depth, HashMap<Case, ArrayList<Pions>> listRobotsToMove, ArrayList<Case> reste) {
		this.depth = depth;   
		this.listRobotsToMove = listRobotsToMove;
		
		// TRANSFORME LE PATH EN ARRAYLIST
		this.path = new ArrayList<>(); 
		if(reste != null) {
			this.path = reste;
			System.out.println("resto non nullo = " + this.path.size() );
		}
		
		this.path.add(neighbor);
		while(neighbor.previous != null) {
			this.path.add(neighbor.previous);
			neighbor = neighbor.previous;
		}
	}
	
	/**
	 * RESOU LA PARTIE
	 * @return le currentPath le plus optimal
	 * @throws Exception de la fonction getCase();
	 */

	public void solve() throws Exception {

		//INITIALISATION
		ArrayList<String> finalPathString = null; 
		String winColor = ""; 

		//ON TROUVE LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("target") && !p.getColor().equals("black")){ 
				endCase = p.getCase();
				winColor = p.getColor();
			}
		}

		//ON TROUVE LE ROBOT QUI DOIT ALLER SUR LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("robot")){ 
				if(p.getColor().equals(winColor)) {
					startCase = p.getCase(); 
					primaryPion = p;
				}//ON RECUPERER LES ROBOTS SECONDAIRES
				else {
					secondaryPions.add(p);}
			}
		}
		this.currentPath = execAStar(startCase, endCase, primaryPion);
			
		ArrayList<Pions> robotsToMove = this.secondaryPions;
		
		finalPathString = bfs(robotsToMove, new ArrayList<>(), 0, this.currentPath, null, new HashMap<>()); // RECUPERE LE CHEMIN OPTIMAL
		
		// AFFICHE LES DEPLACEMENTS A FAIRE
		if(!this.path.isEmpty()) {
			Case tmp  = null;
			Case same = null;
			ArrayList<Case> listTmp = new ArrayList<>(); 
			for(Case c : this.path) {
				System.out.println(c.getColor() + " : " + c + " (numero di colpi " + c.getNumber() + ")");
				if(tmp != null) {
					if(!tmp.getColor().equals(c.getColor()) && listTmp.size() > 1) {
						if(same != null && same.getColor().equals(c.getColor())) {
							listTmp.add(same); 
						}
						this.moveList.put(tmp, AlgoritmA.trad(listTmp));
						listTmp = new ArrayList<>();
						same = tmp;						
					} 
				}  
				tmp = c;
				listTmp.add(c);
			}
			if(!listTmp.isEmpty()) {
				this.moveList.put(tmp, AlgoritmA.trad(listTmp));
			}
		}
		
		for(Map.Entry<Case, ArrayList<String>> e : this.moveList.entrySet()) {
			System.out.println(e.getKey().getColor() + " : " + e.getValue());
			for(Pions p : robotsToMove) {
				if(p.getColor().equals(e.getKey().getColor()) && !e.getValue().isEmpty()) {
					this.algoPlay(e.getValue(), p);
				}
			} 
		}
		
		System.out.println("Il risolutore ha trovato : " + finalPathString);
//		for(Map.Entry<String, ArrayList<String>> e : this.allPaths.entrySet()) {
//			System.out.println(e.getKey() + " : " + e.getValue());
//		}
		System.out.println("Realizzabile in : " + finalPathString.size() + " colpi (Nodi(s) percorsi(s) : " + this.noeuds);
		
		this.algoPlay(finalPathString, this.robot);
	}

	/**
	 * EXECUTE L'ALGORITHME A*
	 * @param startCase case de depart
	 * @param endCase case d'arrivee
	 * @param robotTmp robot qui doit se deplacer
	 * @return le chemin de depart jusqu'a l'arrivee
	 */

	public ArrayList<String> execAStar(Case startCase, Case endCase, Pions robotTmp){
		AlgoritmA algo = new AlgoritmA(this.g, startCase, endCase, robotTmp);
		algo.AStar();
		ArrayList<Case> finalPath = algo.getPath();
		return algo.trad(finalPath);
	}
}
