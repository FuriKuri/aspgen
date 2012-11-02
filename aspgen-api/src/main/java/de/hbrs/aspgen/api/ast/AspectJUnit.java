package de.hbrs.aspgen.api.ast;

import java.util.List;
import java.util.Set;


public interface AspectJUnit {
    List<AspectJITDConstructor> getItdConstructors();
    List<AspectJITDMethod> getItdMethods();
    List<AspectJITDField> getItdFields();
    List<AspectJAdvice> getAdvices();
    List<AspectJBlock> getAllBlocks();
    List<PositionContent> getImports();
    int getNextBlockPositionToWrite();
    String getClassname();
    Set<String> getUsedTypesAndMethods();
    PositionContent getPackageName();
    List<AspectJDeclare> getDeclares();
}
