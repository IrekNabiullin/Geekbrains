package workgroup;

public class Main {
    public static void main(String[] args) throws Exception{
        Class c = String.class;
        String str = "java";
        Class c2 = str.getClass();

        Class.forName("java.lang.String");
        String.format("test");

        Class c3 = int.class;
        Class c4 = int[].class;
        Class c5 = void.class;

        Class cat = Cat.class;
        System.out.println(cat.getComponentType());
        System.out.println(cat.getModifiers());
    }
}
