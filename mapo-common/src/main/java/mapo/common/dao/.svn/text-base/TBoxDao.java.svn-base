package mapo.common.dao;

import java.util.List;

import mapo.common.concept.Concept;
import mapo.common.concept.Expression;

public interface TBoxDao {
	
	public void write(int lhs, Concept rhs, boolean equivalent);
	
	public List<Concept> get(int lhs, boolean equivalent);
	
	/**
	 * Get all the expression mentioned in the TBox
	 * @return
	 * @author jmayaalv
	 */
	public List<Expression> getAll(); 
	
}
