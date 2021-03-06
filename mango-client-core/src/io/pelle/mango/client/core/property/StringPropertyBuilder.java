package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.base.property.PROPERTY_VALUE_TYPE;

public class StringPropertyBuilder extends BasePropertyBuilder<String> {

	public StringPropertyBuilder() {
	}

	public StringPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}
	
	public StringPropertyBuilder(String key, PROPERTY_TYPE type) {
		super(key, type);
	}

	@Override
	public String parseValue(String valueString) {
		return valueString;
	}

	@Override
	public PROPERTY_VALUE_TYPE getValueType() {
		return PROPERTY_VALUE_TYPE.STRING;
	}

}
