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
package org.jboss.tools.jst.web.webapp.model;

import org.jboss.tools.common.model.impl.RegularObjectImpl;

public class WebAppServiceRefImpl extends RegularObjectImpl {
	private static final long serialVersionUID = 1L;

	public String name() {
		return getAttributeValue("service-ref-name") + ":" + getAttributeValue("service-interface");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
