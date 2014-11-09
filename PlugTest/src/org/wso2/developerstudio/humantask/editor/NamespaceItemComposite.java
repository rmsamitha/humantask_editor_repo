package org.wso2.developerstudio.humantask.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NamespaceItemComposite extends Composite {
	protected Text txtPrefix;
	protected Text txtNamespace;
	protected int itemIndex;
	protected Button btnRemoveNamespace ;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param i 
	 */
	public NamespaceItemComposite(Composite parent, int style, int itemIndex) {
		super(parent, style);
		this.itemIndex=itemIndex;
		setLayout(new GridLayout(10, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText(" Prefix:   xmlns:");
		
		txtPrefix = new Text(this, SWT.BORDER);
		GridData gd_txtPrefix = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtPrefix.widthHint = 100;
		txtPrefix.setLayoutData(gd_txtPrefix);
	
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setText("    "+HTEditorConstants.NAMESPACE_TITLE);

		
		txtNamespace = new Text(this, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_text_1.widthHint = 300;
		txtNamespace.setLayoutData(gd_text_1);

		
		btnRemoveNamespace= new Button(this, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.heightHint = 23;
		gd_btnNewButton.widthHint = 27;
		btnRemoveNamespace.setLayoutData(gd_btnNewButton);
		btnRemoveNamespace.setToolTipText(HTEditorConstants.REMOVE_NAMESPACE_TITLE);
		btnRemoveNamespace.setText("-");
	}

	@Override
	protected void checkSubclass() {
		
	}
}
