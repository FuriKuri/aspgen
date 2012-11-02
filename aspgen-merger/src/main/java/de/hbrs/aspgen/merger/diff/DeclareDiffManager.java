package de.hbrs.aspgen.merger.diff;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class DeclareDiffManager extends AbstracteDiffManager {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final AspectJDiffFiller aspectJDiffFiller;

    @Inject
    public DeclareDiffManager(final AspectJParser ajParser, final GeneratorManager generatorManager,
            final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
        aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public void updateDiff(final AspectJUnit aspectJUnit,
            final String actualContent, final AspectJDiffImpl diff, final GeneratorData data) {
        for (final AspectJDeclare declare : aspectJUnit.getDeclares()) {
            final JavaClass javaClass = javaFactory.buildJavaClassFromDeclareForMethod(declare, data);
            final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());

            final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
            final AspectJBlock orginalAdvice = getBlockFromUnit(declare, orginalUnit);

            final String actualFieldBlock = actualContent.substring(declare.getStartPosition(), declare.getEndPosition())
                    .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
            final String orginalFieldBlock = orginalContent.substring(orginalAdvice.getStartPosition(), orginalAdvice.getEndPosition())
                    .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

            if (!actualFieldBlock.equals(orginalFieldBlock)) {
                final JavaMethod javaMethod = javaClass.getMethods().get(0);
                aspectJDiffFiller.addNewMethodDiff(diff, declare, javaMethod);
                diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addModified(declare.getAnnotationName());
            }
        }
    }
}
