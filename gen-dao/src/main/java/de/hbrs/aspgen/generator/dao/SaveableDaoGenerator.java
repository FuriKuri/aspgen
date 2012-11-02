package de.hbrs.aspgen.generator.dao;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;

public class SaveableDaoGenerator implements MethodForClassGenerator {

    @Override
    public String getName() {
        return SaveableDao.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
            final Map<String, String> properties) {
        final String className = properties.get("entity").replace(".class", "");
        final String firstCharLowerCase = className.substring(0, 1).toLowerCase() + className.substring(1);
        final MethodForClass methodForClass = methodBuilder.appendNewMethod("Save");
        methodForClass.setMethodDeclaration("public void save" + className + "(" + className + " " + firstCharLowerCase + ")");
        methodForClass.addLine("getHibernateTemplate().saveOrUpdate(" + firstCharLowerCase + ");");

        methodForClass.addAnnotation("@Transactional(readOnly = false)");
    }

}
