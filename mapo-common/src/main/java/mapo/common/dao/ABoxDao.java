package mapo.common.dao;

import java.util.List;
import java.util.Map;

import mapo.common.concept.SimpleConcept;

public interface ABoxDao {
	
	/**
	 * Writes a concept assertion for an individual
	 * @param individual
	 * @param concept
	 * @author jmayaalv
	 */
	public void addConceptAssertion(int individual, int concept);
	
	/**
	 * Gets a list of {@link SimpleConcept} asserted to an individual
	 * @param individual
	 * @author jmayaalv
	 */
	public Map<SimpleConcept, Integer> getConceptAssertion(int individual);
	
	/**
	 * writes an individual role assertion of the type role(individual, object) to the abox
	 * @param individual
	 * @param role
	 * @param object
	 * @author jmayaalv
	 */
	public void addRoleAssertion(int individual, int role, int object);
	
	/**
	 * Gets the asserted objects for an individual an a role
	 * role(indivual, XXX)
	 * @param individual
	 * @param role
	 * @return
	 * @author jmayaalv
	 */
	public Map<SimpleConcept, Integer>  getAssertedObjects(int individual, int role);
	
	/**
	 * Checks if the individual has an associated assertion for the given concept
	 * @param individual
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public boolean hasConceptAssertion(int individual, SimpleConcept concept);
	
	/**
	 * Checks if the concept has an associated assertion for the given role
	 * @param individual
	 * @param role
	 * @return
	 * @author jmayaalv
	 */
	public boolean hasRoleAssertion(int individual, int role);

}
