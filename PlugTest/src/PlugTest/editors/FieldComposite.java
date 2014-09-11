package PlugTest.editors;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class FieldComposite extends Composite {

	public FieldComposite(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout(3,true));
	}

}
