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


**通过类型获取bean**

1. 从IOC容器中获取bean时，除了通过id值获取，还可以通过bean的类型获取。但如果同一个类型的bean在XML文件中配置了多个，则获取时会抛出异常，所以同一个类型的bean在容器中必须是唯一的

		Person person = ctx.getBean(Person.class);

2. 或者可以使用另外一个重载的方法，同时指定bean的id值和类型

		Person person = ctx.getBean("person", Person.class);

**注意:getBean是由接口BeanFactory定义的方法**

Main类

	public class Main {
	    public static void main(String[] args) {
	        // 获取到Person对象.
	
	        //1. 创建Spring的IOC容器对象
	        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	        //2. 获取Person对象
	        //1.根据bean id在applicationContext.xml文件里查找，类型需要进行转换
	        //Person person = (Person)ctx.getBean("person");
	        /*2.根据class类型进行查找，如果同时定义了多个相同class类型,会报expected single matching bean but found 2: person,person2异常*/
	        //Person person = ctx.getBean(Person.class);
	        /*同时指定bean id和class类型，不需要进行转换*/
	        Person person = ctx.getBean("person", Person.class);
	
	        person.sayHello();
	    }
	}

applicationContext.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	
	
	    <bean id="person" class="bean.Person">
	        <property name="name" value="李四"/>
	    </bean>
	
	    <bean id="person2" class="bean.Person">
	    <property name="name" value="李四"/>
	    </bean>
	</beans>

**给bean的属性赋值**

依赖注入的方式

1. 通过bean的setXxx()方法赋值

		//spring-di.xml
	
		<!-- DI依赖注入的方式: set方法注入 -->
		<!--这里通过Car类的setxxx()进行赋值,如果没有定义，会报异常--->
		  <bean id="car" class="com.spring.di.bean.Car">
	        <property name="brand" value="丰田"/>		//setBrand(String brand)
	        <property name="crop" value="广汽"/>			//setCrop(String crop)
	        <property name="price" value="200000"/>		//setPrice(Double price)
	    </bean>

2. 通过bean的构造器赋值(在建立构造函数赋值时,最好建一个空构选器,以免异常)

	1. Spring自动匹配合适的构造器

			   <bean id="car2" class="com.spring.di.bean.Car">
			        <constructor-arg value="宝马"/>
			        <constructor-arg value="华晨"/>
			        <constructor-arg value="450000"/>
		    	</bean>

				//Car类构造器
				public Car(String brand, String crop, Double price) {
			        this.brand = brand;
			        this.crop = crop;
			        this.price = price;
			    }

	2. 通过索引值指定参数位置

			
			<!--索引从0开始,如果不设置索引顺序的话,构造函数会因为参数报异常-->
			 <bean id="car2" class="com.spring.di.bean.Car">
		        <constructor-arg value="宝马" index="0"/>
		        <constructor-arg value="450000" index="2"/>
		        <constructor-arg value="华晨" index="1"/>
		    </bean>

	3. 通过类型区分重载的构造器
		
			/**通过指定类型可以防止如果有相同数量的参数构造器匹配错误**/
			 <bean id="car2" class="com.spring.di.bean.Car">
		        <constructor-arg value="宝马" index="0" type="java.lang.String"/>
		        <constructor-arg value="450000" index="2"  type="java.lang.Integer"/>
		        <constructor-arg value="华晨" index="1"  type="java.lang.String"/>
		    </bean>

			//Car类的构造函数
			public Car(String brand, String crop, Double price) {
		        this.brand = brand;
		        this.crop = crop;
		        this.price = price;
		    }
		
		    public Car(String brand, String crop, Integer speed) {
		        this.brand = brand;
		        this.crop = crop;
		        this.speed = speed;
		    }
		
			/**从反射打印可以看出构造器的打印顺序与书写代码的顺序有关(最后打印为空的是空构造器)***/
			 private static void test3()
		    {
		        Class<Car> carClass = Car.class;
		        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
		        for (Constructor constructor:declaredConstructors)
		        {
		            Class[] parameterTypes = constructor.getParameterTypes();
		            for (Class type:parameterTypes)
		            {
		                System.out.print(type.getName()+" ");
		            }
		            System.out.println();
		        }

				/*
				java.lang.String java.lang.String java.lang.Integer 
				java.lang.String java.lang.String java.lang.Double 
				
				*/
		    }

**p名称空间**

为了简化XML文件的配置，越来越多的XML文件采用属性而非子元素配置信息。Spring从2.5版本开始引入了一个新的p命名空间，可以通过<bean>元素属性的方式配置Bean的属性。
使用p命名空间后，基于XML的配置方式将进一步简化。

		//在头部引入标签
		 xmlns:p="http://www.springframework.org/schema/p"

		//只需把Car类的setXXX(XXX)方法设置好，需要赋值的写在bean标签里,p标签也是通过类的set方法进行赋值(与标签<property>的使用一样)
		<bean id="car3" class="com.spring.di.bean.Car" p:brand="本田" p:crop="东本" p:price="200000" p:speed="200"/>

**可以使用的值**

**1. 字面量**

1. 可以使用字符串表示的值，可以通过value属性或value子节点的方式指定
2. 基本数据类型及其封装类、String等类型都可以采取字面值注入的方式
3. 若字面值中包含特殊字符，可以使用<![CDATA[]]>把字面值包裹起来

		 <!-- 字面量(在需要赋值特殊符号时推荐使用第二种方式，不需要记住特殊符号的实体名称,可以直接写)
	        特殊字符:
	            1. 使用实体.  &nbsp;
	                    &: &amp;
	                    <: &lt;
	                    >: &gt;
	                    ": &quot;
	                    ':
	            2. <![CDATA[  写任意字符    ]]>
	     -->
		<!--推荐都是在<property>标签里进行赋值，除非是有特殊符号才使用<value>标签-->
	    <bean id="book" class="com.spring.di.bean.Book">
	        <property name="bookId" value="1000"/>
	        <!--<property name="bookName" value="&lt;&lt;Java从入门到精通&gt;&gt;"/>-->
	        <property name="bookName">
	            <value><![CDATA[& ' " > < * % $ #@ ]]]></value>
	        </property>
	
	    </bean>

**2. null值**
	
	<!--null标签赋值为Null，与不写不进行赋值的效果一样(了解)-->
    <bean id="person2" class="com.spring.di.bean.Person">
        <property name="id" value="103"></property>
        <property name="name" value="Julia老师"></property>
       <!-- <property name="car"><null /></property>-->
    </bean>

**3. 给bean的级联属性赋值**

	 <bean id="person" class="com.spring.di.bean.Person">
        <property name="id" value="101"/>
        <property name="name" value="苍老师"/>
        <property name="car" ref="car"/>
        <!-- 给级联属性赋值-->
		<!--通过指定其它容器里的属性进行赋值-->
        <property name="car.speed" value="100"/>
    </bean>

**4. 外部已声明的bean**

		 <!-- 引用其他的bean -->
		<!--通过ref选择当前IOC容器里的bean id进行赋值-->
	    <bean id="person" class="com.spring.di.bean.Person">
	        <property name="id" value="101"/>
	        <property name="name" value="苍老师"/>
	        <property name="car" ref="car"/>
	    </bean>

**5. 内部bean**

当bean实例仅仅给一个特定的属性使用时，可以将其声明为内部bean。内部bean声明直接包含在<property>或<constructor-arg>元素里，不需要设置任何id或name属性
**内部bean不能使用在任何其他地方**

	<!-- 内部bean -->
    <bean id="person1" class="com.spring.di.bean.Person">
        <property name="id" value="102"/>
        <property name="name" value="三上老师"/>
        <property name="car">
           <bean class="com.spring.di.bean.Car">
               <property name="brand" value="Mini"></property>
               <property name="crop" value="宝马"></property>
               <property name="price" value="300000"></property>
               <property name="speed" value="260"></property>
           </bean>
        </property>
    </bean>