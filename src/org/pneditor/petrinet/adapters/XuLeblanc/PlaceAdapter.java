package org.pneditor.petrinet.adapters.XuLeblanc;

import org.pneditor.petrinet.AbstractPlace;
import petriElement.Place;


public class PlaceAdapter extends AbstractPlace {
	private Place p;
	
	
	public PlaceAdapter(String label) {
		super(label);
		
		// TODO Auto-generated constructor stub
	}
	
	public void setPlace(Place p) {
		this.p=p;
	}
	@Override
	public void addToken() {
		this.setTokens(this.p.getNbToken()+1);

	}

	@Override
	public void removeToken() {
		// TODO Auto-generated method stub
		this.setTokens(this.p.getNbToken()-1);
	}

	@Override
	public int getTokens() {
		// TODO Auto-generated method stub
		return this.p.getNbToken();
	}

	@Override
	public void setTokens(int tokens) {
		// TODO Auto-generated method stub
		try {
		this.p.setNbToken(tokens);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Place getPlace() {
		return p;
	}
}
