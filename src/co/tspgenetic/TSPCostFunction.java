package co.tspgenetic;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class TSPCostFunction implements ICostFunction {
	
	private int[][] costMatrix;

	public TSPCostFunction(int[][] costMatrix) {
		this.costMatrix = costMatrix;
	}

	@Override
	public double evaluate(List<Double> path) throws Exception {
		// TODO Auto-generated method stub
		double cost = 0;
        for (int i = 0; i < path.size()-1; i++) {
        	int s = path.get(i).intValue();
        	int e = path.get(i+1).intValue();
            cost += costMatrix[s-1][e-1];
        }
        cost += costMatrix[path.get(path.size()-1).intValue()-1][path.get(0).intValue()-1];
		return cost;
	}

}
