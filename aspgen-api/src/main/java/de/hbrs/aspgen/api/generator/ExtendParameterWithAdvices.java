package de.hbrs.aspgen.api.generator;

public interface ExtendParameterWithAdvices extends ExtendClass {
    AdviceForParameter appendNewBeforeAdviceForParameter(String name);
    AdviceForParameter appendNewAroundAdviceForParameter(String name);
    AdviceForParameter appendNewAfterAdviceForParameter(String name);
}
