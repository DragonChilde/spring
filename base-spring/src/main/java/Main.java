import bean.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lee
 * @create 2019-09-22 18:40
 */
public class Main {
    public static void main(String[] args) {
        // 获取到Person对象.

        //1. 创建Spring的IOC容器对象
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2. 获取Person对象
        Person person = (Person)ctx.getBean("person");

        person.sayHello();
    }
}
