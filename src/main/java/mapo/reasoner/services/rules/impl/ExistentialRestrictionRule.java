/**
 * 
 */
package mapo.reasoner.services.rules.impl;

import mapo.reasoner.Rule;
import mapo.reasoner.TableauTree;
import mapo.reasoner.term.IndividualTerm;
import mapo.reasoner.term.RoleTerm;
import mapo.reasoner.term.Term;

/**
 * @author jmayaalv
 * 
 */
public class ExistentialRestrictionRule implements Rule {

	@Override
	public boolean isApplicable(TableauTree tree) {
		if (tree.getConcepts() == null || tree.getConcepts().isEmpty()) {
			tree.addAppliedRule(this.getClass());
			return false;
		}

		if (tree.isRuleApplied(this.getClass())) {
			return false;
		}

		return getExistencialTerm(tree) != null;

	}

	/**
	 * This rules creates splits the current tree in two different ones.
	 * 
	 * @param tree
	 */
	@Override
	public boolean apply(TableauTree tree) {
		RoleTerm eTerm = getExistencialTerm(tree);
		if (eTerm == null) {
			return false; // nothing to apply. It should have been call but to avoid problems return the same tree

		}

		tree.getConcepts().remove(eTerm);
		tree.addChildren(eTerm.getRole(), new TableauTree(new IndividualTerm("a" + tree.getTotalChildren(), true), eTerm.getTerm()));
		return true;
	}

	/**
	 * @return
	 * @author jmayaalv
	 */
	private RoleTerm getExistencialTerm(TableauTree tree) {

		RoleTerm rTerm = null;
		for (Term term : tree.getConcepts()) {
			if (term instanceof RoleTerm) {
				RoleTerm roleTerm = (RoleTerm) term;
				if (RoleTerm.RoleTermOperation.EXISTENTIAL_RESTRICTION.equals(roleTerm.getOperation())) {
					rTerm = roleTerm;
					break;
				}
			}
		}

		if (rTerm == null) {
			tree.addAppliedRule(this.getClass());
		}

		return rTerm;
	}
}
