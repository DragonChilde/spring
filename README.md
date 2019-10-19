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

**集合属性**

在Spring中可以通过一组内置的XML标签来配置集合属性

	例如：<list>，<set>或<map>。

**1. 数组和List**

	配置java.util.List类型的属性，需要指定<list>标签，在标签里包含一些元素。这些标签	可以通过<value>指定简单的常量值，通过<ref>指定对其他Bean的引用。通过<bean>	指定内置bean定义。通过<null/>指定空元素。甚至可以内嵌其他集合。
	数组的定义和List一样，都使用<list>元素。
	配置java.util.Set需要使用<set>标签，定义的方法与List一样。

    <!-- List集合 -->
	<!--List集合和Array数组的使用一样，因为集合的底层使用的就是数组,所以如果是数组类型,建议也是使用<list>标签,<set>标签与<list>标签定义一样-->
    <bean id="personlist" class="com.spring.di.bean.PersonList">
        <property name="name" value="宇田老师"/>
        <property name="cars">
            <list>
                <ref bean="car"/>
                <ref bean="car2"/>
                <ref bean="car3"/>
            </list>
			<!-- <array></array>-->
			 <!--<set></set>-->
        </property>
    </bean>

	/**注意这里car引用bean时speed明明没有定义，为何会打印有值，因为在级联属性赋值,已经通过指定car的speed赋值了,对于底层原理，两个引用指向同一个对象**/
	/**PersonList{name='宇田老师', cars=[Car{brand='丰田', crop='广汽', price=200000.0, speed=100}, Car{brand='宝马', crop='华晨', price=null, speed=450000}, Car{brand='本田', crop='东本', price=200000.0, speed=200}]}**/

**2. Map**

	Java.util.Map通过<map>标签定义，<map>标签里可以使用多个<entry>作为子标签。每个条目包含一个键和一个值。
	必须在<key>标签里定义键。
	因为键和值的类型没有限制，所以可以自由地为它们指定<value>、<ref>、<bean>或<null/>元素。
	可以将Map的键和值作为<entry>的属性定义：简单常量使用key和value来定义；bean引用通过key-ref和value-ref属性定义。

	  <!-- Map集合 -->
    <bean id="personmap" class="com.spring.di.bean.PersonMap">
        <property name="name" value="藤井老师"/>
        <property name="cars">
            <map>
                <entry key="aaa" value-ref="car"/>
                <entry key="bbb" value-ref="car2" />
                <entry key="ccc" value-ref="car3"/>
            </map>
        </property>
    </bean>

**3. 集合类型的bean**

如果只能将集合对象配置在某个bean内部，则这个集合的配置将不能重用。我们需要将集合bean的配置拿到外面，供其他bean引用。

配置集合类型的bean需要引入util名称空间
	
	<!--需要加入util命名空间-->
	xmlns:util="http://www.springframework.org/schema/util"
	xsi:schemaLocation="http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd"

	  <!-- 集合Bean，通过这种定义，可以多个引用同一个集合bean，就不需每次都重新定义,因为在bean里定义的集合bean是无法多个引用的-->
	<!--也可以在里面定义一个bean-->
    <util:list id="carlist">
        <ref bean="car"/>
        <ref bean="car2"/>
        <ref bean="car3"/>
        <!--<bean></bean>-->
    </util:list>


**FactoryBean**

Spring中有两种类型的bean，一种是普通bean，另一种是工厂bean，即FactoryBean。

工厂bean跟普通bean不同，其返回的对象不是指定类的一个实例，其返回的是该工厂bean的getObject方法所返回的对象。

工厂bean必须实现org.springframework.beans.factory.FactoryBean接口。

	<bean id="car" class="com.spring.factorybean.bean.CarFactoryBean"/>

	/**工厂bean的实际使用是在整合时通过继承已经定义好的xxxFactoryBean来使用**/
	public class CarFactoryBean implements FactoryBean<Car> {
	
	    /**
	     * 工厂bean具体创建的bean对象是由getObject方法来返回的.
	     */
	    public Car getObject() throws Exception {
	        return  new Car("五菱宏光", "五菱", 50000);
	    }
	
	    /**
	     *  返回具体的bean对象的类型
	     */
	    public Class<?> getObjectType() {
	        return Car.class;
	    }
	
	    /**
	     * bean 可以是单例的   也可以是原型的(非单例)： 后面讲bean的作用域再研究.
	     */
	    public boolean isSingleton() {
	        return true;
	    }
	}

**BeanFactory 和 FactoryBean的区别:**

两个根本没有可比性，只是长得像而已，BeanFactory是IOC容器的顶层接口,FactoryBean是工厂方法的接口，

**bean的高级配置**

**1. 配置信息的继承**

Spring允许继承bean的配置，被继承的bean称为父bean。继承这个父bean的bean称为子bean

子bean从父bean中继承配置，包括bean的属性配置

子bean也可以覆盖从父bean继承过来的配置


	 <!--以id为address为模板，创建address2-->
	<!--可以继续class，和其属性city,street-->
	<!--address2.street覆盖了继承的属性-->
	<bean id="address" class="com.spring.relation.bean.Address">
	    <property name="city" value="广州"/>
	    <property name="street" value="体育西路"/>
	</bean>
	
	<bean id="address2" parent="address">
	    <property name="street" value="体育东路"/>
	</bean>

**补充说明**

	父bean可以作为配置模板，也可以作为bean实例。若只想把父bean作为模板，可以设置<bean>的abstract 属性为true，这样Spring将不会实例化这个bean
	如果一个bean的class属性没有指定，则必须是抽象bean
	并不是<bean>元素里的所有属性都会被继承。比如：autowire，abstract等。
	也可以忽略父bean的class属性，让子bean指定自己的类，而共享相同的属性配置。但此时abstract必须设为true。


	 <!-- bean的继承关系
		 abstract="true": 抽象bean. 不能被创建对象. class可以省略不配置
		 继承可以从父bean中继承一些配置， 但是 id  abstract  autowire 是不能被继承下来的.
	 -->
    <bean id="address" abstract="true">
        <property name="city" value="广州"/>
        <property name="street" value="体育西路"/>
    </bean>
	<bean id="address2" class="com.spring.relation.bean.Address" parent="address"/>

**2. bean之间的依赖**

有的时候创建一个bean的时候需要保证另外一个bean也被创建，这时我们称前面的bean对后面的bean有依赖。例如：要求创建address3对象的时候必须创建address4。	这里需要注意的是依赖关系不等于引用关系，address3即使依赖address4也可以不引用它。

	<!-- 依赖关系 -->
	<!--只有在address4存在的时候才能成功创建address3,否则会报异常-->
	 <bean id="address3" class="com.spring.relation.bean.Address" depends-on="address4"/>

    <bean id="address4" class="com.spring.relation.bean.Address"/>

**bean的作用域**

	在Spring中，可以在<bean>元素的scope属性里设置bean的作用域，以决定这个bean是单实例的还是多实例的。
	
	默认情况下，Spring只为每个在IOC容器里声明的bean创建唯一一个实例，整个IOC容器范围内都能共享该实例：所有后续的getBean()调用和bean引用都将返回这个唯一的bean实例。该作用域被称为singleton，它是所有bean的默认作用域。

	singleton	\\在SpringIOC容器中仅存在一个Bean实例,Bean以单实例的方式存在
	prototype	\\每次调用getBean()时都会返回一个新的实例
	request		\\每次HTTP请求都会创建一个新的Bean,该作用域仅适用于WebApplicationContext环境
	session		\\同一个HTTP Session共享一个Bean，不同的HTTP Session使用不同的Bean.该作用域仅适用于WebApplicationContext环境

	 <!--
		bean的作用域:
			singleton: 单例的(默认值), 在整个IOC容器中只能存在一个bean的对象. 而且在IOC
			                          容器对象被创建时，就创建单例的bean的对象. 后续每次通过getBean()方法
			                           获取bean对象时，返回的都是同一个对象.
			prototype: 原型的/多例的       在整个IOC容器中可有多个bean的对象。 在IOC容器对象被
					       创建时， 不会创建原型的bean的对象。 而是等到每次通过getBean()方法获取
					   bean对象时，才会创建一个新的bean对象返回.
			request:   一次请求对应一个bean对象
			session:   一次会话对应一个bean对象
	 -->
    <bean id="car" class="com.spring.di.bean.Car" scope="singleton">
        <property name="brand" value="丰田"/>
        <property name="crop" value="广汽"/>
        <property name="price" value="200000"/>
    </bean>


当bean的作用域为单例时，Spring会在IOC容器对象创建时就创建bean的对象实例。而当bean的作用域为prototype时，IOC容器在获取bean的实例时创建bean的实例对象。

**bean的生命周期**

1. Spring IOC容器可以管理bean的生命周期，Spring允许在bean生命周期内特定的时间点执行指定的任务
2. Spring IOC容器对bean的生命周期进行管理的过程：
	1. 通过构造器或工厂方法创建bean实例
	2. 为bean的属性设置值和对其他bean的引用
	3. 调用bean的初始化方法
	4. bean可以使用了
	5. 当容器关闭时，调用bean的销毁方法

			//spring-lifecycle.xml
			<bean id="car" class="com.spring.lifecycle.Car" init-method="init" destroy-method="destory">
		        <property name="brand" value="丰田"/>
		        <property name="price" value="200000"/>
		    </bean>

			//com.spring.lifecycle.Car
			public class Car {
			    private String brand ;
			
			    private Double price ;
			
			    public Car() {
			        System.out.println("===>1. 调用构造器创建bean对象 ");
			    }
			
			    /**
			     * 初始化方法
			     * 需要通过 init-method来指定初始化方法
			     */
			    public void init()
			    {
			        System.out.println("===>3. 调用初始化方法");
			    }
			
			
			    /**
			     * 销毁方法： IOC容器关闭， bean对象被销毁.
			     */
			    public void destory()
			    {
			        System.out.println("===>5. 调用销毁方法");
			    }
			
			    public Car(String brand, Double price) {
			        this.brand = brand;
			        this.price = price;
			    }
			
			    public String getBrand() {
			        return brand;
			    }
			
			    public void setBrand(String brand) {
			        System.out.println("===>2. 调用set方法给对象的属性赋值");
			        this.brand = brand;
			    }
			
			    public Double getPrice() {
			        return price;
			    }
			
			    public void setPrice(Double price) {
			        this.price = price;
			    }
			
			    @Override
			    public String toString() {
			        return "Car{" +
			                "brand='" + brand + '\'' +
			                ", price=" + price +
			                '}';
			    }
			}

			  private static void test1()
		    {
				//注意:这里需要关闭容器，因此需要定义ConfigurableApplicationContext类型
		        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-lifecycle.xml");
		        Car car = context.getBean("car", Car.class);
		        System.out.println("===>4. 使用bean对象" + car);
		        //关闭容器
		        context.close();
		    }
			/**
			===>1. 调用构造器创建bean对象 
			===>2. 调用set方法给对象的属性赋值
			===>3. 调用初始化方法
			===>4. 使用bean对象Car{brand='丰田', price=200000.0}
			十月 14, 2019 5:38:16 下午 org.springframework.context.support.ClassPathXmlApplicationContext doClose
			信息: Closing org.springframework.context.support.ClassPathXmlApplicationContext@179d3b25: startup date [Mon Oct 14 17:38:15 CST 2019]; root of context hierarchy
			===>5. 调用销毁方法
			**/

3. 在配置bean时，通过init-method和destroy-method 属性为bean指定初始化和销毁方法
4. bean的后置处理器
	1. bean后置处理器允许在调用**初始化方法前后**对bean进行额外的处理
	2. bean后置处理器对IOC容器里的所有bean实例逐一处理，而非单一实例。其典型应用是：检查bean属性的正确性或根据特定的标准更改bean的属性。
	3. bean后置处理器时需要实现接口：org.springframework.beans.factory.config.BeanPostProcessor。在初始化方法被调用前后，Spring将把每个bean实例分别传递给上述接口的以下两个方法：
		1. postProcessBeforeInitialization(Object, String) 
		2. postProcessAfterInitialization(Object, String)

				<!--spring-lifecycle.xml-->
				 <!-- 配置后置处理器 : Spring能自动识别是一个后置处理器 -->
				<bean class="com.spring.lifecycle.bean.MyBeanPostProcessor"/>

				/**
				 * bean的后置处理器 : 对IOC容器中所有的bean都起作用.
				 */
				public class MyBeanPostProcessor implements BeanPostProcessor {
				
				    /**
				     * 在bean的生命周期的初始化方法之前执行
				     * Object bean: 正在被创建的bean对象.
				     * String beanName: bena对象的id值.
				     */
					//可以根据需求返回特定的Object类型
				    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
				        System.out.println("postProcessBeforeInitialization");
				        return o;
				    }
				
				    /**
				     * 在bean的生命周期的初始化方法之后执行
				     */
					//可以根据需求返回特定的Object类型
				    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
				        System.out.println("postProcessAfterInitialization");
				        return o;
				    }
				}

				/**
				===>1. 调用构造器创建bean对象 
				===>2. 调用set方法给对象的属性赋值
				postProcessBeforeInitialization
				===>3. 调用初始化方法
				postProcessAfterInitialization
				===>4. 使用bean对象Car{brand='丰田', price=200000.0}
				十月 14, 2019 5:57:40 下午 org.springframework.context.support.ClassPathXmlApplicationContext doClose
				信息: Closing org.springframework.context.support.ClassPathXmlApplicationContext@179d3b25: startup date [Mon Oct 14 17:57:39 CST 2019]; root of context hierarchy
				===>5. 调用销毁方法
				**/

5. 添加bean后置处理器后bean的生命周期
	1. 通过构造器或工厂方法**创建bean实例**
	2. 为bean的**属性设置值**和对其他bean的引用
	3. 将bean实例传递给bean后置处理器的postProcessBeforeInitialization()方法
	4. 调用bean的**初始化**方法
	5. 将bean实例传递给bean后置处理器的postProcessAfterInitialization()方法
	6. bean可以使用了
	7. 当容器关闭时调用bean的**销毁方法**


**引用外部属性文件**

当bean的配置信息逐渐增多时，查找和修改一些bean的配置信息就变得愈加困难。这时可以将一部分信息提取到bean配置文件的外部，以properties格式的属性文件保存起来，同时在bean的配置文件中引用properties属性文件中的内容，从而实现一部分属性值在发生变化时仅修改properties属性文件即可。这种技术多用于连接数据库的基本信息的配置。

**直接配置**

	  <!-- 直接配置c3p0连接池    ComboPooledDataSource-->
    <bean id="datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://120.77.237.175:9306/mysql"/>
        <property name="user" value="root"/>
        <property name="password" value="123456"/>
        <property name="initialPoolSize" value="5"></property>
        <property name="maxPoolSize" value="10"></property>
    </bean>

**使用外部的属性文件**

**1. 创建properties属性文件**

		# k = v 
		jdbc.driverClass=com.mysql.jdbc.Driver
		jdbc.jdbcUrl=jdbc:mysql://120.77.237.175:9306/mysql
		jdbc.user=root
		jdbc.password=123456

**2. 引入context名称空间**
		
		xmlns:context="http://www.springframework.org/schema/context"
		 xsi:schemaLocation="http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd"	

**3. 指定properties属性文件的位置**

	
		<!-- classpath:xxx 表示属性文件位于类路径下 -->
		<context:property-placeholder location="classpath:config/db.properties"/>

**4. 从properties属性文件中引入属性值**

	    <bean id="datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	        <property name="driverClass"  value="${jdbc.driverClass}"/>
	        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
	        <property name="user" value="${jdbc.user}"/>
	        <property name="password" value="${jdbc.password}"/>
	    </bean>

另:还有一种引入方式(通过指定class类的属性进行配置，这种方式比较旧)

	 <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		 <property name="location" value="classpath:db.properties"></property>
	 </bean>

**注意:classpath指的是在当前类路径下进行查询，如果是多个模块下的配置是classpath***

**自动装配**

**自动装配的概念**

1. 手动装配：以value或ref的方式明确指定属性值都是手动装配。
2. 自动装配：根据指定的装配规则，不需要明确指定，Spring自动将匹配的属性值注入bean中。

**装配模式**

1. 根据类型自动装配：将类型匹配的bean作为属性注入到另一个bean中。若IOC容器中有多个与目标bean类型一致的bean，Spring将无法判定哪个bean最合适该属性，所以不能执行自动装配
2. 根据名称自动装配：必须将目标bean的名称和属性名设置的完全相同
3. 通过构造器自动装配：当bean中存在多个构造器时，此种自动装配方式将会很复杂。不推荐使用。

		 <!-- Car -->
	    <bean id="car1" class="com.spring.autowire.bean.Car">
	        <property name="brand" value="奔驰"></property>
	        <property name="price" value="500000"></property>
	    </bean>
	
	    <!-- Address -->
	    <bean id="address" class="com.spring.autowire.bean.Address">
	        <property name="province" value="山西省"></property>
	        <property name="city" value="太原市"></property>
	    </bean>
		<!--
	    <bean id="address2" class="com.spring.autowire.bean.Address">
	        <property name="province" value="山西省"></property>
	        <property name="city" value="太原市"></property>
	    </bean>
		-->
	
	    <!-- Person  : 演示自动装配
	
			 byName: 使用bean的属性名与IOC容器中<bean>的id值进行匹配. 匹配成功则装配成功.
	
			 byType: 使用bean的属性的类型与IOC容器中<bean>的class进行匹配。 如果唯一匹配则装配成功
			                     如果匹配到多个兼容类型的bean。则跑出异常。
		-->
	    <bean id="person" class="com.spring.autowire.bean.Person" autowire="byType">
	        <property name="name" value="Tom"></property>
	    </bean>

**选用建议**

相对于使用注解的方式实现的自动装配，在XML文档中进行的自动装配略显笨拙，在项目中更多的使用注解的方式实现。


**扫描组件**

组件被上述注解标识后还需要通过Spring进行扫描才能够侦测到。

1. 指定被扫描的package
		
		<!--spring-annotation.xml-->
		<!--这里使用了context标签，务必要引入context声明-->
		<!-- 组件扫描:  扫描加了注解的类，并管理到IOC容器中 
		base-package: 基包. Spring会扫描指定包以及子包下所有的类，将带有注解的类管理到IOC容器中
		-->
		<context:component-scan base-package="com.spring.annotation"/>

		
		/**
		 * @Cotroller 注解的作用:
		 * 相当于在xml文件中:
		 * <bean id="userController" class="com.spring.annotation.controller.UserController">
		 * 注解默认的id值 就是类名首字母小写， 可以在注解中手动指定id值:@Controller(value="id值"),可以简写为:@Controller("id值")
		 */
		/**com.spring.annotation.controller.UserController**/
		@Controller
		public class UserController {
		}

		/**com.spring.annotation.TestAnnotation**/
		 private static void test1()
	    {
	        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-annotation.xml");
	        UserController userController = classPathXmlApplicationContext.getBean("userController", UserController.class);
	        System.out.println(userController);
	
	        UserService userServiceImpl = classPathXmlApplicationContext.getBean("userServiceImpl", UserServiceImpl.class);
	        System.out.println(userServiceImpl);
	
	        UserDao userDao = classPathXmlApplicationContext.getBean("userDaoImpl", UserDaoImpl.class);
	        System.out.println(userDao);

			/*
			com.spring.annotation.controller.UserController@eafc191
			com.spring.annotation.service.UserServiceImpl@612fc6eb
			com.spring.annotation.dao.UserDaoImpl@1060b431
			*/
	    }

2. 详细说明
	1. base-package属性指定一个需要扫描的基类包，Spring容器将会扫描这个基类包及其子包中的所有类。
	2. 当需要扫描多个包时可以使用逗号分隔。
	3. 如果仅希望扫描特定的类而非基包下的所有类，可使用resource-pattern属性过滤特定的类
	4. 包含与排除
		- <context:include-filter>子节点表示要包含的目标类
			
			注意：通常需要与use-default-filters属性配合使用才能够达到“仅包含某些组件”这样的效果。即：通过将use-default-filters属性设置为false，			禁用默认过滤器，然后扫描的就只是include-filter中的规则指定的				组件了。
		- <context:exclude-filter>子节点表示要排除在外的目标类
		- component-scan下可以拥有若干个include-filter和exclude-filter子节点
		- 过滤表达式


				annotation	com.spring.XxxAnnotation	//过滤所有标注了XxxAnnotation的类。这个规则根据目标组件是否标注了指定类型的注解进行过滤。
				assignable	com.spring.BaseXxx			//过滤所有BaseXxx类的子类。这个规则根据目标组件是否是指定类型的子类的方式进行过滤。
				aspectj		com.spring.*Service+		//所有类名是以Service结束的，或这样的类的子类。这个规则根据AspectJ表达式进行过滤。
				regex		com\.spring\.anno\.*		//所有com.atguigu.anno包下的类。这个规则根据正则表达式匹配到的类名进行过滤。
				custom		com.spring.XxxTypeFilter	//使用XxxTypeFilter类通过编码的方式自定义过滤规则。该类必须实现org.springframework.core.type.filter.TypeFilter接口


				<!-- 指定扫描  必须 设置use-default-filters="false"
						排除扫描   use-default-filters="true"   -->
				<!--指定扫描Contoller，只能扫描到@Controller注解-->
				<context:component-scan base-package="com.spring.annotation" use-default-filters="false">
			        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
			    </context:component-scan>

				//只能成功打印UserController
				/**
					com.spring.annotation.controller.UserController@77ec78b9
				**/

				<!--排除扫描Contoller,不扫描@Controller注解-->
				 <context:component-scan base-package="com.spring.annotation" use-default-filters="true">
			        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
			    </context:component-scan>
				
				//只能成功打印UserServiceImpl和UserDaoImpl
				/**
				com.spring.annotation.service.UserServiceImpl@ed9d034
				com.spring.annotation.dao.UserDaoImpl@6121c9d6
				**/

				
3. JAR包 必须在原有JAR包组合的基础上再导入一个：spring-aop-4.3.18.RELEASE.jar

**组件装配**

1. Controller组件中往往需要用到Service组件的实例，Service组件中往往需要用到Repository组件的实例。Spring可以通过注解的方式帮我们实现属性的装配。
2. 实现依据,在指定要扫描的包时，<context:component-scan> 元素会自动注册一个bean的后置处理器：AutowiredAnnotationBeanPostProcessor的实例。该后置处理器可以自动装配标记了**@Autowired**、@Resource或@Inject注解的属性。

		/**
		 * @Cotroller 注解的作用:
		 * 相当于在xml文件中:
		 * <bean id="userController" class="com.spring.annotation.controller.UserController">
		 * 注解默认的id值 就是类名首字母小写， 可以在注解中手动指定id值:@Controller(value="id值"),可以简写为:@Controller("id值")
		 */
		@Controller
		public class UserController {
		
		    @Autowired
		    private UserService userService;
		
		    public void regist()
		    {
		        userService.handleAddUser();
		    }
		}

3. @Autowired注解
	1. 根据类型实现自动装配。
	2. 构造器、普通字段(即使是非public)、一切具有参数的方法都可以应用@Autowired注解
	3. 默认情况下，所有使用@Autowired注解的属性都需要被设置。当Spring找不到匹配的bean装配属性时，会抛出异常。
	4. 若某一属性允许不被设置，可以设置@Autowired注解的required属性为 false
	5. 默认情况下，当IOC容器里存在多个类型兼容的bean时，Spring会尝试匹配bean的id值是否与变量名相同，如果相同则进行装配。如果bean的id值不相同，通过类型的自动装配将无法工作。此时可以在@Qualifier注解里提供bean的名称。Spring	甚至允许在方法的形参上标注@Qualifiter注解以指定注入bean的名称。
	6. @Autowired注解也可以应用在数组类型的属性上，此时Spring将会把所有匹配的bean进行自动装配。
	7. @Autowired注解也可以应用在集合属性上，此时Spring读取该集合的类型信息，然后自动装配所有与之兼容的bean。
	8. @Autowired注解用在java.util.Map上时，若该Map的键值为String，那么 Spring将自动装配与值类型兼容的bean作为值，并以bean的id值作为键。
4. @Resource注解要求提供一个bean名称的属性，若该属性为空，则自动采用标注处的变量或方法名作为bean的名称。(基本不用)
5. @Inject和@Autowired注解一样也是按类型注入匹配的bean，但没有reqired属性。(基本不用)

		@Repository("userDao")
		public class UserDaoMybatisImpl implements UserDao{
		
		    public void addUser() {
		        System.out.println("UserDao  Mybatis .....");
		    }
		}
	
		@Repository
		public class UserDaoImpl implements UserDao{
		
		    public void addUser() {
		        System.out.println("UserDao operation...........");
		    }
		}


		/**
		情况一：定义两个类继承UserDao,注解@Repository,这时运行会报异常,因为要byType查找到两个UserDaoMybatisImpl和UserDaoImpl，只能在其中之一里指定一个唯一bean id值,@Repository("userDao"),才可注入成功
		情况二:可以通过@Qualifier(id值)加载指定的类,@Qualifier("userDaoMybatisImpl")
		情况三:@Autowired(required = false)可以设置成有就装配，没有就不装配，把上面两个类的@Repository注释掉，这时侯userDao取到的是默认值NULL，当方法调用时会报空指针异常java.lang.NullPointerException
		**/
		@Service
		public class UserServiceImpl implements UserService {
			/**
			 *  @Autowired 完成bean属性的自动装配
			 *  
			 *  工作机制:  首先会使用byType的方式进行自动装配，如果能唯一匹配，则装配成功， 
			 *           如果匹配到多个兼容类型的bean, 还会尝试使用byName的方式进行唯一确定. 
			 *           如果能唯一确定，则装配成功，如果不能唯一确定，则装配失败，抛出异常. 
			 *           byType和byName取到的值都必须是唯一的，不是有多或少
			 *  
			 *  默认情况下， 使用@Autowired标注的属性必须被装配，如果装配不了，也会抛出异常. 
			 *  可以使用required=false来设置不是必须要被装配. 
			 *  
			 *  如果匹配到多个兼容类型的bean，可以使用@Qualifier来进一步指定要装配的bean的id值 。
			 *  
			 *  @Autowired @Qualifier 注解即可在成员变量上，也可以加在对应的set方法上.. 
			 *  
			 */
			
			@Autowired(required = false)
		    @Qualifier("userDao")
		    private UserDao userDao;

			//第二种注入方式，可以通过设置set方法注入到参数里,但要多写一个方法，没上面直接在变量里注入方便
			/*
		    @Autowired
		    @Qualifier("userDao")
		    public void setUserDao(UserDao userDao)
		    {
		        this.userDao = userDao;
		    }
			*/
		
		    public void handleAddUser() {
		        userDao.addUser();
		    }
		}


# AOP #

**动态代理**

动态代理的原理

代理设计模式的原理：使用一个代理将对象包装起来，然后用该代理对象取代原始对象。任何对原始对象的调用都要通过代理。代理对象决定是否以及何时将方法调用转到原始对象上。

![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/5.png?raw=true)

**动态代理的方式**

1. 基于接口实现动态代理：JDK动态代理
2. 基于继承实现动态代理： Cglib、Javassist动态代理 


		public interface ArithmeticCalculator {
		
		    public int add(int i,int j);
		
		    public int sub(int i,int j);
		
		    public int mul(int i,int j);
		
		    public int div(int i,int j);
		}
		
		public class ArithmeticCalculatorImpl implements ArithmeticCalculator{
		    public int add(int i, int j) {
		       int result = i + j;
		       return result;
		    }
		
		    public int sub(int i, int j) {
		        int result = i - j;
		        return result;
		    }
		
		    public int mul(int i, int j) {
		        int result = i * j;
		        return result;
		    }
		
		    public int div(int i, int j) {
		        int result = i  / j;
		        return result;
		    }
		}

上面是一个计数器类，如果要增加日志记录，最Low的办法是在每个方法里都打印输出日志,这样会有问题:

1. 代码混乱：越来越多的非业务需求(日志和验证等)加入后，原有的业务方法急剧膨胀。每个方法在处理核心逻辑的同时还必须兼顾其他多个关注点
2. 代码分散: 以日志需求为例，只是为了满足这个单一需求，就不得不在多个模块（方法）里多次重复相同的日志代码。如果日志需求发生变化，必须修改所有模块。

		/**
		 * 生成代理对象。
		 *
		 * JDK的动态代理:
		 * 	 1. Proxy : 是所有动态代理类的父类， 专门为用户生成代理类或者是代理对象
		 * 		 	public static Class<?> getProxyClass(ClassLoader loader,
		 Class<?>... interfaces)
		 用于生成代理类的Class对象.
		
		 * 			public static Object newProxyInstance(ClassLoader loader,
		 Class<?>[] interfaces,
		 InvocationHandler h)
		 用于生成代理对象
		
		 *   2. InvocationHandler :完成动态代理的整个过程.
		 *   		public Object invoke(Object proxy, Method method, Object[] args)
		 throws Throwable;
		 *
		 */
		
		public class ArithmeticCalculatorProxy {
		
		    //动态代理:    目标对象     如何获取代理对象      代理要做什么
		
		    //目标对象
		    private ArithmeticCalculator target;
		
		    public ArithmeticCalculatorProxy(ArithmeticCalculator target) {
		        this.target = target;
		    }
		
		    //获取代理对象的方法
		    public Object getProxy()
		    {
		        //代理对象
		        Object proxy;
		
		        /**
		         * loader:  ClassLoader对象。 类加载器对象.  帮我们加载动态生成的代理类。
		         *
		         * interfaces: 接口们.  提供目标对象的所有的接口.  目的是让代理对象保证与目标对象都有接口中想同的方法.
		         *
		         * h:  InvocationHandler类型的对象.
		         */
		
		        ClassLoader classLoader = target.getClass().getClassLoader();
		        Class<?>[] interfaces = target.getClass().getInterfaces();
		
		        proxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
		            /**
		             * invoke:  代理对象调用代理方法， 会回来调用invoke方法。
		             *
		             * proxy: 代理对象 ， 在invoke方法中一般不会使用.
		             *
		             * method: 正在被调用的方法对象.
		             *
		             * args:   正在被调用的方法的参数.
		             */
					/**注意:invoke()方法在这里并没有被调用,只是相当于继承一个类重写一个了方法,真正何时被调用可以在下面的(模拟底层生成的动态代理类)可以看到**/
		            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		                //将方法的调用转回到目标对象上.
		
		                //获取方法的名字
		                String methodName = method.getName();
		                //记录日志
		                System.out.println("LoggingProxy: method is "+methodName+"! params is "+ Arrays.asList(args));
		                Object result = method.invoke(target,args);// 目标对象执行目标方法. 相当于执行ArithmeticCalculatorImpl中的+ - * /
		
		                //记录日志
		                System.out.println("LoggingProxy: result is "+result);
		                return result;
		            }
		        });
		
		        return proxy;
		    }
		}

	    private static void test1()
		{
		    //目标对象
		    ArithmeticCalculator target = new ArithmeticCalculatorImpl();
		
		    ArithmeticCalculatorProxy arithmeticCalculatorProxy = new ArithmeticCalculatorProxy(target);
		    //获取代理对象
		    Object proxy = arithmeticCalculatorProxy.getProxy();
		    // 转回具体的类型.
		    ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) proxy;
		    int result = arithmeticCalculator.add(1, 2);
		    System.out.println(result);

			/**
			LoggingProxy: method is add! params is [1, 2]
			LoggingProxy: result is 3
			result is 3
			**/
		}

**问题:**

1. 代理对象能否转换成目标对象的类型?

	不能,通过上面可看到代理对象和目标对象是兄弟关系，两个是同级的，但它们都是继承于同一个接口

2. 代理对象调用代理方法，为什么会执行InvocationHandler中的invoke 方法

		/**通过打印代理对象可以看到当前我们使用的代理对象名**/
		System.out.println(arithmeticCalculator.getClass().getName());
		/**
			com.sun.proxy.$Proxy0
		**/

		/**
		 * 模拟底层生成的动态代理类
		 */
		/**代理对象必须实现代理类和继承调用对象的接口**/
		class $Proxy0 extends Proxy implements ArithmeticCalculator{
			
			//必须生成构造函数和重写方法，构造函数要传入的参数h最终会赋值到java.lang.reflect.Proxy.h
		    public $Proxy0(InvocationHandler h) {
		        super(h);
		    }
		
			//因此每个代理对象的方法调用最终都是通过父类Proxy的h去invoke,进入的是重写的InvocationHandler.invoke方法，
			//注意：动态代理的原理里代理对象决定是否以及何时将方法调用转到原始对象上,这句话就是在这里体现了，代理对象在这里只是通过代理Proxy.h去调invoke()方法，并没有实际实现
		    public int add(int i, int j) {
		        //super.h.invoke(this,方法对象,方法参数)
		        return 0;
		    }
		
		    public int sub(int i, int j) {
		        return 0;
		    }
		
		    public int mul(int i, int j) {
		        return 0;
		    }
		
		    public int div(int i, int j) {
		        return 0;
		    }
		}

动态代理是虚拟机在调用时自动创建的，以下是保存生成的动态代理类,

	Properties properties = System.getProperties();
 	properties.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

生成的代理类,发现与上面模拟的动态代理类基本累似

	public final class $Proxy0 extends Proxy implements ArithmeticCalculator {
	    private static Method m1;
	    private static Method m2;
	    private static Method m5;
	    private static Method m3;
	    private static Method m4;
	    private static Method m6;
	    private static Method m0;
	
	    public $Proxy0(InvocationHandler var1) throws  {
	        super(var1);
	    }
	
	    public final boolean equals(Object var1) throws  {
	        try {
	            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
	        } catch (RuntimeException | Error var3) {
	            throw var3;
	        } catch (Throwable var4) {
	            throw new UndeclaredThrowableException(var4);
	        }
	    }
	
	    public final String toString() throws  {
	        try {
	            return (String)super.h.invoke(this, m2, (Object[])null);
	        } catch (RuntimeException | Error var2) {
	            throw var2;
	        } catch (Throwable var3) {
	            throw new UndeclaredThrowableException(var3);
	        }
	    }
	
	    public final int mul(int var1, int var2) throws  {
	        try {
	            return (Integer)super.h.invoke(this, m5, new Object[]{var1, var2});
	        } catch (RuntimeException | Error var4) {
	            throw var4;
	        } catch (Throwable var5) {
	            throw new UndeclaredThrowableException(var5);
	        }
	    }
	
	    public final int add(int var1, int var2) throws  {
	        try {
	            return (Integer)super.h.invoke(this, m3, new Object[]{var1, var2});
	        } catch (RuntimeException | Error var4) {
	            throw var4;
	        } catch (Throwable var5) {
	            throw new UndeclaredThrowableException(var5);
	        }
	    }
	
	    public final int sub(int var1, int var2) throws  {
	        try {
	            return (Integer)super.h.invoke(this, m4, new Object[]{var1, var2});
	        } catch (RuntimeException | Error var4) {
	            throw var4;
	        } catch (Throwable var5) {
	            throw new UndeclaredThrowableException(var5);
	        }
	    }
	
	    public final int div(int var1, int var2) throws  {
	        try {
	            return (Integer)super.h.invoke(this, m6, new Object[]{var1, var2});
	        } catch (RuntimeException | Error var4) {
	            throw var4;
	        } catch (Throwable var5) {
	            throw new UndeclaredThrowableException(var5);
	        }
	    }
	
	    public final int hashCode() throws  {
	        try {
	            return (Integer)super.h.invoke(this, m0, (Object[])null);
	        } catch (RuntimeException | Error var2) {
	            throw var2;
	        } catch (Throwable var3) {
	            throw new UndeclaredThrowableException(var3);
	        }
	    }
	
	    static {
	        try {
	            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
	            m2 = Class.forName("java.lang.Object").getMethod("toString");
	            m5 = Class.forName("com.spring.aop.poxy.ArithmeticCalculator").getMethod("mul", Integer.TYPE, Integer.TYPE);
	            m3 = Class.forName("com.spring.aop.poxy.ArithmeticCalculator").getMethod("add", Integer.TYPE, Integer.TYPE);
	            m4 = Class.forName("com.spring.aop.poxy.ArithmeticCalculator").getMethod("sub", Integer.TYPE, Integer.TYPE);
	            m6 = Class.forName("com.spring.aop.poxy.ArithmeticCalculator").getMethod("div", Integer.TYPE, Integer.TYPE);
	            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
	        } catch (NoSuchMethodException var2) {
	            throw new NoSuchMethodError(var2.getMessage());
	        } catch (ClassNotFoundException var3) {
	            throw new NoClassDefFoundError(var3.getMessage());
	        }
	    }
	}

上面创建代理是通过newProxyInstance()方法,下面是通过getProxyClass()创建代理对象

	public class ArithmeticCalculatorProxy2 {
	    private ArithmeticCalculator target;
	
	    public ArithmeticCalculatorProxy2(ArithmeticCalculator target) {
	        this.target = target;
	    }
	
	    public Object getProxy() throws Exception
	    {
	        Object proxy;
	
	        ClassLoader classLoader = target.getClass().getClassLoader();
	        Class<?>[] interfaces = target.getClass().getInterfaces();
	        Class<?> proxyClass = Proxy.getProxyClass(classLoader,interfaces);
	
			//通过解析代理对象，可知要在构造函数要传入InvocationHandler对象
	        Constructor<?> con = proxyClass.getDeclaredConstructor(InvocationHandler.class);
	
	        proxy = con.newInstance(new InvocationHandler() {
	            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	                //将方法的调用转回到目标对象上.
	
	                //获取方法的名字
	                String methodName = method.getName();
	                //记录日志
	                System.out.println("LoggingProxy: method is "+methodName+"! params is "+ Arrays.asList(args));
	                Object result = method.invoke(target,args);
	                //相当于执行ArithmeticCalculatorImpl中的+ - * /
	
	                //记录日志
	                System.out.println("LoggingProxy: result is "+result);
	                return result;
	            }
	        });
	
	
	        return proxy;
	
			//最后的调用结果与上面创建的代理对象方法一样，通过上面的代理对象创建可以清晰看出为什么要传InvocationHandler类型
	    }
	}

**另:**

- **上面的代理对象示例是通过在方法里定义好InvocationHandler类型作用的，也可改成Object作用到全部类**
- **上面实现的示例都是通过代理对象类的接口，这方法的好处是可以把接口里已经定义好的方法都调用**
- **如果在ArithmeticCalculatorImpl类里定义了新方法,这时就不能使用基于接口的代理对象方法了，会报异常,只能通过基于继承的代理对象ArithmeticCalculatorImpl来实现**


**AOP概述**

1. AOP(Aspect-Oriented Programming，**面向切面编程**)：是一种新的方法论，是对传  统 OOP(Object-Oriented Programming，面向对象编程)的补充。
2. AOP编程操作的主要对象是切面(aspect)，而**切面模块化横切关注点**。
3. 在应用AOP编程时，仍然需要定义公共功能，但可以明确的定义这个功能应用在哪里，以什么方式应用，并且不必修改受影响的类。这样一来横切关注点就被模块化到特殊的类里——这样的类我们通常称之为“切面”。
4. AOP的好处：
	1. 每个事物逻辑位于一个位置，代码不分散，便于维护和升级
	2. 业务模块更简洁，只包含核心业务代码
	3. AOP图解

![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/3.png?raw=true)

**AOP术语**

- 横切关注点:从每个方法中抽取出来的同一类非核心业务。
- 切面(Aspect):封装横切关注点信息的类，每个关注点体现为一个通知方法。
- 通知(Advice):切面必须要完成的各个具体工作
- 目标(Target):被通知的对象
- 代理(Proxy)：向目标对象应用通知之后创建的代理对象
- 连接点(Joinpoint):横切关注点在程序代码中的具体体现，对应程序执行的某个特定位置。例如：类某个方法调用前、调用后、方法捕获到异常后等。在应用程序中可以使用横纵两个坐标来定位一个具体的连接点：

![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/2.png)

- 切入点(pointcut)：定位连接点的方式。每个类的方法中都包含多个连接点，所以连接点是类中客观存在的事物。如果把连接点看作数据库中的记录，那么切入点就是查询条件——AOP可以通过切入点定位到特定的连接点。切点通过org.springframework.aop.Pointcut 接口进行描述，它使用类和方法作为连接点的查询条件。
![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/4.jpg?raw=true)