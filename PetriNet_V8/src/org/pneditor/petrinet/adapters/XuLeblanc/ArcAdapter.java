package org.pneditor.petrinet.adapters.XuLeblanc;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.ResetArcMultiplicityException;

import petriElement.ArcEntrant;
import petriElement.ArcSortant;
import petriElement.ArcVideur;
import petriElement.ArcZero;

public class ArcAdapter extends AbstractArc {
	private ArcSortant as = null;
	private ArcEntrant ae = null;
	private AbstractNode src;
	private AbstractNode dst;
	private boolean inhib = false;
	private boolean reset = false;
	private int arcEntrantMultiplicity=1;
	private int indexPn;
	private boolean isSortant;
	
	public ArcAdapter(AbstractNode src, AbstractNode dst, int indexPN) {
		this.src=src;
		this.dst=dst;
		this.indexPn=indexPN;
	}
	
	
	public ArcAdapter(AbstractNode src, AbstractNode dst, ArcSortant as, int indexPN) {
		this(src,dst,indexPN);
		this.as=as;
		this.inhib = as instanceof ArcZero;
		this.reset = as instanceof ArcVideur;
		this.isSortant=true;
	}
	
	public ArcAdapter(AbstractNode src, AbstractNode dst, ArcEntrant ae, int indexPN) {
		this(src,dst,indexPN);
		this.ae=ae;
		this.isSortant=false;
	}
	
	@Override
	public AbstractNode getSource() {
		// TODO Auto-generated method stub
		return src;
	}

	@Override
	public AbstractNode getDestination() {
		// TODO Auto-generated method stub
		return dst;
	}

	@Override
	public boolean isReset() {
		// TODO Auto-generated method stub
		return this.reset;
	}

	@Override
	public boolean isRegular() {
		// TODO Auto-generated method stub
		return (!this.isReset())&&(!this.isInhibitory());
	}

	@Override
	public boolean isInhibitory() {
		// TODO Auto-generated method stub
		return this.inhib;
	}

	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		if (ae==null) {
			return as.getWeight();
		}
		return arcEntrantMultiplicity;
	}

	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		if (ae==null) {
			try {
				as.setWeight(multiplicity);
			}catch(Exception e) {
				throw new ResetArcMultiplicityException();
			}
		} else {
			try {
				ae.setWeight(multiplicity);
				arcEntrantMultiplicity=multiplicity;
			}catch(Exception e) {
				throw new ResetArcMultiplicityException();
			}
		}
	}
	
	public void setInhibitory() {
		this.inhib=true;
		this.reset=false;
	}
	
	public void setReset() {
		this.inhib = false;
		this.reset = true;
	}
	
	public void setRegular() {
		this.inhib = false;
		this.reset = false;
	}

	
	public boolean isSameArc(AbstractNode source,AbstractNode destination) {
		return source==src && destination == dst;
	}
	
	public ArcSortant getArcSortant() {
		return as;
	}
	
	public ArcEntrant getArcEntrant() {
		return ae;
	}

	public int getIndexPn() {
		return this.indexPn;
	}
	
	public void setArcSortant(ArcSortant as) {
		this.as=as;
	}
	
	public boolean isSortant() {
		return this.isSortant;
	}
	
	public void setArcEntrant(ArcEntrant ae) {
		this.ae=ae;
	}
}
