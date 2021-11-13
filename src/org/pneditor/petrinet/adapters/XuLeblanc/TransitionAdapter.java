package org.pneditor.petrinet.adapters.XuLeblanc;

import org.pneditor.petrinet.AbstractTransition;
import petriElement.Transition;

public class TransitionAdapter extends AbstractTransition {
	
	private Transition t;
	
	public TransitionAdapter(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}
	
	public void setTransition(Transition t) {
		this.t=t;
		
	}
	
	public Transition getTransition() {
		return t;
	}

}
