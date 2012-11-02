package de.hbrs.aspgen.generator.neo4j;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.api.generator.ConstructorForClassGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithConstructors;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;


public class Neo4JGenerator extends AbstractGeneratorBundle implements
            FieldForClassGenerator, ConstructorForClassGenerator, MethodForFieldGenerator {

    @Override
    public String getName() {
        return Neo4J.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
            final Map<String, String> properties) {
        fieldBuilder.addImport("org.neo4j.graphdb.Node");
        final FieldForClass fieldForClass = fieldBuilder.appendNewField("Node");
        fieldForClass.setContent("private Node node;");
    }

    @Override
    public void extendJavaClass(final ExtendClassWithConstructors builder,
            final Map<String, String> properties) {
        final ConstructorForClass constructorForClass = builder.appendNewConstructor("Neo4J Constructor");
        constructorForClass.addParameter("final Node node");
        constructorForClass.addLine("super();");
        constructorForClass.addLine("this.node = node;");
    }

    @Override
    public void extendJavaClass(final ExtendFieldWithMethods builder,
            final Map<String, String> properties) {
        final MethodForField methodForField = builder.appendNewMethod("Get Property");
        methodForField.setMethodDeclaration("public $fieldtype$ get$fieldname$()");
        methodForField.addLine("return ($fieldtype$) node.getProperty(\"$fieldname$\");");
    }


}
