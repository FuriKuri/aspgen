public class JavaClassWithMethods {
    @MethodAnno1(1234)
    public String getName() {
        assertEquals("", "");
        assertNull(null);
        return name;
    }

    @MethodAnno1
    @MethodAnno2
    private void setId(final int id, String newName) {
        this.id = id;
        this.name = newName;
    }
    
    protected static int m2(int a, @NotNull @Size(value=1) double b) {
        return 1;
    }
    
    void m3() {
        this.id = id;
        this.name = newName;
    }
}