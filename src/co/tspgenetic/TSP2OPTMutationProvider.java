package co.tspgenetic;

import java.util.Collections;

import co.mechanism.core.AbstractOptimizer;
import co.mechanism.core.DefaultSearchAgent;
import co.mechanism.core.IMutationProvider;
import co.mechanism.core.ISearchAgent;
import co.mechanism.utils.MersenneTwisterFast;

public class TSP2OPTMutationProvider implements IMutationProvider {

	@Override
	public ISearchAgent mutate(ISearchAgent agent, AbstractOptimizer<? extends DefaultSearchAgent> optimizer) {
		MersenneTwisterFast mt = co.mechanism.utils.Utils.getMTInstance();
		int size = agent.getPosition().size();
		//System.out.println("before: "+agent);
		int c1 = mt.nextInt(size);
		int c2;
		do {
			c2 = mt.nextInt(size);
		}while(c2 == c1);
		Collections.swap(agent.getPosition(),c1,c2);
		//System.out.println("after: "+agent);
		return agent;
	}

}
