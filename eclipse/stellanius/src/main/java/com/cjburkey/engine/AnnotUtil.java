package com.cjburkey.engine;

import java.lang.annotation.Annotation;

public final class AnnotUtil {
	
	public static <T extends Annotation> T getAnnotationOnClass(Class<?> cls, Class<T> annotation) {
		for (Annotation annot : cls.getAnnotations()) {
			if (annotation.isAssignableFrom(annot.getClass())) {
				return annotation.cast(annot);
			}
 		}
		return null;
	}
	
}