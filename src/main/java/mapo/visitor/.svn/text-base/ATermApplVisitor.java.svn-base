/**
 * 
 */
package mapo.visitor;

import java.util.ArrayList;
import java.util.List;

import jjtraveler.VisitFailure;

import org.mindswap.pellet.utils.ATermUtils;

import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermList;
import aterm.Visitor;

/**
 * @author jmayaalv
 * 
 */
public class ATermApplVisitor extends Visitor {

	private final List<Object> terms = new ArrayList<Object>();;

	/**
	 * @return the terms
	 */
	public List<Object> getTerms() {
		return terms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aterm.Visitor#visitAppl(aterm.ATermAppl)
	 */
	@Override
	public void visitAppl(ATermAppl aterm) throws VisitFailure {
		System.out.println("visiting " + aterm);

		if (ATermUtils.isAnd(aterm)) {
			for (ATerm argument : aterm.getArgumentArray()) {
				argument.accept(this);
			}
		} else if (ATermUtils.isNot(aterm)) {
			ATermList list = ATermUtils.negate(aterm.getArguments());
			list.accept(this);
		}else if (ATermUtils.isAllValues(aterm)){
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aterm.Visitor#visitList(aterm.ATermList)
	 */
	@Override
	public void visitList(ATermList alist) throws VisitFailure {
		int lenght = alist.getLength();
		for (int i = 0; i < lenght; i++) {
			alist.getFirst().accept(this);
		}
	}

}
