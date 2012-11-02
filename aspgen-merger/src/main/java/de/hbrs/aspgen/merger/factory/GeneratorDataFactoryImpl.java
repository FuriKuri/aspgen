package de.hbrs.aspgen.merger.factory;

import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.merge.GeneratorDataFactory;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class GeneratorDataFactoryImpl implements GeneratorDataFactory {
    @Override
    public GeneratorData createGeneratorData(final String annotation, final String classname) {
        return new GeneratorDataImpl(annotation, classname);
    }
}
