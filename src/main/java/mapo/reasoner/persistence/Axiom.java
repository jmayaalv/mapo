package mapo.reasoner.persistence;

import mapo.reasoner.term.Term;

public class Axiom {
	
	private final Individual individual;
	private final Term term;
	private Status status;
	
	/**
	 * @param individual
	 * @param term
	 * @param status
	 * @author jmayaalv
	 */
	public Axiom(Individual individual, Term term) {
		super();
		this.individual = individual;
		this.term = term;
		this.status = Status.NEW;
	}
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the individual
	 */
	public Individual getIndividual() {
		return individual;
	}

	/**
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}

	public static enum Status{
		NEW, CLASHED, EXPANDED 
	}
}
