package de.hbrs.aspgen.generator.dao;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;

public class LoadableDaoGenerator implements MethodForClassGenerator {

    @Override
    public String getName() {
        return LoadableDao.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
            final Map<String, String> properties) {
        final String className = properties.get("entity").replace(".class", "");
        final MethodForClass methodForClass = methodBuilder.appendNewMethod("Save");
        methodForClass.setMethodDeclaration("public void select" + className + "ById(String id)");
        methodForClass.addLine("getHibernateTemplate().get(" + className + ".class, id);");

        methodForClass.addAnnotation("@Transactional(readOnly = true)");
    }

}
