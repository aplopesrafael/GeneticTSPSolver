package co.tspgenetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.mechanism.core.AbstractOptimizer;
import co.mechanism.core.ISearchAgent;
import co.mechanism.optimizers.evolutionary.genetic.IPositionProvider;

public class TSPRandomPathProvider implements IPositionProvider {

	@Override
	public List<Double> getRandomPosition(AbstractOptimizer<? extends ISearchAgent> opt) {
		// TODO Auto-generated method stub
		List<Double> position = new ArrayList<Double>();
		for (int i = 0; i < opt.getLowerBound().size(); i++) {
			position.add((double)i+1);
		}
		Collections.shuffle(position);
		return position;
	}

}
