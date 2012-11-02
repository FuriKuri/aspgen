package de.hbrs.aspgen.ajparser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.org.eclipse.jdt.core.dom.ASTNode;
import org.aspectj.org.eclipse.jdt.core.dom.AdviceDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.AfterAdviceDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.AjASTVisitor;
import org.aspectj.org.eclipse.jdt.core.dom.Annotation;
import org.aspectj.org.eclipse.jdt.core.dom.AroundAdviceDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.BeforeAdviceDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.BodyDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.CompilationUnit;
import org.aspectj.org.eclipse.jdt.core.dom.DeclareSoftDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.ImportDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.InterTypeFieldDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.InterTypeMethodDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.aspectj.org.eclipse.jdt.core.dom.MethodDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.MethodInvocation;
import org.aspectj.org.eclipse.jdt.core.dom.NormalAnnotation;
import org.aspectj.org.eclipse.jdt.core.dom.PackageDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.QualifiedName;
import org.aspectj.org.eclipse.jdt.core.dom.SimpleName;
import org.aspectj.org.eclipse.jdt.core.dom.SimpleType;
import org.aspectj.org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.aspectj.org.eclipse.jdt.core.dom.TypeDeclaration;

import de.hbrs.aspgen.ajparser.type.AspectJ6Advice;
import de.hbrs.aspgen.ajparser.type.AspectJ6Class;
import de.hbrs.aspgen.ajparser.type.AspectJ6Declare;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDConstructor;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDField;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDMethod;
import de.hbrs.aspgen.ajparser.type.AspectJPositionContent;
import de.hbrs.aspgen.api.ast.AspectJBlock;

public class AspectJVisitor extends AjASTVisitor {
    private static final String GENERATOR_ANNOTATION_REGEX = "@Generated\\([^)]*\\)";
    private final AspectJ6Class aspectJClass;
    private final String source;
    private final AspectJAdviceBuilder aspectJAdviceBuilder;
    private final AspectJFieldBuilder aspectJFieldBuilder;
    private final AspectJMethodBuilder aspectJMethodBuilder;
    private final AspectJConstructorBuilder aspectJConstructorBuilder;
    private boolean isGeneratedBlockPositionSet = false;

    public AspectJVisitor(final AspectJ6Class result, final String source) {
        this.aspectJClass = result;
        this.source = source;
        this.aspectJAdviceBuilder = new AspectJAdviceBuilder(source);
        this.aspectJFieldBuilder = new AspectJFieldBuilder(source);
        this.aspectJMethodBuilder = new AspectJMethodBuilder(source);
        this.aspectJConstructorBuilder = new AspectJConstructorBuilder(source);
    }

    @Override
    public boolean visit(final DeclareSoftDeclaration node) {
        final int start = node.getStartPosition();
        final int end = start + node.getLength() + 1; // for semikolon
        final int newLinePosition = source.lastIndexOf("\n", start) - 1;
        final int annotationEndPosition = source.lastIndexOf(")", newLinePosition);
        final int commentStart = source.lastIndexOf("\n", annotationEndPosition) + 1;
        final int spacesBefore = countSpaces(source.substring(commentStart));
//        commentStart =+ spacesBefore;

        final String annotation = source.substring(commentStart + spacesBefore, annotationEndPosition + 1).replaceFirst("\\s*//", "");

        final AspectJ6Declare declare = new AspectJ6Declare();
        declare.setAnnotations(new AspectJPositionContent(commentStart + spacesBefore, annotationEndPosition + 1, annotation));
        declare.setEndPosition(end);
        declare.setStartPosition(commentStart + spacesBefore);
        declare.setBlock(new AspectJPositionContent(start, end, source.substring(start, end)));
        aspectJClass.addDeclares(declare);
        return super.visit(node);
    }

    @Override
    public boolean visit(final QualifiedName node) {
        aspectJClass.addUsedType(node.getFullyQualifiedName().split("\\.")[0]);
        return super.visit(node);
    }

    private int countSpaces(final String string) {
        int i = 0;
        while (string.charAt(i) == ' ') {
            i++;
        }
        return i;
    }

    @Override
    public boolean visit(final PackageDeclaration node) {
        final int start = node.getStartPosition();
        final int end = node.getStartPosition() + node.getLength();
        aspectJClass.setPackageName(
                new AspectJPositionContent(start, end, source.substring(start, end)));
        return super.visit(node);
    }

    @Override
    public boolean visit(final NormalAnnotation node) {
        aspectJClass.addUsedType(node.getTypeName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public boolean visit(final MarkerAnnotation node) {
        aspectJClass.addUsedType(node.getTypeName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
        for (final Object obj : node.thrownExceptions()) {
            aspectJClass.addUsedType(((SimpleName) obj).getFullyQualifiedName());
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(final SingleMemberAnnotation node) {
        aspectJClass.addUsedType(node.getTypeName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public boolean visit(final SimpleType node) {
        aspectJClass.addUsedType(node.getName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public void endVisit(final MethodInvocation node) {
        aspectJClass.addUsedMethod(node.getName().getFullyQualifiedName());
        super.endVisit(node);
    }

    @Override
    public void endVisit(final CompilationUnit node) {
        final List<?> types = node.types();
        for (final Object object : types) {
            if (object instanceof TypeDeclaration) {
                final TypeDeclaration typeDeclaration = (TypeDeclaration) object;
                final int start = typeDeclaration.getStartPosition();
                final int end = start + typeDeclaration.getLength();
                final String typeDeclarationAsString = source.substring(typeDeclaration.getStartPosition(), end);
                if (aspectJClass.getNextBlockPositionToWrite() == 0) {
                    aspectJClass.setNextFreeBlockPosition(start + typeDeclarationAsString.indexOf("{") + 1);
                }
                aspectJClass.setClassname(typeDeclaration.getName().toString());
            }
        }
        super.endVisit(node);
    }

    @Override
    public boolean visit(final ImportDeclaration node) {
        final int start = node.getStartPosition();
        final int end = node.getStartPosition() + node.getLength();
        final String importContent = source.substring(start, end);
        aspectJClass.addJavaClassImport(new AspectJPositionContent(start, end, importContent));
        return super.visit(node);
    }

    private void addAdvice(final AdviceDeclaration node) {
        for (final Object obj : node.thrownExceptions()) {
            aspectJClass.addUsedType(((SimpleName) obj).getFullyQualifiedName());
        }
        if (containsAnnotation(node)) {
            // TODO alle annotationen hinzufügen
            aspectJClass.addUsedType("Generated");
            final AspectJ6Advice advice = aspectJAdviceBuilder.createAdvice(node);
            aspectJClass.addAdvice(advice);
            setNextForPositionGeneratedBlock(advice);
        } else {
            setNextForPositionNotGeneratedBlock(node);
        }
    }

    private void setNextForPositionNotGeneratedBlock(final ASTNode node) {
        if (!isGeneratedBlockPositionSet) {
            final int position = node.getStartPosition() + node.getLength();
            if (aspectJClass.getNextBlockPositionToWrite() < position) {
                aspectJClass.setNextFreeBlockPosition(position);
            }
        }
    }

    private void setNextForPositionGeneratedBlock(final AspectJBlock block) {
        isGeneratedBlockPositionSet = true;
        if (aspectJClass.getNextBlockPositionToWrite() < block.getEndPosition()) {
            aspectJClass.setNextFreeBlockPosition(block.getEndPosition());
        }
    }

    private boolean containsAnnotation(final AdviceDeclaration node) {
        final int adviceStart = node.getStartPosition();
        final int pointcutStart = node.getPointcut().getStartPosition();
        final Matcher annotationMatcher = Pattern.compile(GENERATOR_ANNOTATION_REGEX).matcher(
                source.substring(adviceStart, pointcutStart));
        return annotationMatcher.find();
    }

    @Override
    public boolean visit(final BeforeAdviceDeclaration node) {
        addAdvice(node);
        return super.visit(node);
    }

    @Override
    public boolean visit(final AfterAdviceDeclaration node) {
        addAdvice(node);
        return super.visit(node);
    }

    @Override
    public boolean visit(final AroundAdviceDeclaration node) {
        addAdvice(node);
        return super.visit(node);
    }


    @Override
    public boolean visit(final InterTypeFieldDeclaration node) {
        if (containsAnnotation(node)) {
            final AspectJ6ITDField field = aspectJFieldBuilder.createField(node);
            aspectJClass.addITDField(field);
            setNextForPositionGeneratedBlock(field);
        } else {
            setNextForPositionNotGeneratedBlock(node);
        }
        return super.visit(node);
    }

    private boolean containsAnnotation(final BodyDeclaration node) {
        for (final Object obj : node.modifiers()) {
            if (obj instanceof Annotation) {
                final Annotation annotation = (Annotation) obj;
                if (annotation.getTypeName().getFullyQualifiedName().equals("Generated")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean visit(final InterTypeMethodDeclaration node) {
        for (final Object obj : node.thrownExceptions()) {
            aspectJClass.addUsedType(((SimpleName) obj).getFullyQualifiedName());
        }
        if (containsAnnotation(node)) {
            final int typeStart = node.getReturnType2().getStartPosition();
            if (typeStart != 0) {
                final AspectJ6ITDMethod method = aspectJMethodBuilder.createMethod(node, typeStart);

                aspectJClass.addITDMethod(method);
                setNextForPositionGeneratedBlock(method);
            } else {

                final AspectJ6ITDConstructor constructor = aspectJConstructorBuilder.createConstructor(node);
                aspectJClass.addITDConstructors(constructor);
                setNextForPositionGeneratedBlock(constructor);

            }

        } else {
            setNextForPositionNotGeneratedBlock(node);
        }
        return super.visit(node);
    }
}
