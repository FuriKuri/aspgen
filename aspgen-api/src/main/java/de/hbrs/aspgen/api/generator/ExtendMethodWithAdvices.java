package de.hbrs.aspgen.api.generator;

public interface ExtendMethodWithAdvices extends ExtendClass {
    AdviceForMethod appendNewBeforeAdvice(String name);
    AdviceForMethod appendNewAroundAdvice(String name);
    AdviceForMethod appendNewAfterAdvice(String name);
}
