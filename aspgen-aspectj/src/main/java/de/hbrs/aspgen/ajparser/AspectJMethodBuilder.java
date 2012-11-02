package de.hbrs.aspgen.ajparser;

import org.aspectj.org.eclipse.jdt.core.dom.InterTypeMethodDeclaration;

import de.hbrs.aspgen.ajparser.type.AspectJ6ITDMethod;
import de.hbrs.aspgen.ajparser.type.AspectJPositionContent;

public class AspectJMethodBuilder extends AspectJConstructorBuilder {

    public AspectJMethodBuilder(final String source) {
        super(source);
    }

    public AspectJ6ITDMethod createMethod(
            final InterTypeMethodDeclaration node, final int typeStart) {
        final int javaDocstart = node.getStartPosition();
        final int annotationStart = getAnnotationStartPoint(node, javaDocstart);
        final int modifyerStart = getModiferyStart(node, annotationStart);
        final int onTypeStart = node.getName().getStartPosition() - (node.getOnType().length() + 1);
        final int parameterStart = onTypeStart + node.getName().getLength() + node.getOnType().length() + 1;
        final int blockStart = node.getBody().getStartPosition();
        final int end = node.getStartPosition() + node.getLength();
        final AspectJ6ITDMethod method = new AspectJ6ITDMethod();
        method.setStartPosition(node.getStartPosition());
        method.setEndPosition(end);
        setJavaDoc(method, javaDocstart, annotationStart);
        setAnnotation(method, annotationStart, modifyerStart, typeStart);
        setModifer(method, annotationStart, modifyerStart, typeStart);
        setType(method, typeStart, onTypeStart);
        setOnType(method, onTypeStart, parameterStart);
        setParameters(method, parameterStart, blockStart);
        setBlock(method, blockStart, end);
        return method;
    }

    private void setType(final AspectJ6ITDMethod method, final int typeStart,
            final int onTypeStart) {
        final String type = source.substring(typeStart, onTypeStart);
        method.setType(new AspectJPositionContent(typeStart, onTypeStart, type));
    }

}
