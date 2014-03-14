/**
 * 
 */
package mapo.reasoner.services.rules.impl;

import java.util.ArrayList;
import java.util.List;

import mapo.reasoner.Rule;
import mapo.reasoner.TableauModel;
import mapo.reasoner.TableauTree;
import mapo.reasoner.term.AppTerm;
import mapo.reasoner.term.Term;
import mapo.reasoner.term.AppTerm.TermOperation;

/**
 * @author jmayaalv
 * 
 */
public class DisjunctionRule implements Rule {

	@Override
	public boolean isApplicable(TableauTree tree) {
		if (tree.getConcepts() == null || tree.getConcepts().isEmpty()) {
			tree.addAppliedRule(this.getClass());
			return false;
		}

		if (tree.isRuleApplied(this.getClass())) {
			return false;
		}

		return getDisjunctionTerm(tree) != null;

	}

	/**
	 * This rules creates splits the current tree in two different ones.
	 * 
	 * @param tree
	 */
	@Override
	public boolean apply(TableauTree tree) {
		AppTerm disjunctionTerm = getDisjunctionTerm(tree);
		if (disjunctionTerm == null) {
			return false; // nothing to apply. It should have been call but to avoid problems return the same tree

		}

		TableauModel model = tree.getModel();
		
		List<Term> concepts = new ArrayList<Term>();
		List<Term> concepts2 = new ArrayList<Term>();
		for (Term term : tree.getConcepts()){
			if (!term.equals(disjunctionTerm)){
				concepts.add(term);
				concepts2.add(term);
			}
		}
		
		concepts.add(disjunctionTerm.getLhs());
		concepts2.add(disjunctionTerm.getRhs());
		

		model.addTree(new TableauTree(tree.getKey(), concepts));
		model.addTree(new TableauTree(tree.getKey(), concepts2));
		model.getTrees().remove(tree);
		
		return true;
	}

	/**
	 * Gets a {@link AppTerm} representing conjuction from the concept
	 * expression
	 * 
	 * @return
	 * @author jmayaalv
	 */
	private AppTerm getDisjunctionTerm(TableauTree tree) {

		AppTerm nTerm = null;
		for (Term term : tree.getConcepts()) {
			if (term instanceof AppTerm) {
				AppTerm appTerm = (AppTerm) term;
				if (TermOperation.DISJUNCTION.equals(appTerm.getOperation())) {
					nTerm = appTerm;
					break;
				}
			}
		}

		if (nTerm == null) {
			tree.addAppliedRule(this.getClass());
		}

		return nTerm;
	}

}
