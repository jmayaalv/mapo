/**
 * 
 */
package mapo.reasoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeModel;

import mapo.reasoner.services.rules.impl.ConjuctionRule;
import mapo.reasoner.services.rules.impl.DisjunctionRule;
import mapo.reasoner.services.rules.impl.ExistentialRestrictionRule;

/**
 * Visits a {@link TableauTree} applying the required rules. Each Rule could
 * create new trees, modify the current one, create children
 * 
 * @author jmayaalv
 * 
 */
public class CompletionRulesVisitor implements Visitor {

	private final List<Rule> rules;

	public CompletionRulesVisitor() {
		rules = new ArrayList<Rule>();
		this.rules.add(new ConjuctionRule());
		this.rules.add(new DisjunctionRule());
		this.rules.add(new ExistentialRestrictionRule());
	}

	/**
	 * @param rules
	 * @author jmayaalv
	 */
	public CompletionRulesVisitor(List<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.reasoner.Visitor#visit(mapo.reasoner.TableauTree)
	 */
	@Override
	public boolean visit(TableauTree tree) {

		//check what rule could be applied
		boolean ruleApplied = false;
		for (Rule rule : rules) {
			if (rule.isApplicable(tree)) {
				rule.apply(tree);
				ruleApplied = true;
				break;
			}
		}

		if (!ruleApplied && tree.hasChildren()) {
			for (Map.Entry<String, TableauTree> child : tree.getChildren().entrySet()) {
				if (!child.getValue().isExpanded()){
					ruleApplied = child.getValue().accept(new CompletionRulesVisitor());
				}
			}
		}

		if (!ruleApplied) {
			tree.setStatus(TableauTree.Status.EXPANDED);
		}

		return ruleApplied;
	}

}
