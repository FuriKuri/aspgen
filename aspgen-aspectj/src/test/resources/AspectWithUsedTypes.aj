public privileged aspect FileAspect extends ClassA implements ClassB {
    @Generated2(id = 12, me = 12)
    public void method2(@Check Double d) throws Eab2 {
        assertEquals(1, 1);
        BigDecimal bd = new BigDecimal();
        System.out.println("Output");
        throw new Asd();
    }

    @Generated2(12345)
    public void OneKlasse.method(Double d) throws Eab {
        assertEquals(1, 1);
        BigDecimal bd = new BigDecimal();
        System.out.println("Output");
        int result = HashCodeUtil.SEED;
    }
    
    @Generated("mappingId")
    before(String value) throws IOException : execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) {
        System.out.println("Execute doMethod");
    }
}