/**
 * 
 */
package mapo.reasoner.services.rules.impl;

import mapo.reasoner.Rule;
import mapo.reasoner.TableauTree;
import mapo.reasoner.term.AppTerm;
import mapo.reasoner.term.Term;
import mapo.reasoner.term.AppTerm.TermOperation;

/**
 * @author jmayaalv
 * 
 */
public class ConjuctionRule implements Rule {

	@Override
	public boolean isApplicable(TableauTree tree) {
		if (tree.getConcepts() == null || tree.getConcepts().isEmpty()) {
			tree.addAppliedRule(this.getClass());
			return false;
		}
		
		if (tree.isRuleApplied(this.getClass())){
			return false;
		}
		
		return getConjuctionTerm(tree) != null;

	}

	/** 
	 * If the rule can be applied it replaces the node label with 2 expresions coming from the n
	 * A n B - > A;B ... The original expresion A n B dissapears from the label
	 * If the rule can't be applied it returns the node without modifications
	 * 
	 * @param tree
	 */
	@Override
	public boolean apply(TableauTree tree) {
		AppTerm conjuctionTerm = getConjuctionTerm(tree);
		if (conjuctionTerm == null){
			return false; // nothing to apply. It should have been call but to avoid problems return the same tree
			
		}
		
		//Modifies the tree replacing the concept expression that is being used
		
		AppTerm term = getConjuctionTerm(tree);
		tree.getConcepts().add(term.getLhs()); // add first term
		tree.getConcepts().add(term.getRhs()); // add first term
		tree.getConcepts().remove(term); //remove old term
		
		return true;
	}
	
	/**
	 * Gets a {@link AppTerm} representing conjuction from the concept expression
	 * @return
	 * @author jmayaalv
	 */
	private AppTerm getConjuctionTerm(TableauTree tree){
		
		AppTerm nTerm = null;
		for (Term term : tree.getConcepts()) {
			if (term instanceof AppTerm) {
				AppTerm appTerm = (AppTerm)term;
				if (TermOperation.CONJUCTION.equals(appTerm.getOperation())){
					nTerm = appTerm;
					break;
				}
			}
		}
		
		if (nTerm==null){
			tree.addAppliedRule(this.getClass());
		}
		
		return nTerm;
	}

}
