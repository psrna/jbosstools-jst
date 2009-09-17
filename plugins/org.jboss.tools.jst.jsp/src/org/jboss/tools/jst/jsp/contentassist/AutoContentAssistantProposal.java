/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.jst.jsp.contentassist;

import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.sse.ui.internal.contentassist.IRelevanceConstants;

/**
 * @author Igels
 */
public class AutoContentAssistantProposal extends CustomCompletionProposal implements ICompletionProposalExtension6 

{

    private boolean autoContentAssistant = false;

	public AutoContentAssistantProposal(String replacementString, int replacementOffset, int replacementLength, int cursorPosition, Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo) {
		super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString,  contextInformation, additionalProposalInfo, IRelevanceConstants.R_NONE);
	}

	public AutoContentAssistantProposal(String replacementString, int replacementOffset, int replacementLength, int cursorPosition, Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo, int relevance) {
	    super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString, contextInformation, additionalProposalInfo, relevance);
	}

	public AutoContentAssistantProposal(boolean autoContentAssistant, String replacementString, int replacementOffset, int replacementLength, int cursorPosition, Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo, int relevance) {
	    super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString, contextInformation, additionalProposalInfo, relevance);
	    this.autoContentAssistant = autoContentAssistant;
	}

	public AutoContentAssistantProposal(String replacementString, int replacementOffset, int replacementLength, int cursorPosition, Image image, String displayString, IContextInformation contextInformation, String additionalProposalInfo, int relevance, boolean updateReplacementLengthOnValidate) {
	    super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString, contextInformation, additionalProposalInfo, relevance, updateReplacementLengthOnValidate);
	}

	public void apply(ITextViewer viewer, char trigger, int stateMask, int offset) {
	    super.apply(viewer, trigger, stateMask, offset);
	    if(autoContentAssistant) {
			Point selection = getSelection(viewer.getDocument());
			viewer.setSelectedRange(selection.x, selection.y);
			if(viewer instanceof ITextOperationTarget) {
				((ITextOperationTarget)viewer).doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
			}
	    }
	}

	/**
	 * Return cursor position of proposal replacement string.
	 */
	@SuppressWarnings("nls")
	public int getCursorPosition() {
	    int cursorPosition = -1;
		int firstAttributeEndPosition = getReplacementString().indexOf("=");
		int openEndTagPosition = getReplacementString().indexOf("</");
		int closeStartAndEndTagPosition = getReplacementString().indexOf("/>");
		if(firstAttributeEndPosition>-1) {
		    cursorPosition = firstAttributeEndPosition + 2;
		} else if(openEndTagPosition>-1) {
		    cursorPosition = openEndTagPosition;
		} else if(closeStartAndEndTagPosition>-1) {
		    cursorPosition = closeStartAndEndTagPosition;
		}
		return cursorPosition>-1?cursorPosition:super.getCursorPosition();
	}

	
	StyledString fStyledDisplayString;
	
	public String getDisplayString() {
		if (super.getDisplayString() != null)
			return super.getDisplayString();
		else {
			if (super.getReplacementString() != null) { 
				setDisplayString(super.getReplacementString());
				return super.getDisplayString();
			}
		}
		return ""; //$NON-NLS-1$
	}
	
	public void setDisplayString(String string) {
		super.setDisplayString(string);
		
		boolean isJavaWordPart = string.length() > 0  && Character.isJavaIdentifierPart(string.charAt(0));
		boolean hasRetType = isJavaWordPart && string.indexOf(':') > 0;
		boolean hasDeclType = isJavaWordPart && string.lastIndexOf('-') > 0;
		
		int p1i = string.indexOf(':');
		int p2i = string.lastIndexOf('-');
		
		String p1 = hasRetType && hasDeclType ? string.substring(0, p2i) : string;
		String p2 = string.substring(p1.length());
		
		StyledString styledString = new StyledString();

		// name, attrs, type
		styledString.append(p1);

		// decl type
		if (p2 != null && p2.length() > 0) 
			styledString.append(p2, StyledString.QUALIFIER_STYLER);
		fStyledDisplayString = styledString; 
	}
	
	public StyledString getStyledDisplayString() {
		if (fStyledDisplayString == null) {
			setDisplayString(super.getDisplayString()); // This re-creates Styled Display String
		}
		return fStyledDisplayString;
	}
	
	public void setStyledDisplayString(StyledString text) {
		fStyledDisplayString = text;
		super.setDisplayString(fStyledDisplayString == null ? "" : fStyledDisplayString.getString());
	}
}