package mapo.reasoner.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapo.reasoner.term.IndividualTerm;
import mapo.reasoner.term.Term;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Db {
	
	private Map<IndividualTerm, Boolean> individuals = new HashMap<IndividualTerm, Boolean>();
	private Map<Integer, String> roles = new HashMap<Integer, String>(); //key: i1+i2, value: rolename
	private Multimap<IndividualTerm, List<Term>> axioms = new ArrayListMultimap<IndividualTerm, List<Term>>(); 
	
	public void addIndividual(IndividualTerm term, boolean temp){
		individuals.put(term, temp);
	}
	
	public void addRole(IndividualTerm i1, IndividualTerm i2, String role){
		roles.put(i1.hashCode() + i2.hashCode(), role);
	}
	
	public void addAxiom(IndividualTerm i, List<Term> terms){
		if (terms == null){
			List<Term> newTerms = new ArrayList<Term>();
		}
		axioms.put(i, terms);
	}
	
	public void addAxiom(IndividualTerm i, List<Term> terms, boolean replace){
		//erase if there is data in a
		axioms.removeAll(i);
		addAxiom(i, terms);
	}
}
