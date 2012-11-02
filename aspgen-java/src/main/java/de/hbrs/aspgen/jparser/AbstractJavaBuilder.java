package de.hbrs.aspgen.jparser;

import java.util.LinkedList;
import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.Annotation;
import org.aspectj.org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.aspectj.org.eclipse.jdt.core.dom.MemberValuePair;
import org.aspectj.org.eclipse.jdt.core.dom.Modifier;
import org.aspectj.org.eclipse.jdt.core.dom.NormalAnnotation;
import org.aspectj.org.eclipse.jdt.core.dom.SingleMemberAnnotation;

import de.hbrs.aspgen.jparser.type.Java6Annotation;

public abstract class AbstractJavaBuilder {
    protected List<Java6Annotation> getAnnotations(final List<?> modifiers, final String source) {
        final List<Java6Annotation> annotations = new LinkedList<>();
        for (final Object object : modifiers) {
            if (object instanceof Annotation) {
                final Annotation orginalAnnotation = (Annotation) object;
                final int startPosition = orginalAnnotation.getStartPosition();
                final int endPosition = orginalAnnotation.getStartPosition() + orginalAnnotation.getLength();

                final Java6Annotation annotation = new Java6Annotation();
                annotation.setStartPosition(startPosition);
                annotation.setEndPosition(endPosition);
                annotation.setAnnotationAsString(source.substring(startPosition, endPosition));

                if (object instanceof MarkerAnnotation) {
                    final MarkerAnnotation markerAnnotation = (MarkerAnnotation) object;
                    annotation.setName(markerAnnotation.getTypeName().getFullyQualifiedName());
                } else if (object instanceof SingleMemberAnnotation) {
                    final SingleMemberAnnotation memberAnnotation = (SingleMemberAnnotation) object;
                    annotation.setName(memberAnnotation.getTypeName().getFullyQualifiedName());
                    annotation.setSingleValue(memberAnnotation.getValue().toString());
                } else if (object instanceof NormalAnnotation) {
                    final NormalAnnotation normalAnnotation = (NormalAnnotation) object;
                    annotation.setName(normalAnnotation.getTypeName().getFullyQualifiedName());
                    final List<?> values = normalAnnotation.values();
                    for (final Object value : values) {
                        final MemberValuePair valuePair = (MemberValuePair) value;
                        annotation.addAttribute(valuePair.getName().getFullyQualifiedName(), valuePair.getValue().toString());
                    }
                }
                annotations.add(annotation);

            }
        }
        return annotations;
    }

    protected boolean isStatic(final List<?> modifiers) {
        for (final Object object : modifiers) {
            if (object instanceof Modifier) {
                final Modifier modifier = (Modifier) object;
                if (modifier.isStatic()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String getAccessType(final List<?> modifiers) {
        for (final Object object : modifiers) {
            if (object instanceof Modifier) {
                final Modifier modifier = (Modifier) object;
                return modifier.toString();
            }
        }
        return "";
    }
}
