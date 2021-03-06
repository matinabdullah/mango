/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;
import io.pelle.mango.client.web.test.controls.BooleanTestControl;
import io.pelle.mango.client.web.test.controls.ControlGroupTestControl;
import io.pelle.mango.client.web.test.controls.DateTestControl;
import io.pelle.mango.client.web.test.controls.DecimalTestControl;
import io.pelle.mango.client.web.test.controls.EnumerationTestControl;
import io.pelle.mango.client.web.test.controls.IntegerTestControl;
import io.pelle.mango.client.web.test.controls.ReferenceTestControl;
import io.pelle.mango.client.web.test.controls.TextTestControl;
import io.pelle.mango.client.web.test.util.FocusableTestWidget;

public abstract class BaseDictionaryModuleTestUI implements FocusableTestWidget {

	private IBaseDictionaryModule baseDictionaryModule;

	public BaseDictionaryModuleTestUI(IBaseDictionaryModule baseDictionaryModule, String uiModuleId) {
		super();
		this.baseDictionaryModule = baseDictionaryModule;
	}

	public TextTestControl getControl(TextControlModel controlModel) {
		return new TextTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public void onResize() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <VOType extends IBaseVO> ReferenceTestControl<VOType> getControl(ReferenceControlModel<VOType> controlModel) {
		return new ReferenceTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public BooleanTestControl getControl(BooleanControlModel controlModel) {
		return new BooleanTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public DateTestControl getControl(DateControlModel controlModel) {
		return new DateTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public IntegerTestControl getControl(IntegerControlModel controlModel) {
		return new IntegerTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public DecimalTestControl getControl(BigDecimalControlModel controlModel) {
		return new DecimalTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public <ENUM_TYPE> EnumerationTestControl<ENUM_TYPE> getControl(EnumerationControlModel<ENUM_TYPE> controlModel) {
		return new EnumerationTestControl<ENUM_TYPE>(this.baseDictionaryModule.getElement(controlModel));
	}

	public ControlGroupTestControl getGroupControlTest(ControlGroupModel controlModel) {
		return new ControlGroupTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public boolean isInstanceOf(String moduleUrl) {
		return this.baseDictionaryModule.isInstanceOf(moduleUrl);
	}

	public void updateUrl(String moduleUrl) {
	}

	@Override
	public void onFocusEnter() {
	}

	@Override
	public void onFocusLeave() {
	}
}
