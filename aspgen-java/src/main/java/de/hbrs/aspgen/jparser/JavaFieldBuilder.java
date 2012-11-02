package de.hbrs.aspgen.jparser;

import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.FieldDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class JavaFieldBuilder extends AbstractJavaBuilder {

    public Java6Field createField(final FieldDeclaration node,
            final VariableDeclarationFragment fragment, final String source) {
        final Java6Field field = new Java6Field();
        final String type = node.getType().toString();
        final List<?> modifiers = node.modifiers();
        final List<Java6Annotation> annotations = getAnnotations(modifiers, source);
        field.setName(fragment.getName().getFullyQualifiedName());
        field.setType(type);
        field.setStatic(isStatic(modifiers));
        field.setAccessType(getAccessType(modifiers));
        field.setStartPosition(node.getStartPosition());
        for (final Java6Annotation java6Annotation : annotations) {
            field.addAnnotation(java6Annotation);
        }
        return field;
    }
}
