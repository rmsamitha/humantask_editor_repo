/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.humantask.editor;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;

/**
 * This class is used to identify Single Lines and whitespaces in text editor.
 * further the white spaces are handled by XMLWhiteSpaceDetector
 * 
 */
public class XMLScanner extends RuleBasedScanner {

	/**
	 * @param manager
	 */
	public XMLScanner(ColorManager manager) {
		IToken procInstr =
		                   new Token(
		                             new TextAttribute(
		                                               manager.getColor(IXMLColorConstants.PROC_INSTR)));

		IRule[] rules = new IRule[2];
		// Add rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new XMLWhitespaceDetector());

		setRules(rules);
	}
}
