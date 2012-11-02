package de.hbrs.aspgen.ajparser;

import org.aspectj.org.eclipse.jdt.core.dom.Annotation;
import org.aspectj.org.eclipse.jdt.core.dom.BodyDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.Modifier;

public class AbstractAspectJBuilder {

    protected int getModiferyStart(final BodyDeclaration node,
            final int defaultValue) {
        for (final Object obj : node.modifiers()) {
            if (obj instanceof Modifier) {
                final Modifier modifier = (Modifier) obj;
                return modifier.getStartPosition();
            }
        }
        return defaultValue;

    }

    protected int getAnnotationStartPoint(final BodyDeclaration node, final int defaultValue) {
        int minPosition = Integer.MAX_VALUE;
        for (final Object obj : node.modifiers()) {
             if (obj instanceof Annotation) {
                final Annotation annotation = (Annotation) obj;
                if (minPosition > annotation.getStartPosition()) {
                    minPosition = annotation.getStartPosition();
                }
            }
        }
        if (minPosition == Integer.MAX_VALUE) {
            return defaultValue;
        } else {
            return minPosition;
        }
    }

}
