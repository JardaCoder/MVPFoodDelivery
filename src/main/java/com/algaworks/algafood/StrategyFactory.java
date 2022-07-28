package com.algaworks.algafood;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.Strategy;
import com.algaworks.algafood.domain.enums.Notificador;

@Component
public class StrategyFactory {

	  private Map<Notificador, Strategy> strategies;
	  
	  public StrategyFactory(Set<Strategy> strategySet) {
	     createStrategy(strategySet);
	  }
	  
	  public Strategy findStrategy(Notificador strategyName) {
	     return strategies.get(strategyName);
	  }
	  
	  private void createStrategy(Set<Strategy> strategySet) {
	      strategies = new HashMap<Notificador, Strategy>();
	      strategySet.forEach( 
	   strategy ->strategies.put(strategy.getStrategyName(), strategy));
	  }
}
