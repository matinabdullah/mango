package io.pelle.mango.db.copy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.google.common.base.Objects;

public class ObjectFieldDescriptor {
	private final String fieldName;

	private final Object sourceValue;
	private final Object targetValue;

	private final Class<?> sourceType;
	private Class<?> targetType;

	private final Method sourceReadMethod;

	private Method targetReadMethod;
	private Method targetWriteMethod;

	public ObjectFieldDescriptor(String fieldName, PropertyDescriptor sourcePropertyDescriptor, Object sourceValue, PropertyDescriptor targetPropertyDescriptor, Object targetValue) {
		super();
		this.fieldName = fieldName;
		this.sourceValue = sourceValue;
		this.targetValue = targetValue;

		this.sourceReadMethod = sourcePropertyDescriptor.getReadMethod();
		this.sourceType = sourcePropertyDescriptor.getPropertyType();

		if (targetPropertyDescriptor != null) {
			this.targetReadMethod = targetPropertyDescriptor.getReadMethod();
			this.targetWriteMethod = targetPropertyDescriptor.getWriteMethod();
			this.targetType = targetPropertyDescriptor.getPropertyType();
		}

	}

	public String getFieldName() {
		return this.fieldName;
	}

	public Object getTargetValue() {
		return this.targetValue;
	}

	public Object getSourceValue() {
		return this.sourceValue;
	}

	@SuppressWarnings("rawtypes")
	public Class getSourceType() {
		return this.sourceType;
	}

	@SuppressWarnings("rawtypes")
	public Class getTargetType() {
		return this.targetType;
	}

	public boolean sourceHasReadMethod() {
		return this.sourceReadMethod != null;
	}

	public boolean targetHasWriteMethod() {
		return this.getTargetWriteMethod() != null;
	}

	public Method getTargetWriteMethod() {
		return this.targetWriteMethod;
	}

	public boolean targetHasReadMethod() {
		return this.targetReadMethod != null;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("fieldName", this.fieldName).toString();
	}

	public boolean isNonNullSourceType(Class<?> type) {
		return getSourceValue() != null && type.isAssignableFrom(getSourceType());
	}

}