import java.util.LinkedList;
import java.util.List;

public class SimpleJavaClass {
    private static int id;
    @Anno1("defaultValue")
    @SingleAnno
    public String name;
    @Anno2(value1=1, value2=Second.class)
    protected List<String> list, list2 = new LinkedList<String>();
}