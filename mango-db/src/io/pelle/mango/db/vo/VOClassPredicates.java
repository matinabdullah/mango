package io.pelle.mango.db.vo;

public class VOClassPredicates {

	public static AttributeDescriptorAttributeTypePredicate attributeDescriptorAttributeType(Class<?> clazz) {
		return new AttributeDescriptorAttributeTypePredicate(clazz);
	}

	public static AttributeDescriptorAnnotationPredicate attributeDescriptorAnnotationType(Class<?> annotationClass) {
		return new AttributeDescriptorAnnotationPredicate(annotationClass);
	}

}
