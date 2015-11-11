package co.tspgenetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import co.mechanism.optimizers.evolutionary.Individual;
import co.mechanism.optimizers.evolutionary.genetic.ICrossoverProvider;
import co.mechanism.utils.MersenneTwisterFast;

public class TSPOnePointCrossoverProvider implements ICrossoverProvider {
	
	
	private MersenneTwisterFast rng;

	public TSPOnePointCrossoverProvider() {
		this.rng = co.mechanism.utils.Utils.getMTInstance();
	}

	@Override
	public List<Individual> mate(Individual firstParent, Individual secondParent) {
		// TODO Auto-generated method stub
		int point = rng.nextInt(firstParent.getPosition().size());
		List<Double> pos1 = Utils.doubleListDeepCopy(firstParent.getPosition().subList(0, point));
		pos1.addAll(Utils.doubleListDeepCopy(secondParent.getPosition().subList(point, firstParent.getPosition().size())));
		List<Double> pos2 = Utils.doubleListDeepCopy(secondParent.getPosition().subList(point, firstParent.getPosition().size()));
		pos2.addAll(Utils.doubleListDeepCopy(firstParent.getPosition().subList(0, point)));
		//heuristic repair
		Iterator<Double> it = pos1.iterator();
		int[] freqs = new int[pos1.size()];
		int j = 0;
		while(it.hasNext()) {
			freqs[j] = Collections.frequency(pos1, it.next());
			j++;
		}
		for (int i = 0; i < pos1.size(); i++) {
			if (pos1.indexOf(i+1.0) == -1) {
				for (int k = 0; k < pos1.size(); k++) {
					if (freqs[k] > 1) {
						pos1.set(k, new Double(i+1));
						freqs[k] = 1;
						break;
					}
				}
			}
		}
		
		
		it = pos2.iterator();
		freqs = new int[pos2.size()];
		j = 0;
		while(it.hasNext()) {
			freqs[j] = Collections.frequency(pos2, it.next());
			j++;
		}
		for (int i = 0; i < pos2.size(); i++) {
			if (pos2.indexOf(i+1.0) == -1) {
				for (int k = 0; k < pos2.size(); k++) {
					if (freqs[k] > 1) {
						pos2.set(k, new Double(i+1));
						freqs[k] = 1;
						break;
					}
				}
			}
		}
		
		Individual offspring1 = new Individual(pos1);
		Individual offspring2 = new Individual(pos2);
		List<Individual> results = new ArrayList<Individual>();
		results.add(offspring1);
		results.add(offspring2);
		return results;
	}

}
