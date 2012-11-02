package de.hbrs.aspgen.generator.process;

import org.junit.Before;
import org.mockito.Mockito;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;
import de.hbrs.aspgen.testhelper.DummyLogService;

public class AspectJGeneratorManagerTest {
    AspectJGeneratorManager generatorManager;
    GeneratorRegistry generatorRegistry;
    JavaClass javaClass;

    @Before
    public void init() {
        generatorRegistry = Mockito.mock(GeneratorRegistry.class);
        javaClass = Mockito.mock(JavaClass.class);
        generatorManager = new AspectJGeneratorManager(generatorRegistry, new DummyLogService());
    }

//
//    @Test
//    public void getRegistredAnnotationsInJavaClassWithSimpleImports() {
//        final List<String> imports = Arrays.asList("de.hbrs.ToString", "de.hbrs.Equals", "de.hbrs.HashCode");
//        final Set<String> registredAnnotations = new HashSet<String>(Arrays.asList("de.hbrs.ToString", "de.hbrs.HashCode"));
//
//
//        when(javaClass.getImports()).thenReturn(imports);
//        when(generatorRegistry.getRegistredGeneratorNames()).thenReturn(registredAnnotations);
//
//        final Set<String> result = generatorManager.getRegisteredAnnotationsInJavaClass(javaClass);
//        assertEquals(2, result.size());
//        final Iterator<String> resultAsIterator = result.iterator();
//        assertEquals("de.hbrs.HashCode", resultAsIterator.next());
//        assertEquals("de.hbrs.ToString", resultAsIterator.next());
//
//    }
}
