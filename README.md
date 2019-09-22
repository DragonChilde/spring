# Spring #

**搭建Spring运行时的基础环境**

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0"
	         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	    <parent>
	        <artifactId>Spring</artifactId>
	        <groupId>Spring</groupId>
	        <version>1.0-SNAPSHOT</version>
	    </parent>
	    <modelVersion>4.0.0</modelVersion>
	
	    <artifactId>base-spring</artifactId>
	
	    <dependencies>
	        <dependency>
	            <groupId>org.springframework</groupId>
	            <artifactId>spring-context</artifactId>
	            <version>4.3.18.RELEASE</version>
	        </dependency>
	        <dependency>
	            <groupId>org.springframework</groupId>
	            <artifactId>spring-core</artifactId>
	            <version>4.3.18.RELEASE</version>
	        </dependency>
	        <dependency>
	            <groupId>org.springframework</groupId>
	            <artifactId>spring-beans</artifactId>
	            <version>4.3.18.RELEASE</version>
	        </dependency>
	        <dependency>
	            <groupId>org.springframework</groupId>
	            <artifactId>spring-expression</artifactId>
	            <version>4.3.18.RELEASE</version>
	        </dependency>
	
	        <dependency>
	            <groupId>commons-logging</groupId>
	            <artifactId>commons-logging</artifactId>
	            <version>1.2</version>
	        </dependency>
	
	    </dependencies>
	</project>

创建一个Person类

	public class Person {
	
	    private String name;		//成员变量
	
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

创建一个spring配置文件名为applicationContext.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	
		<!-- 配置bean 
			  配置方式:  基于xml的方式 ,使用的全类名的方式.
			 <bean>: 受Spring管理的一个javaBean对象. 
			 	id:  <bean>的唯一标识. 在整个IOC容器中唯一不重复. 
			 	class: 指定javaBean的全类名. 目的是通过反射创建对象。 
			 		   Class cls = Class.forName("com.atguigu.spring.helloWorld.Person");
			 		   Object obj = cls.newInstance();   必须提供无参数构造器. 
			 <property>: 给对象的属性赋值
			  	name: 指定属性名,指定setXXX风格的属性名. (name找是的setXXX的方法,不是类的成员变量.注set后面才是属性,类的变量是成员变量)
			  	value:指定属性值 	
		-->
	   	<bean id="person" class="bean.Person">
	        <property name="name" value="李四"/>
	    </bean>
	</beans>

创建Main启动类

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


#IOC容器和Bean的配置#

**IOC和DI**

**IOC(Inversion of Control)：反转控制**

在应用程序中的组件需要获取资源时，传统的方式是组件主动的从容器中获取所需要的资源，在这样的模式下开发人员往往需要知道在具体容器中特定资源的获取方式，增加了学习成本，同时降低了开发效率。
反转控制的思想完全颠覆了应用程序组件获取资源的传统方式：反转了资源的获取方向——改由容器主动的将资源推送给需要的组件，开发人员不需要知道容器是如何创建资源对象的，只需要提供接收资源的方式即可，极大的降低了学习成本，提高了开发的效率。这种行为也称为查找的被动形式。


**DI(Dependency Injection)：依赖注入**

IOC的另一种表述方式：即组件以一些预先定义好的方式(例如：setter 方法)接受来自于容器的资源注入。相对于IOC而言，这种表述更直接。

IOC 描述的是一种思想，而DI 是对IOC思想的具体实现.

**IOC容器在Spring中的实现**


- 在通过IOC容器读取Bean的实例之前，需要先将IOC容器本身实例化。
- Spring提供了IOC容器的两种实现方式
	1. BeanFactory：IOC容器的基本实现，是Spring内部的基础设施，是面向Spring本身的，不是提供给开发人员使用的。
	2. ApplicationContext：BeanFactory的子接口，提供了更多高级特性。面向Spring的使用者，几乎所有场合都使用ApplicationContext而不是底层的BeanFactor

![](https://images2018.cnblogs.com/blog/1362278/201807/1362278-20180709153041698-778938050.png)

-  **ApplicationContext的主要实现类**

	1. ClassPathXmlApplicationContext：对应类路径下的XML格式的配置文件
	2. FileSystemXmlApplicationContext：对应文件系统中的XML格式的配置文件
	3. 在初始化时就创建单例的bean，也可以通过配置的方式指定创建的Bean是多实例的。

-  **ConfigurableApplicationContext**
	1. 是ApplicationContext的子接口，包含一些扩展方法
	2. refresh()和close()让ApplicationContext具有启动、关闭和刷新上下文的能力。

- **WebApplicationContext**

	专门为WEB应用而准备的，它允许从相对于WEB根目录的路径中完成初始化工