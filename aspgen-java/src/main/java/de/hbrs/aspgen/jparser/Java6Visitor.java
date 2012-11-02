package de.hbrs.aspgen.jparser;
import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.ASTVisitor;
import org.aspectj.org.eclipse.jdt.core.dom.CompilationUnit;
import org.aspectj.org.eclipse.jdt.core.dom.FieldDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.ImportDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.MethodDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.PackageDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.TypeDeclaration;
import org.aspectj.org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;


public class Java6Visitor extends ASTVisitor {

    private final Java6Class result;
    private final String source;
    private final JavaFieldBuilder javaFieldBuilder;
    private final JavaMethodBuilder javaMethodBuilder;
    private final JavaAnnotationBuilder javaAnnotationBuilder;

    public Java6Visitor(final Java6Class result, final String source) {
        this.result = result;
        this.source = source;
        javaFieldBuilder = new JavaFieldBuilder();
        javaMethodBuilder = new JavaMethodBuilder();
        javaAnnotationBuilder = new JavaAnnotationBuilder();
    }

    @Override
    public boolean visit(final CompilationUnit node) {
        final List<?> types = node.types();
        for (final Object object : types) {
            if (object instanceof TypeDeclaration) {
                final TypeDeclaration typeDeclaration = (TypeDeclaration) object;
                result.setClassName(typeDeclaration.getName().getFullyQualifiedName());
                result.setIsInterface(typeDeclaration.isInterface());
                result.setStartPosition(typeDeclaration.getStartPosition());
            }
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(final FieldDeclaration node) {

        final List<?> fragments = node.fragments();
        for (final Object object : fragments) {
            final Java6Field field = javaFieldBuilder.createField(node, (VariableDeclarationFragment) object, source);
            result.addField(field);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(final ImportDeclaration node) {
        final int start = node.getStartPosition();
        final int end = start + node.getLength();
        final String content = source.substring(start, end);
        String name;
        if (node.isOnDemand()) {
            name = node.getName().getFullyQualifiedName() + ".*";
        } else {
            name = node.getName().getFullyQualifiedName();
        }
        if (content.contains(" static ")) {
            result.addStaticImport(name);
        } else {
            result.addImport(name);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
        final Java6Method method = javaMethodBuilder.createMethod(node, source);
        result.addMethod(method);
        return super.visit(node);
    }

    @Override
    public boolean visit(final PackageDeclaration node) {
        result.setPackageName(node.getName().getFullyQualifiedName());
        return super.visit(node);
    }

    @Override
    public boolean visit(final TypeDeclaration node) {
        for (final Java6Annotation java6Annotation : javaAnnotationBuilder.getAnnotations(node.modifiers(), source)) {
            result.addAnnotation(java6Annotation);
        }
        return super.visit(node);
    }

}
