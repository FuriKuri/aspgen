package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;

public class JavaClassExtenderWithPackageAndImportsTest {

    @Test
    public void createAspectWithImportsAndPackageFromJavaClass() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new MethodGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "package de.hbrs.mypackage;\n\n"
            + "import de.hbrs.aspgen.annotation.Generated;\n\n"
            + "import de.hbrs.ToString;\n"
            + "import de.hbrs.JavaBean;\n"
            + "import static de.hbrs.Assert.equals;\n"
            + "import static de.hbrs.Assert.same;\n\n"
            + "public privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";\")\n    public void Person.print() {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClass(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);
        java6Class.setPackageName("de.hbrs.mypackage");

        java6Class.addImport("de.hbrs.ToString");
        java6Class.addImport("de.hbrs.JavaBean");

        java6Class.addStaticImport("de.hbrs.Assert.equals");
        java6Class.addStaticImport("de.hbrs.Assert.same");

        return java6Class;
    }

    private static class MethodGen implements MethodForClassGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
            methodForClass.setMethodDeclaration("public void print()");
            methodForClass.addLine("System.out.println();");
        }
    }

    @Test
    public void createAspectWithImportsAndPackageFromGen() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new MethodGenWithIndImports());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "package de.hbrs.mypackage;\n\n"
            + "import de.hbrs.aspgen.annotation.Generated;\n\n"
            + "import de.hbrs.ToString;\n"
            + "import de.hbrs.JavaBean;\n"
            + "import static de.hbrs.Assert.equals;\n"
            + "import static de.hbrs.Assert.same;\n\n"
            + "import de.hbrs.GenImport2;\n"
            + "import de.hbrs.GenImport;\n\n"
            + "public privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";\")\n    public void Person.print() {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private static class MethodGenWithIndImports implements MethodForClassGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
            methodForClass.setMethodDeclaration("public void print()");
            methodForClass.addLine("System.out.println();");

            methodBuilder.addImport("de.hbrs.GenImport");
            methodBuilder.addImport("de.hbrs.GenImport2");
        }
    }

    @Test
    public void createAspectWithStaticImportsAndPackageFromGen() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new MethodGenWithStaticImports());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "package de.hbrs.mypackage;\n\n"
            + "import de.hbrs.aspgen.annotation.Generated;\n\n"
            + "import de.hbrs.ToString;\n"
            + "import de.hbrs.JavaBean;\n"
            + "import static de.hbrs.Assert.equals;\n"
            + "import static de.hbrs.Assert.same;\n\n"
            + "import static de.hbrs.GenImport;\n"
            + "import static de.hbrs.GenImport2;\n\n"
            + "public privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";\")\n    public void Person.print() {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private static class MethodGenWithStaticImports implements MethodForClassGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
            methodForClass.setMethodDeclaration("public void print()");
            methodForClass.addLine("System.out.println();");

            methodBuilder.addStaticImport("de.hbrs.GenImport");
            methodBuilder.addStaticImport("de.hbrs.GenImport2");
        }
    }
}
