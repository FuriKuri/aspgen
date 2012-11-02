package de.hbrs.aspgen.generator.builder.extender;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.api.generator.Generator;

public class GeneratorComparator implements Comparator<Generator> {

    public static final GeneratorComparator INSTANCE = new GeneratorComparator();

    private GeneratorComparator() {
        generatorOrdering.put(AdviceForParameterGenerator.class, 1);
    }

    private final Map<Class<? extends Generator>, Integer> generatorOrdering = new HashMap<>();

    @Override
    public int compare(final Generator o1, final Generator o2) {
        return getOrder(o1) - getOrder(o2);
    }

    private int getOrder(final Generator o1) {
        if (generatorOrdering.containsKey(o1)) {
            return generatorOrdering.get(o1);
        } else {
            return Integer.MAX_VALUE;
        }
    }

}
