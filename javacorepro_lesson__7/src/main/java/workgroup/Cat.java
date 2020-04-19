package workgroup;

import java.util.Objects;

public class Cat {
    String name;
    private String color;
    public int age;
    public Cat(String name, String color, int age){
        this.name = name;
        this.color = color;
        this.age = age;
    }
    public Cat(){
    }
    public Cat(String name){
        this.name = name;
    }
    public static void meow(){
        System.out.println("Meow!");
    }
    @MyAnno()
    public void info(){
        System.out.println("Cat info " + this.name);
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return age == cat.age && Objects.equals(name, cat.name) && Objects.equals(color, cat.color);
    }
    @Override
    public int hashCode(){
        return 1;
    }
}
