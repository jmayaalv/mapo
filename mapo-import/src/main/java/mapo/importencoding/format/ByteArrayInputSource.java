/**
 * 
 */
package mapo.importencoding.format;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import org.semanticweb.owlapi.io.OWLOntologyInputSource;

/**
 * @author jmayaalv
 *
 */
public class ByteArrayInputSource implements OWLOntologyInputSource {

	private final URI physicalURI;
	private final byte[] bytes;
	
	/**
	 * @param physicalURI
	 * @param string
	 * @author jmayaalv
	 */
	public ByteArrayInputSource(URI physicalURI, byte[] bytes) {
		super();
		this.physicalURI = physicalURI;
		this.bytes = bytes;
	}
	
	/**
	 * @param physicalURI
	 * @param string
	 * @author jmayaalv
	 */
	public ByteArrayInputSource(byte[] bytes) {
		super();
		this.physicalURI = URI.create("http://org.semanticweb.ontologies/Ontology" + System.nanoTime());
		this.bytes = bytes;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.io.OWLOntologyInputSource#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.bytes);
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.io.OWLOntologyInputSource#getPhysicalURI()
	 */
	@Override
	public URI getPhysicalURI() {
		return this.physicalURI;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.io.OWLOntologyInputSource#getReader()
	 */
	@Override
	public Reader getReader() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.io.OWLOntologyInputSource#isInputStreamAvailable()
	 */
	@Override
	public boolean isInputStreamAvailable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.io.OWLOntologyInputSource#isReaderAvailable()
	 */
	@Override
	public boolean isReaderAvailable() {
		return false;
	}

}
