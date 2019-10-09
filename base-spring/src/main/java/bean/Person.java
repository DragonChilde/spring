package bean;

/**
 * @author Lee
 * @create 2019-09-22 18:26
 */
public class Person {

    private String name;    //成员变量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }


    public void sayHello(){
        System.out.println("My name is "+ name);
    }
}
