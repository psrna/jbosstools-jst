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
package org.jboss.tools.jst.web.ui.editors;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jboss.tools.jst.web.ui.WebUiPlugin;

public class TLDEditorMessages {
	private static final String RESOURCE_BUNDLE= "org.jboss.tools.jst.web.ui.editors.TLDEditorMessages";
	
	private static ResourceBundle resourceBundle= ResourceBundle.getBundle(RESOURCE_BUNDLE);

	private TLDEditorMessages() {}

	public static String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			WebUiPlugin.getPluginLog().logError(e);
			return "%" + key + "%";
		}
	}
	
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
