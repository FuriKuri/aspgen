
@JavaBean
@ToString(true)
@NotNull(exclude=true, someClass=String.class, stringValue="value")
public class ClassWithAnnotations {
    @Anno1("defaultValue")
    @SingleAnno
    public String name;
    
    @MethodAnno1
    @MethodAnno2
    private void setId(final int id, String newName) {
        this.id = id;
        this.name = newName;
    }
    
    protected static int m2(int a, @NotNull @Size(value=1) double b) {
        return 1;
    }
}