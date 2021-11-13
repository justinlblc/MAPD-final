package org.pneditor.petrinet.adapters.XuLeblanc;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;

import Error.NegParException;
import Error.NullException;
import petriElement.ArcEntrant;
import petriElement.ArcSortant;
import petriElement.ArcVideur;
import petriElement.ArcZero;
import petriElement.Place;
import petriNet.PetriNetwork;
import petriElement.Transition;
import java.util.Vector;


public class PetriNetAdapter extends PetriNetInterface {
	private PetriNetwork pn;
	private Vector<PlaceAdapter> listPlaceAdapt;
	private Vector<TransitionAdapter> listTransitionAdapt;
	private Vector<ArcAdapter>  listArcAdapt;
	
	
	public PetriNetAdapter() {
		pn = new PetriNetwork();
		listPlaceAdapt = new Vector<PlaceAdapter>();
		listTransitionAdapt = new Vector<TransitionAdapter>();
		listArcAdapt = new Vector<ArcAdapter>();
	}
	
	
	@Override
	public AbstractPlace addPlace() {
		// TODO Auto-generated method stub
		pn.addPlace(0);
		Vector<Place> glp = pn.getListPlace();
		Place p = glp.get(glp.size()-1);
		PlaceAdapter pa;
		try {
		pa = new PlaceAdapter("Place");
		pa.setPlace(p);
		listPlaceAdapt.add(pa);
		return pa;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public AbstractTransition addTransition() {
		pn.addTransition();
		Vector<Transition> glt = pn.getListTransition();
		Transition t = glt.get(glt.size()-1);
		TransitionAdapter ta = new TransitionAdapter("Transition");
		ta.setTransition(t);
		
		listTransitionAdapt.add(ta);
		return ta;
	}

	
	public int getArcAdaptIndex(AbstractNode source, AbstractNode destination ) {
		for (int i=0;i<listArcAdapt.size();i++) {
			if (listArcAdapt.get(i).isSameArc(source, destination)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	public ArcAdapter addArcSortant(PlaceAdapter p,TransitionAdapter t) {
		pn.addArcSortant(1, p.getPlace(), t.getTransition());
		int index1 = pn.getNbArcSortant()-1;
		ArcSortant as = pn.getArcSortant(index1);
		ArcAdapter asa = new ArcAdapter(p,t,as,index1);
		listArcAdapt.add(asa);
		return asa;
	}
	
	
	public ArcAdapter addArcEntrant(PlaceAdapter place,TransitionAdapter transition) {
		pn.addArcEntrant(1, place.getPlace(), transition.getTransition());
		int index2 = pn.getNbArcEntrant()-1;
		ArcEntrant ae = pn.getArcEntrant(index2);
		ArcAdapter aea = new ArcAdapter(transition,place,ae,index2);
		listArcAdapt.add(aea);
		return aea;
	}
	
	public void changeArcAdapt(ArcAdapter aa, ArcSortant as,ArcEntrant ae,String type) {
		if (type.equals("Regular")) {
			aa.setRegular();
		} else if (type.equals("Inhib")) {
			aa.setInhibitory();
		} else {
			aa.setReset();
		}
		aa.setArcSortant(as);
		aa.setArcEntrant(ae);
		
	}
	@Override
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
		// TODO Auto-generated method stub
		int index = this.getArcAdaptIndex(source,destination);
		
		ArcAdapter aa = null;
		if (index != -1) {
			aa = listArcAdapt.get(index);
			super.removeAbstractArc(aa);
			/*
			if (aa.isSortant()) {
				pn.addArcSortant(1, ((PlaceAdapter)source).getPlace(), ((TransitionAdapter)destination).getTransition());
				changeArcAdapt(aa,pn.getArcSortant(aa.getIndexPn()),null,"Regular");
			} else {
				pn.addArcEntrant(1, ((PlaceAdapter)destination).getPlace(), ((TransitionAdapter)source).getTransition());
				changeArcAdapt(aa,null,pn.getArcEntrant(aa.getIndexPn()),"Regular");
			}
			return listArcAdapt.get(index);
			*/
		}
		
		if (source instanceof PlaceAdapter) {
			PlaceAdapter place =  (PlaceAdapter)source;
			TransitionAdapter transition = (TransitionAdapter)destination;
			return addArcSortant(place,transition);
		}
		
		PlaceAdapter place =  (PlaceAdapter)destination;
		TransitionAdapter transition = (TransitionAdapter)source;
		return addArcEntrant(place,transition);
	}
	
	
	
	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		// TODO Auto-generated method stub
		int index = this.getArcAdaptIndex(place,transition);
		PlaceAdapter p = (PlaceAdapter)place;
		TransitionAdapter t =(TransitionAdapter) transition; 
		if (index != -1) {
			ArcAdapter aa = listArcAdapt.get(index);
			super.removeAbstractArc(aa);
			/*
			pn.addArcSortantZero(p.getPlace(), t.getTransition());
			changeArcAdapt(aa,pn.getArcSortant(aa.getIndexPn()),null,"Inhib");
			return listArcAdapt.get(index);
			*/
		}
		
		pn.addArcSortantZero( p.getPlace(), t.getTransition());
		ArcZero as = (ArcZero)pn.getArcSortant(pn.getNbArcSortant()-1);
		ArcAdapter asa = new ArcAdapter(place,transition,as,pn.getNbArcSortant()-1);
		return asa;
		
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void removePlace(AbstractPlace place) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTransition(AbstractTransition transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeArc(AbstractArc arc) {
		// TODO Auto-generated method stub
		ArcSortant as = ((ArcAdapter)arc).getArcSortant();
		ArcEntrant ae = ((ArcAdapter)arc).getArcEntrant();
		listArcAdapt.remove(arc);
		if (as==null) {
			pn.deleteArcEntrant(((ArcAdapter)arc).getIndexPn());
		}
		pn.deleteArcSortant(0);
		listArcAdapt.remove(arc);
	}

	@Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		Transition t = ((TransitionAdapter)transition).getTransition();
		return t.fireable();
	}

	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		Transition t = ((TransitionAdapter)transition).getTransition();
		t.fire();
	}

}
