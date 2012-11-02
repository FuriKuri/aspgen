package de.hbrs.aspgen.api.diff;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.merge.GeneratorData;

public interface AspectJDiff {

    AspectJClassDiff getAspectJClassDiff();

    GeneratorData getData();

    AspectJFieldDiff getAspectJFieldDiff(JavaField javaField);

    AspectJMethodDiff getAspectJMethodDiff(JavaMethod javaMethod);

    AspectJParameterDiff getAspectJParameterDiff(
            JavaParameter javaParameter, JavaMethod javaMethod);

}
