package de.hbrs.aspgen.ajparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.org.eclipse.jdt.core.dom.AdviceDeclaration;

import de.hbrs.aspgen.ajparser.type.AspectJ6Advice;
import de.hbrs.aspgen.ajparser.type.AspectJPositionContent;

public class AspectJAdviceBuilder {
    private final String source;
    private static final String GENERATOR_ANNOTATION_REGEX = "@Generated\\([^)]*\\)";

    public AspectJAdviceBuilder(final String source) {
        this.source = source;
    }
    public AspectJ6Advice createAdvice(final AdviceDeclaration node) {
        final AspectJ6Advice advice = new AspectJ6Advice();
        final int start = node.getStartPosition() + calcRealStart(source.substring(node.getStartPosition(), node.getStartPosition() + node.getLength()));
        final int javaDocstart = start;
        final int pointcutStart = node.getPointcut().getStartPosition();
        final int blockStart = node.getBody().getStartPosition();
        final int end = node.getStartPosition() + node.getLength();
        final String javaDocAnnotaionAndAdviceDeclration = source.substring(javaDocstart, pointcutStart);
        final Matcher annotationMatcher = Pattern.compile(GENERATOR_ANNOTATION_REGEX).matcher(javaDocAnnotaionAndAdviceDeclration);
        final int annotationStart;
        final int adviceHeadStart;
        if (annotationMatcher.find()) {
            annotationStart = annotationMatcher.start() + javaDocstart;
            adviceHeadStart = annotationMatcher.end() + javaDocstart;
        } else {
            throw new RuntimeException();
        }

        advice.setStartPosition(start);
        advice.setEndPosition(end);

        setJavaDoc(advice, javaDocstart, annotationStart);
        setAnnotation(advice, annotationStart, adviceHeadStart);
        setAdvice(advice, pointcutStart, adviceHeadStart);
        setPointcut(advice, pointcutStart, blockStart);
        setBlock(advice, blockStart, end);
        return advice;
    }

    private int calcRealStart(final String block) {
        final String regex = "(/\\*\\*.+?\\*/[\\s\\t\\n\\r]*)?(?<!//)@Generated";
        final Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(block);
        if (matcher.find()) {
            return matcher.start();
        } else {
            throw new RuntimeException("Error while try to find start position");
        }
    }
    private void setJavaDoc(final AspectJ6Advice advice,
            final int javaDocstart, final int annotationStart) {
        if (javaDocstart == annotationStart) {
            advice.setJavaDoc(AspectJPositionContent.EMTPY_CONTENT);
        } else {
            final AspectJPositionContent content = new AspectJPositionContent(javaDocstart, annotationStart,
                    source.substring(javaDocstart, annotationStart));
            advice.setJavaDoc(content);
        }
    }

    private void setBlock(final AspectJ6Advice advice, final int blockStart,
            final int end) {
        final String blockContent = source.substring(blockStart, end);
        advice.setBlock(new AspectJPositionContent(blockStart, end, blockContent));
    }

    private void setPointcut(final AspectJ6Advice advice,
            final int pointcutStart, final int blockStart) {
        final String pointcut = source.substring(pointcutStart, blockStart);
        advice.setPointcut(new AspectJPositionContent(pointcutStart, blockStart, pointcut));
    }

    private void setAdvice(final AspectJ6Advice advice,
            final int pointcutStart, final int adviceHeadStart) {
        final String adviceHead = source.substring(adviceHeadStart, pointcutStart);
        advice.setAdviceHead(new AspectJPositionContent(adviceHeadStart, pointcutStart, adviceHead));
    }

    private void setAnnotation(final AspectJ6Advice advice,
            final int annotationStart, final int adviceHeadStart) {
        final String annotation = source.substring(annotationStart, adviceHeadStart);
        advice.setAnnotations(new AspectJPositionContent(annotationStart, adviceHeadStart, annotation));
    }
}
