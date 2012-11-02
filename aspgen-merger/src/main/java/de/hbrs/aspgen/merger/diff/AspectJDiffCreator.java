package de.hbrs.aspgen.merger.diff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.AspectJITDMethod;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.DiffCreator;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.merger.anno.AdviceDiff;
import de.hbrs.aspgen.merger.anno.ConstructorDiff;
import de.hbrs.aspgen.merger.anno.DeclareDiff;
import de.hbrs.aspgen.merger.anno.FieldDiff;
import de.hbrs.aspgen.merger.anno.MethodDiff;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJDiffCreator implements DiffCreator {

    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final JavaParser javaParser;

    private final DiffManager methodDiffManager;
    private final DiffManager constructorDiffManager;
    private final DiffManager adviceDiffManager;
    private final DiffManager fieldDiffManager;
    private final DiffManager declareDiffManager;

    private final AspectJDiffFiller aspectJDiffFiller;

    @Inject
    public AspectJDiffCreator(
            final AspectJParser ajParser,
            final JavaParser javaParser,
            final GeneratorManager generatorManager,
            final JavaFactory javaFactory,
            @MethodDiff final DiffManager methodDiffManager,
            @ConstructorDiff final DiffManager constructorDiffManager,
            @AdviceDiff final DiffManager adviceDiffManager,
            @FieldDiff final DiffManager fieldDiffManager,
            @DeclareDiff final DiffManager declareDiffManager) {
        this.ajParser = ajParser;
        this.javaParser = javaParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;

        this.methodDiffManager = methodDiffManager;
        this.constructorDiffManager = constructorDiffManager;
        this.adviceDiffManager = adviceDiffManager;
        this.fieldDiffManager = fieldDiffManager;
        this.declareDiffManager = declareDiffManager;
        this.aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public AspectJDiffImpl createDiff(final String aspectJContent, final String javaContent) {
        final AspectJDiffImpl diff = new AspectJDiffImpl();
        final AspectJUnit aspectJUnit = ajParser.parse(aspectJContent);
        final GeneratorData data = createData(aspectJUnit);
        diff.setData(data);
        final JavaClass javaUnit = javaParser.parse(javaContent);
        updateDeletedBlocks(aspectJUnit, javaUnit, diff, data);
        methodDiffManager.updateDiff(aspectJUnit, aspectJContent, diff, data);
        constructorDiffManager.updateDiff(aspectJUnit, aspectJContent, diff, data);
        adviceDiffManager.updateDiff(aspectJUnit, aspectJContent, diff, data);
        fieldDiffManager.updateDiff(aspectJUnit, aspectJContent, diff, data);
        declareDiffManager.updateDiff(aspectJUnit, aspectJContent, diff, data);
        return diff;
    }

    private void updateDeletedBlocks(final AspectJUnit aspectJUnit,
            final JavaClass javaUnit, final AspectJDiffImpl diff, final GeneratorData data) {
        javaUnit.removeAnnotationAttribute("deleted");
        final String orginalContent = generatorManager.generateContentForGenerator(javaUnit, data.getAnnotation());
        final AspectJUnit orginalAspectJUnit = ajParser.parse(orginalContent);

        for (final AspectJBlock blockInOrginalUnit : orginalAspectJUnit.getAllBlocks()) {
            boolean blockIsMissingInNewUnit = true;
            final String oldAnnotationName = blockInOrginalUnit.getAnnotationName();
            final String oldAnnotationData = blockInOrginalUnit.getAnnotationData();

            for (final AspectJBlock blockInNewUnit : aspectJUnit.getAllBlocks()) {
                final String newAnnotationName = blockInNewUnit.getAnnotationName();
                final String newAnnotationData = blockInNewUnit.getAnnotationData();

                if (oldAnnotationData.equals(newAnnotationData) && oldAnnotationName.equals(newAnnotationName)) {
                    blockIsMissingInNewUnit = false;
                }
            }

            if (blockIsMissingInNewUnit) {
                if (blockInOrginalUnit instanceof AspectJITDMethod) {
                    final AspectJITDMethod method = (AspectJITDMethod) blockInOrginalUnit;
                    if (method.getAnnotationId().equals("{newIdClass}") || method.getAnnotationId().equals("1")) {
                        aspectJDiffFiller.addNewClassDiff(diff, method);
                        diff.getAspectJClassDiff().getAnnotationData().addDeleted(method.getAnnotationName());
                    } else {
                        final JavaField field = javaFactory.buildClassWithField(method.getAnnotationData());
                        aspectJDiffFiller.addNewFieldDiff(diff, method, field);
                        diff.getAspectJFieldDiff(field).getAnnotationData().addDeleted(method.getAnnotationName());
                    }
                }

                if (blockInOrginalUnit instanceof AspectJITDConstructor && !(blockInOrginalUnit instanceof AspectJITDMethod) && !(blockInOrginalUnit instanceof AspectJITDField)) {
                    final AspectJITDConstructor cons = (AspectJITDConstructor) blockInOrginalUnit;
                    aspectJDiffFiller.addNewClassDiff(diff, cons);
                    diff.getAspectJClassDiff().getAnnotationData().addDeleted(cons.getAnnotationName());
                }

                if (blockInOrginalUnit instanceof AspectJITDField) {
                    final AspectJITDField field = (AspectJITDField) blockInOrginalUnit;
                    if (field.getAnnotationId().equals("{newIdClass}") || field.getAnnotationId().equals("1")) {
                        aspectJDiffFiller.addNewClassDiff(diff, field);
                        diff.getAspectJClassDiff().getAnnotationData().addDeleted(field.getAnnotationName());
                    } else {
                        final JavaMethod javaMethod = javaFactory.buildMethod(field.getAnnotationData());
                        aspectJDiffFiller.addNewMethodDiff(diff, field, javaMethod);
                        diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addDeleted(field.getAnnotationName());
                    }

                }

                if (blockInOrginalUnit instanceof AspectJDeclare) {
                    final AspectJDeclare declare = (AspectJDeclare) blockInOrginalUnit;

                    final JavaMethod javaMethod = javaFactory.buildMethod(declare.getAnnotationData());
                    aspectJDiffFiller.addNewMethodDiff(diff, declare, javaMethod);
                    diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addDeleted(declare.getAnnotationName());
                }

                if (blockInOrginalUnit instanceof AspectJAdvice) {
                    final AspectJAdvice advice = (AspectJAdvice) blockInOrginalUnit;
                    final Pattern p = Pattern.compile("\\w+:\\w+;\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
                    final Matcher m = p.matcher(advice.getAnnotationData());

                    final Pattern p2 = Pattern.compile("\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
                    final Matcher m2 = p2.matcher(advice.getAnnotationData());

                    final Pattern p3 = Pattern.compile("\\w+:\\w+;.*");
                    final Matcher m3 = p3.matcher(advice.getAnnotationData());

                    if (m.find()) {
                        final int indexOfMethodDataBegin = advice.getAnnotationData().indexOf(";");
                        final String methodData = advice.getAnnotationData().substring(indexOfMethodDataBegin + 1);
                        final JavaMethod javaMethod = javaFactory.buildMethod(methodData);
                        final JavaParameter javaParameter = javaFactory.buildParameter(advice.getAnnotationData());
                        aspectJDiffFiller.addNewParameterDiff(diff, advice, javaMethod, javaParameter);
                        diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().addDeleted(advice.getAnnotationName());
                    } else if (m2.find()) {
                        final JavaMethod javaMethod = javaFactory.buildMethod(advice.getAnnotationData());
                        aspectJDiffFiller.addNewMethodDiff(diff, advice, javaMethod);
                        diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addDeleted(advice.getAnnotationName());
                    } else if (m3.find()) {
                        final JavaField field = javaFactory.buildClassWithField(advice.getAnnotationData());
                        aspectJDiffFiller.addNewFieldDiff(diff, advice, field);
                        diff.getAspectJFieldDiff(field).getAnnotationData().addDeleted(advice.getAnnotationName());
                    } else {
                        throw new RuntimeException("Error while match to advice type");
                    }
                }
            }
        }

    }

    private GeneratorData createData(final AspectJUnit aspectJUnit) {
        final String javaClassName = aspectJUnit.getClassname().split("_")[0];
        final String annotationName = aspectJUnit.getClassname().split("_")[1];
        final String fullGeneratorname = generatorManager.getFullGeneratorName(annotationName);
        return new GeneratorDataImpl(fullGeneratorname, javaClassName);
    }
}
