package de.hbrs.aspgen.ajparser.type;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.AspectJITDMethod;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJ6Class implements AspectJUnit {
    private final List<AspectJITDConstructor> itdConstructors = new LinkedList<>();
    private final List<AspectJITDMethod> itdMethods = new LinkedList<>();
    private final List<AspectJITDField> itdFields = new LinkedList<>();
    private final List<AspectJAdvice> advices = new LinkedList<>();
    private final List<AspectJDeclare> declares = new LinkedList<>();
    private final List<PositionContent> javaClassImports = new LinkedList<>();
    private String classname;
    private final Set<String> usedTypesAndMethods = new HashSet<>();
    private PositionContent packageName;
    private int nextFreeBlockPosition = 0;

    @Override
    public List<AspectJITDMethod> getItdMethods() {
        return itdMethods;
    }

    public void addITDMethod(final AspectJITDMethod method) {
        itdMethods.add(method);
    }

    @Override
    public List<AspectJDeclare> getDeclares() {
        return declares;
    }

    public void addDeclares(final AspectJDeclare declare) {
        declares.add(declare);
    }


    @Override
    public List<AspectJITDField> getItdFields() {
        return itdFields;
    }

    public void addITDField(final AspectJITDField field) {
        itdFields.add(field);
    }

    @Override
    public List<AspectJAdvice> getAdvices() {
        return advices;
    }

    public void addAdvice(final AspectJAdvice advice) {
        advices.add(advice);
    }

    @Override
    public List<PositionContent> getImports() {
        return javaClassImports;
    }

    public void addJavaClassImport(final PositionContent importName) {
        javaClassImports.add(importName);
    }

    @Override
    public List<AspectJITDConstructor> getItdConstructors() {
        return itdConstructors;
    }

    public void addITDConstructors(final AspectJITDConstructor constructor) {
        itdConstructors.add(constructor);
    }

    @Override
    public int getNextBlockPositionToWrite() {
        return nextFreeBlockPosition;
    }

    @Override
    public List<AspectJBlock> getAllBlocks() {
        final List<AspectJBlock> blocks = new LinkedList<>();
        blocks.addAll(itdConstructors);
        blocks.addAll(itdFields);
        blocks.addAll(itdMethods);
        blocks.addAll(advices);
        blocks.addAll(declares);
        return blocks;
    }

    @Override
    public String getClassname() {
        return classname;
    }

    public void setClassname(final String classname) {
        this.classname = classname;
    }

    @Override
    public Set<String> getUsedTypesAndMethods() {
        return usedTypesAndMethods;
    }

    public void addUsedMethod(final String method) {
        usedTypesAndMethods.add(method);
    }

    public void addUsedType(final String type) {
        usedTypesAndMethods.add(type);
    }

    @Override
    public PositionContent getPackageName() {
        return packageName;
    }

    public void setPackageName(final PositionContent packageName) {
        this.packageName = packageName;
    }

    public void setNextFreeBlockPosition(final int nextFreeBlockPosition) {
        this.nextFreeBlockPosition = nextFreeBlockPosition;
    }
}
