package de.hbrs.aspgen.ajparser;

import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.InterTypeFieldDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import de.hbrs.aspgen.ajparser.type.AspectJ6ITDField;
import de.hbrs.aspgen.ajparser.type.AspectJPositionContent;

public class AspectJFieldBuilder extends AbstractAspectJBuilder {
    private final String source;

    public AspectJFieldBuilder(final String source) {
        this.source = source;
    }

    public AspectJ6ITDField createField(final InterTypeFieldDeclaration node) {
        final AspectJ6ITDField field = new AspectJ6ITDField();
        final int javaDocstart = node.getStartPosition();
        final int annotationStart = getAnnotationStartPoint(node, javaDocstart);
        final int modifyerStart = getModiferyStart(node, annotationStart);
        final int typeStart = node.getType().getStartPosition();
        final List<?> fragments = node.fragments();
        final Object fieldName = fragments.get(0);
        final VariableDeclarationFragment fragment = (VariableDeclarationFragment) fieldName;
        final int onTypeStart = fragment.getName().getStartPosition() - (node.getOnType().length() + 1);
        final int blockStart = onTypeStart + fragment.getName().getLength() + node.getOnType().length() + 1;
        final int end = node.getStartPosition() + node.getLength();

        field.setStartPosition(node.getStartPosition());
        field.setEndPosition(end);

        setJavaDoc(field, javaDocstart, annotationStart);
        setAnnotation(field, annotationStart, modifyerStart, typeStart);
        setModifer(field, annotationStart, modifyerStart, typeStart);
        setType(field, typeStart, onTypeStart);
        setOnType(field, onTypeStart, blockStart);
        setBlock(field, blockStart, end);
        return field;
    }

    private void setBlock(final AspectJ6ITDField field, final int blockStart,
            final int end) {
        final String block = source.substring(blockStart, end);
        field.setBlock(new AspectJPositionContent(blockStart, end, block));
    }

    private void setOnType(final AspectJ6ITDField field, final int onTypeStart,
            final int blockStart) {
        final String onType = source.substring(onTypeStart, blockStart);
        field.setOnType(new AspectJPositionContent(onTypeStart, blockStart, onType));
    }

    private void setType(final AspectJ6ITDField field, final int typeStart,
            final int onTypeStart) {
        final String type = source.substring(typeStart, onTypeStart);
        field.setType(new AspectJPositionContent(typeStart, onTypeStart, type));
    }

    private void setModifer(final AspectJ6ITDField field,
            final int annotationStart, final int modifyerStart,
            final int typeStart) {
        if (annotationStart == modifyerStart || modifyerStart == typeStart) {
            field.setModifer(AspectJPositionContent.EMTPY_CONTENT);
        } else {
            final String modiferContent = source.substring(modifyerStart, typeStart);
            field.setModifer(new AspectJPositionContent(modifyerStart, typeStart, modiferContent));
        }
    }

    private void setAnnotation(final AspectJ6ITDField field,
            final int annotationStart, final int modifyerStart,
            final int typeStart) {
        String annotationContent;
        if (annotationStart == modifyerStart) {
            annotationContent = source.substring(annotationStart, typeStart);
            field.setAnnotations(new AspectJPositionContent(annotationStart, typeStart, annotationContent));
        } else {
            annotationContent = source.substring(annotationStart, modifyerStart);
            field.setAnnotations(new AspectJPositionContent(annotationStart, modifyerStart, annotationContent));
        }
    }

    private void setJavaDoc(final AspectJ6ITDField field,
            final int javaDocstart, final int annotationStart) {
        if (javaDocstart == annotationStart) {
            field.setJavaDoc(AspectJPositionContent.EMTPY_CONTENT);
        } else {
            final AspectJPositionContent content = new AspectJPositionContent(javaDocstart, annotationStart,
                    source.substring(javaDocstart, annotationStart));
            field.setJavaDoc(content);
        }
    }

}
