package de.hbrs.aspgen.jparser;

import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.MethodDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class JavaMethodBuilder extends AbstractJavaBuilder {
    public Java6Method createMethod(final MethodDeclaration node, final String source) {
        final Java6Method method = new Java6Method();
        method.setStartPosition(node.getStartPosition());
        method.setName(node.getName().getFullyQualifiedName());
        method.setType(node.getReturnType2().toString());
        method.setAccessType(getAccessType(node.modifiers()));
        final List<?> modifiers = node.modifiers();

        final List<Java6Annotation> annotations = getAnnotations(modifiers, source);
        for (final Java6Annotation java6Annotation : annotations) {
            method.addAnnotation(java6Annotation);
        }
        method.setStatic(isStatic(modifiers));

        final List<?> parameters = node.parameters();
        for (final Object object : parameters) {
            final Java6Parameter parameter = new Java6Parameter();
            final SingleVariableDeclaration variableDeclaration = (SingleVariableDeclaration) object;
            parameter.setStartPosition(variableDeclaration.getStartPosition());
            parameter.setName(variableDeclaration.getName().getFullyQualifiedName());
            parameter.setType(variableDeclaration.getType().toString());
            final List<Java6Annotation> annotations2 = getAnnotations(variableDeclaration.modifiers(), source);
            for (final Java6Annotation java6Annotation : annotations2) {
                parameter.addAnnotation(java6Annotation);
            }
            method.addParameter(parameter);
        }
        return method;
    }
}
