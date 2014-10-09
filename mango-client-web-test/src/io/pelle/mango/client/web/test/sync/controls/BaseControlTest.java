package io.pelle.mango.client.web.test.sync.controls;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;

import org.junit.Assert;

import com.google.common.base.Joiner;

public class BaseControlTest<ElementType extends IBaseControl<ValueType, ?>, ValueType> implements IControlUpdateListener {

	private ValueType value;

	private ElementType baseControl;

	private IValidationMessages validationMessages;

	public BaseControlTest(ElementType baseControl) {
		this.baseControl = baseControl;

		baseControl.addUpdateListener(this);
		onUpdate();
	}

	public void assertValue(ValueType expectedValue) {
		Assert.assertEquals(expectedValue, this.baseControl.getValue());
	}

	public ValueType getValue() {
		return this.value;
	}

	protected ElementType getBaseControl() {
		return this.baseControl;
	}

	public void setValue(ValueType value) {
		this.baseControl.setValue(value);
	}

	public void parse(String valueString) {
		this.baseControl.parseValue(valueString);
	}

	public void assertMandatory() {
		Assert.assertTrue(getBaseControl().isMandatory());
	}

	public void assertHasErrors() {
		Assert.assertTrue(this.baseControl.getValidationMessages().hasErrors());
	}

	public void assertHasNoErrors() {
		Assert.assertFalse(this.baseControl.getValidationMessages().hasErrors());
	}

	public void assertHasErrorWithText(String text) {

		for (IValidationMessage validationMessage : validationMessages) {
			if (validationMessage.getHumanMessage().equals(text)) {
				return;
			}
		}

		String message = "message containing '" + text + "' not found (messages: " + Joiner.on(", ").join(baseControl.getValidationMessages()) + ")";
		Assert.fail(message);
	}

	protected ElementType getControl() {
		return baseControl;
	}

	@Override
	public void onUpdate() {
		this.value = this.baseControl.getValue();
		this.validationMessages = baseControl.getValidationMessages();
	}

}
