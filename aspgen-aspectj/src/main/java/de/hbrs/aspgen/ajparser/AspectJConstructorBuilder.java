package de.hbrs.aspgen.ajparser;

import org.aspectj.org.eclipse.jdt.core.dom.InterTypeMethodDeclaration;

import de.hbrs.aspgen.ajparser.type.AspectJ6ITDConstructor;
import de.hbrs.aspgen.ajparser.type.AspectJPositionContent;

public class AspectJConstructorBuilder extends AbstractAspectJBuilder {
    protected final String source;

    public AspectJConstructorBuilder(final String source) {
        this.source = source;
    }


    public AspectJ6ITDConstructor createConstructor(
            final InterTypeMethodDeclaration node) {
        final int javaDocstart = node.getStartPosition();
        final int annotationStart = getAnnotationStartPoint(node, javaDocstart);
        final int modifyerStart = getModiferyStart(node, annotationStart);
        final int onTypeStart = node.getName().getStartPosition();
        final int parameterStart = onTypeStart + node.getName().getLength() + ".new".length();
        final int blockStart = node.getBody().getStartPosition();
        final int end = node.getStartPosition() + node.getLength();

        final AspectJ6ITDConstructor constructor = new AspectJ6ITDConstructor();
        constructor.setStartPosition(node.getStartPosition());
        constructor.setEndPosition(end);
        setJavaDoc(constructor, javaDocstart, annotationStart);
        setAnnotation(constructor, annotationStart, modifyerStart, onTypeStart);
        setModifer(constructor, annotationStart, modifyerStart, onTypeStart);
        setOnType(constructor, onTypeStart, parameterStart);
        setParameters(constructor, parameterStart, blockStart);
        setBlock(constructor, blockStart, end);
        return constructor;
    }


    protected void setBlock(final AspectJ6ITDConstructor method, final int blockStart,
            final int end) {
        final String block = source.substring(blockStart, end);
        method.setBlock(new AspectJPositionContent(blockStart, end, block));
    }

    protected void setParameters(final AspectJ6ITDConstructor method,
            final int parameterStart, final int blockStart) {
        final String parameters = source.substring(parameterStart, blockStart);
        method.setParameters(new AspectJPositionContent(parameterStart, blockStart, parameters));
    }

    protected void setOnType(final AspectJ6ITDConstructor method,
            final int onTypeStart, final int parameterStart) {
        final String onType = source.substring(onTypeStart, parameterStart);
        method.setOnType(new AspectJPositionContent(onTypeStart, parameterStart, onType));
    }

    protected void setModifer(final AspectJ6ITDConstructor method,
            final int annotationStart, final int modifyerStart,
            final int typeStart) {
        if (annotationStart == modifyerStart || modifyerStart == typeStart) {
            method.setModifer(AspectJPositionContent.EMTPY_CONTENT);
        } else {
            final String modiferContent = source.substring(modifyerStart, typeStart);
            method.setModifer(new AspectJPositionContent(modifyerStart, typeStart, modiferContent));
        }
    }

    protected void setAnnotation(final AspectJ6ITDConstructor method,
            final int annotationStart, final int modifyerStart,
            final int typeStart) {
        String annotationContent;
        if (annotationStart == modifyerStart) {
            annotationContent = source.substring(annotationStart, typeStart);
            method.setAnnotations(new AspectJPositionContent(annotationStart, typeStart, annotationContent));
        } else {
            annotationContent = source.substring(annotationStart, modifyerStart);
            method.setAnnotations(new AspectJPositionContent(annotationStart, modifyerStart, annotationContent));
        }
    }

    protected void setJavaDoc(final AspectJ6ITDConstructor method,
            final int javaDocstart, final int annotationStart) {
        if (javaDocstart == annotationStart) {
            method.setJavaDoc(AspectJPositionContent.EMTPY_CONTENT);
        } else {
            final AspectJPositionContent content = new AspectJPositionContent(javaDocstart, annotationStart,
                    source.substring(javaDocstart, annotationStart));
            method.setJavaDoc(content);
        }
    }

}
