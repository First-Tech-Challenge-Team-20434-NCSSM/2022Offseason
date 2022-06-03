package org.firstinspires.ftc.teamcode.Purepursuit.AStar;


import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

import static java.util.Collections.reverse;

public class AStar {




	final int FIELD_SIZE = 144;
	CurvePoint goal;
	CurvePoint initial;
	PriorityQueue<CurvePoint> openSet = new PriorityQueue<>();
	HashMap<CurvePoint, CurvePoint> cameFrom = new HashMap<>();
	HashMap<CurvePoint, Double> gScore = new HashMap<>();
	HashMap<CurvePoint, Double> fScore = new HashMap<>();
	public CurvePoint[][] field;

	public AStar(CurvePoint goal, CurvePoint initial) {
		this.goal = goal;
		this.initial = initial;
		field = new CurvePoint[FIELD_SIZE + 1][FIELD_SIZE + 1];

		for (int x = -FIELD_SIZE/2; x <= FIELD_SIZE/2; ++x) {
			for (int y = -FIELD_SIZE/2; y <= FIELD_SIZE/2; ++y) {
				field[x+FIELD_SIZE/2][y+FIELD_SIZE/2] = new CurvePoint(x,y);
			}
		}
	}


	public ArrayList<CurvePoint> getNeighbors(CurvePoint point) {

		int [][] directions = {
				{1,0},
				{-1,0},
				{0,1},
				{0,-1},
				{1,1},
				{1,-1},
				{-1,-1},
				{-1,1}
		};

		int indexX = (int)point.x + (FIELD_SIZE / 2);
		int indexY = (int)point.y + (FIELD_SIZE / 2);
		ArrayList<CurvePoint> neighbors = new ArrayList<>();

		for (int[] direction : directions) {
			int newX = indexX + direction[0] ;
			int newY = indexY + direction[1] ;
			if (!(newX < 0 || newX > FIELD_SIZE  || newY < 0 || newY > FIELD_SIZE) ) {
				neighbors.add(new CurvePoint(field[newX][newY]));
			}
		}
		return neighbors;
	}





	/**
	 * straight line distance heuristic function
	 * @param n node we are calculating the weight of
	 * @return weight of n relative to the goal
	 */
	public double H(CurvePoint n) {
		return n.distanceTo(goal);
	}

	protected void computeAStar() {
		gScore.put(initial,0.0);
		fScore.put(initial, H(initial));



	}

	protected double getGScore(CurvePoint c){
		if (gScore.containsKey(c)) {
			return Objects.requireNonNull(gScore.get(c));
		}
		return Double.POSITIVE_INFINITY;
	}

	protected double getFScore(CurvePoint c){
		if (fScore.containsKey(c)) {
			return Objects.requireNonNull(fScore.get(c));
		}
		return Double.POSITIVE_INFINITY;
	}

	protected ArrayList<CurvePoint> cameFrom(HashMap<CurvePoint, CurvePoint> cameFrom, CurvePoint current) {
		ArrayList<CurvePoint> reversedPath = new ArrayList<>();
		reversedPath.add(current);
		CurvePoint c = new CurvePoint(current);
		while (cameFrom.containsKey(c)) {
			c = new CurvePoint(Objects.requireNonNull(cameFrom.get(c)));
			reversedPath.add(c);
		}
		// now the path is the correct direction
		reverse(reversedPath);
		return reversedPath;
	}
}
