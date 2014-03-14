/**
 * 
 */
package mapo.dom;

/**
 * @author jmayaalv
 *
 */
public class Triple<E> {
	
	private final E predicate;
	private E subject;
	private E object;
	
	/**
	 * @param subject
	 * @param predicate
	 * @param object
	 * @author jmayaalv
	 */
	public Triple(E subject, E predicate, E object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	/**
	 * @return the subject
	 */
	public E getSubject() {
		return subject;
	}
	
	public void setSubject(E e){
		this.subject = e;
	}

	/**
	 * @return the predicate
	 */
	public E getPredicate() {
		return predicate;
	}

	/**
	 * @return the object
	 */
	public E getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(E object) {
		this.object = object;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Triple<E> other = (Triple<E>) obj;
		if (object == null) {
			if (other.object != null) {
				return false;
			}
		} else if (!object.equals(other.object)) {
			return false;
		}
		if (predicate == null) {
			if (other.predicate != null) {
				return false;
			}
		} else if (!predicate.equals(other.predicate)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Triple [subject=" + subject + ", predicate=" + predicate + ", object=" + object + "]";
	}
}
