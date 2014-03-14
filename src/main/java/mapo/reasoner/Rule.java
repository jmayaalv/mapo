package mapo.reasoner;

import java.util.List;

public interface Rule {

	public boolean isApplicable(TableauTree tree);

	public boolean apply(TableauTree tree);

}