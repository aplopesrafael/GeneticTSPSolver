package co.tspgenetic;

import co.mechanism.core.AbstractOptimizer;
import co.mechanism.core.DefaultSearchAgent;
import co.mechanism.core.IMutationProvider;
import co.mechanism.core.ISearchAgent;
import co.mechanism.utils.MersenneTwisterFast;

public class TSP1OPTMutationProvider implements IMutationProvider {

	@Override
	public ISearchAgent mutate(ISearchAgent agent,
			AbstractOptimizer<? extends DefaultSearchAgent> optimizer) {
		// TODO Auto-generated method stub
		MersenneTwisterFast mt = co.mechanism.utils.Utils.getMTInstance();
		int size = agent.getPosition().size();
		//System.out.println("before: "+agent);
		int c1 = mt.nextInt(size);
		int c2;
		do {
			c2 = mt.nextInt(size);
		}while(c2 == c1 || c2 == c1 - 1 || c2 == c1 + 1);
		double city = agent.getPosition().get(c1);
		agent.getPosition().remove(c1);
		agent.getPosition().add(normalize(c2+1, size), city);
		return null;
	}
	
	private int normalize(int pos, int size) {
		
		return pos % size;
	}

}
