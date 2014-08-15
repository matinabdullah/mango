package io.pelle.mango.db.vo;

import com.google.common.base.Predicate;

public class AttributeDescriptorAnnotationPredicate implements Predicate<AttributeDescriptorAnnotation<?>> {

	private Class annotationClass;

	AttributeDescriptorAnnotationPredicate(Class annotationClass) {
		super();
		this.annotationClass = annotationClass;
	}

	@Override
	public boolean apply(AttributeDescriptorAnnotation<?> attributeDescriptorAnnotation) {
		return attributeDescriptorAnnotation.getAnnotation() != null && annotationClass.isAssignableFrom(attributeDescriptorAnnotation.getAnnotation().getClass());
	}

}
