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
	                Object result = method.invoke(target,args);		// 目标对象执行目标方法. 相当于执行ArithmeticCalculatorImpl中的+ - * /
	
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

**AspectJ**:Java社区里最完整最流行的AOP框架.在Spring2.0以上版本中，可以使用基于AspectJ注解或基于XML配置的AOP

**在Spring中启用AspectJ注解支持**

1. 导入JAR包

	- com.springsource.net.sf.cglib-2.2.0.jar
	- com.springsource.org.aopalliance-1.0.0.jar
	- com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar 
	- spring-aop-4.0.0.RELEASE.jar
	- spring-aspects-4.0.0.RELEASE.jar

2. 引入aop名称空间

		xmlns:aop="http://www.springframework.org/schema/aop"
		xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd"

3. 配置
	
		<aop:aspectj-autoproxy />
	当Spring IOC容器侦测到bean配置文件中的<aop:aspectj-autoproxy>元素时，会自动为与AspectJ切面匹配的bean创建代理

**用AspectJ注解声明切面**

1. 要在Spring中声明AspectJ切面，只需要在IOC容器中将切面声明为bean实例
2. 当在Spring IOC容器中初始化AspectJ切面之后，Spring IOC容器就会为那些与 AspectJ切面相匹配的bean创建代理
3. 在AspectJ注解中，切面只是一个带有@Aspect注解的Java类，它往往要包含很多通知
4. 通知是标注有某种注解的简单的Java方法
5. AspectJ支持5种类型的通知注
	1. @Before：前置通知，在方法执行之前执行
	2. @After：后置通知，在方法执行之后执行
	3. @AfterRunning：返回通知，在方法返回结果之后执行
	4. @AfterThrowing：异常通知，在方法抛出异常之后执行
	5. @Around：环绕通知，围绕着方法执行

# AOP细节 #

**切入点表达式**

通过表达式的方式定位一个或多个具体的连接点

**语法细节**

1. 切入点表达式的语法格式

		execution([权限修饰符] [返回值类型] [简单类名/全类名] [方法名]([参数列表]))
2. 举例说明 

		表达式	execution(* com.spring.aop.poxy.ArithmeticCalculator.*(..))
		含义		ArithmeticCalculator接口中声明的所有方法。
				第一个“*”代表任意修饰符及任意返回值。
				第二个“*”代表任意方法。
				“..”匹配任意数量、任意类型的参数。
				若目标类、接口与该切面类在同一个包中可以省略包名。

		表达式	execution(public * ArithmeticCalculator.*(..))
		含义		ArithmeticCalculator接口的所有公有方法

		表达式	execution(public double ArithmeticCalculator.*(..))
		含义		ArithmeticCalculator接口中返回double类型数值的方法

		表达式	execution(public double ArithmeticCalculator.*(double, ..))
		含义		第一个参数为double类型的方法。“..” 匹配任意数量、任意类型的参数。

		表达式	execution(public double ArithmeticCalculator.*(double, double))
		含义		参数类型为double，double类型的方法

		//在AspectJ中，切入点表达式可以通过 “&&”、“||”、“!”等操作符结合起来
		表达式	execution (* *.add(int,..)) || execution(* *.sub(int,..))
		含义		任意类中第一个参数为int类型的add方法或sub方法
		表达式	!execution (* *.add(int,..)) 
		含义		匹配不是任意类中第一个参数为int类型的add方法

**切入点表达式应用到实际的切面类中**

		@Aspect      //表示当前类是一个切面类
		public class LoggingAspect {
		
			//表示当前方法是一个前置通知->应用前置通知->一组目标方法
		    @Before("execution(public int com.spring.aop.poxy.ArithmeticCalculatorImpl.add(int,int))")//切入点表达式，指定作用到目标方法
		    public void beforeMethod()
		    {
		        System.out.println("LoggingAspect==> The method is "+methodName+");
		    }
		}

**当前连接点细节**

切入点表达式通常都会是从宏观上定位一组方法，和具体某个通知的注解结合起来就能够确定对应的连接点。那么就一个具体的连接点而言，我们可能会关心这个连接点的一些具体信息，例如：当前连接点所在方法的方法名、当前传入的参数值等等。这些信息都封装在JoinPoint接口的实例对象中。

**JoinPoint**

		 Object[] getArgs();	//获取实际参数数组
		 Signature getSignature();		//封装签名信息的对象,可以进一步获取方法名

**通知**

1. 在具体的连接点上要执行的操作
2. 一个切面可以包括一个或者多个通知
3. 通知所使用的注解的值往往是切入点表达式。

		/**
		 * 增加一个日志切面com.spring.aop.annotation.LoggingAspect
		 */
		@Component  //标识为一个组件
		@Aspect      //标识为一个切面
		public class LoggingAspect {
		}

		/**增加标识为组件,令Spring IOC可以扫描到**/
		@Component
		public class ArithmeticCalculatorImpl implements ArithmeticCalculator{
			.....
		}

	    private static void test1()
	    {
	        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("spring-aop-annotation.xml");
	        ArithmeticCalculator bean = con.getBean("arithmeticCalculatorImpl", ArithmeticCalculator.class);
	
	       System.out.println(bean.getClass().getName());	//通过打印bean的Class对象,com.sun.proxy.$Proxy10,可看到是一个动态代理,JDK的动态代理接口类型必须以父接口类型定义ArithmeticCalculator,兄弟关系是不能转换的
	        int result = bean.add(1, 2);
	        System.out.println("mian result is "+result);
	
			/*
	        int result2 = bean.div(5, 0);
	        System.out.println("Main Result: " + result2 );
			*/
	    }

		
	
**前置通知**

1. 前置通知：在方法执行之前执行的通知
2. 使用@Before注解

		/**
	     * 前置通知: 在目标方法(连接点)执行之前执行.
	     */
		/**当有异常时不会执行**/
	    @Before("execution(public int com.spring.aop.poxy.ArithmeticCalculatorImpl.add(int,int))")
	    public void beforeMethod(JoinPoint joinPoint)
	    {
	        //方法的名字
	        String methodName = joinPoint.getSignature().getName();
	        //参数
	        Object[] paramsArray = joinPoint.getArgs();
	
	        System.out.println("LoggingAspect==> The method is "+methodName+",params is "+ Arrays.asList(paramsArray));
			/*
			LoggingAspect==> The method is add,params is [1, 2]
			Main result is 3
			*/
	    }

**后置通知**

1. 后置通知是在连接点完成之后执行的，即连接点返回结果或者抛出异常的时候
2. 使用@After注解

		 /**
	     * 后置通知: 在目标方法执行之后执行， 不管目标方法有没有抛出异常.  不能获取方法的结果
	     *    * com.atguigu.spring.aspectJ.annotation.*.*(..)
	     *    * : 任意修饰符 任意返回值
	     *    * : 任意类
	     *    * : 任意方法
	     *    ..: 任意参数列表
	     *
	     * 连接点对象: JoinPoint
	     */
	    @After("execution(* com.spring.aop.poxy.*.*(..))")
	    public void afterMethod(JoinPoint joinPoint)
	    {
	        //方法的名字
	        String methodName = joinPoint.getSignature().getName();
	        System.out.println("LoggingAspect==> The method "+methodName+" is endding");

			/*
				LoggingAspect==> The method is add,params is [1, 2]
				LoggingAspect==> The method add is endding
				Main result is 3
			
			*/
	    }

**返回通知**

1. 无论连接点是正常返回还是抛出异常，后置通知都会执行。如果只想在连接点返回的时候记录日志，应使用返回通知代替后置通知
2. 使用@AfterReturning注解,在返回通知中访问连接点的返回值
	1. 在返回通知中，只要将returning属性添加到@AfterReturning注解中，就可以访问连接点的返回值。该属性的值即为用来传入返回值的参数名称
	2. 必须在通知方法的签名中添加一个同名参数。在运行时Spring AOP会通过这个参数传递返回值
	3. 原始的切点表达式需要出现在pointcut属性中

			 /**
		     * 返回通知: 在目标方法正常执行结束后执行.  可以获取到方法的返回值.
		     *
		     * 获取方法的返回值: 通过returning 来指定一个名字， 必须要与方法的一个形参名一致.
		     */
			/**当有异常时不会执行**/
		    @AfterReturning(value = "execution(* com.spring.aop.poxy.*.*(..))",returning = "result")
		    public void afterReturningMethod(JoinPoint joinPoint,Object result)
		    {
		        //方法的名字
		        String methodName = joinPoint.getSignature().getName();
		        System.out.println("LoggingAspect==> The method "+methodName+" is afterReturningMethod,return result is "+result);

				/**
				LoggingAspect==> The method is add,params is [1, 2]
				LoggingAspect==> The method add is endding
				LoggingAspect==> The method add is afterReturningMethod,return result is 3
				Main result is 3
				**/
		    }

**异常通知**

1. 只在连接点抛出异常时才执行异常通知
2. 将throwing属性添加到@AfterThrowing注解中，也可以访问连接点抛出的异常。Throwable是所有错误和异常类的顶级父类，所以在异常通知方法可以捕获到任何错误和异常。
3. 如果只对某种特殊的异常类型感兴趣，可以将参数声明为其他异常的参数类型。然后通知就只在抛出这个类型及其子类的异常时才被执行

    	/**
	     * 异常通知: 在目标方法抛出异常后执行.
	     *
	     * 获取方法的异常: 通过throwing来指定一个名字， 必须要与方法的一个形参名一致.
	     *
	     * 可以通过形参中异常的类型 来设置抛出指定异常才会执行异常通知.
	     *
	     */
		/**只有发生异常时才会通知，并且指定的形参的异常类型包含且属于错误异常**/
	    @AfterThrowing(value = "execution(* com.spring.aop.poxy.*.*(..))",throwing = "ex")
	    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex)
	    {
	        String methodName = joinPoint.getSignature().getName();
	        System.out.println("LoggingAspect==> The method "+methodName+" is afterThrowingMethod,throw exception is "+ex);
			/*
			LoggingAspect==> The method div is endding
			LoggingAspect==> The method div is afterThrowingMethod,throw exception is java.lang.ArithmeticException: / by zero
			*/
	    }
			
**环绕通知**

1. 环绕通知是所有通知类型中功能最为强大的，能够全面地控制连接点，甚至可以控制是否执行连接点
2. 对于环绕通知来说，连接点的参数类型必须是ProceedingJoinPoint。它是 JoinPoint的子接口，允许控制何时执行，是否执行连接点
3. 在环绕通知中需要明确调用ProceedingJoinPoint的proceed()方法来执行被代理的方法。如果忘记这样做就会导致通知被执行了，但目标方法没有被执行
4. 注意：环绕通知的方法需要返回目标方法执行之后的结果，即调用 joinPoint.proceed();的返回值，否则会出现空指针异常

	    /**
	     * 环绕通知: 环绕着目标方法执行. 可以理解是 前置 后置 返回  异常 通知的结合体，更像是动态代理的整个过程.
	     */
	    @Around("execution(* com.spring.aop.poxy.*.*(..))")
	    public Object roundMethod(ProceedingJoinPoint pjp)
	    {
	        //执行目标方法
	        try {
	            //前置
	            String name = pjp.getSignature().getName();
	            System.out.println("round before is "+name);
	
	            //返回
	            Object result = pjp.proceed();
	            System.out.println("round return is "+ result);
	            return result;
	        }catch (Throwable e){
	            //异常通知
	            e.printStackTrace();
	        }finally {
	            // 后置
	            System.out.println("round endding!");
	        }
	        return null;

			/**
			round before is add
			LoggingAspect==> The method is add,params is [1, 2]
			round return is 3
			round endding!
			LoggingAspect==> The method add is endding
			LoggingAspect==> The method add is afterReturningMethod,return result is 3
			**/
	    }

**重用切入点定义**

1. 在编写AspectJ切面时，可以直接在通知注解中书写切入点表达式。但同一个切点表达式可能会在多个通知中重复出现
2. 在AspectJ切面中，可以通过@Pointcut注解将一个切入点声明成简单的方法。切入点的方法体通常是空的，因为将切入点定义与应用程序逻辑混在一起是不合理的
3. 切入点方法的访问控制符同时也控制着这个切入点的可见性。如果切入点要在多个切面中共用，最好将它们集中在一个公共的类中。在这种情况下，它们必须被声明为public。在引入这个切入点时，必须将类名也包括在内。如果类没有与这个切面放在同一个包中，还必须包含包名
4. 其他通知可以通过方法名称引入该切入点

		/**
		 * 声明切入点表达式
		 */
		 @Pointcut("execution(* com.spring.aop.poxy.*.*(..))")
	    public void declarePointCut(){}

		原有的@After("execution(* com.spring.aop.poxy.*.*(..))")
		替换成@After("declarePointCut()")

		原有的//@AfterReturning(value = "execution(* com.spring.aop.poxy.*.*(..))",returning = "result")
		替换成@AfterReturning(value = "declarePointCut()", returning = "result")

		//在ValidationAspect类使用时
		原有的//@Before("execution(* com.spring.aop.poxy.*.*(..))")
		@Before("LoggingAspect.declarePointCut()")

**指定切面的优先级**

1. 在同一个连接点上应用不止一个切面时，除非明确指定，否则它们的优先级是不确定的
2. 切面的优先级可以通过实现Ordered接口或利用@Order注解指定
3. 实现Ordered接口，getOrder()方法的返回值越小，优先级越高
4. 若使用@Order注解，序号出现在注解中

		/**
		 * 验证切面
		 *
		 */
		@Component
		@Aspect
		@Order(1)	//设置切面的优先级,默认是int的最大值2147483647 
		public class ValidationAspect {
		
		    @Before("execution(* com.spring.aop.poxy.*.*(..))")
		    public void beforeMethod(JoinPoint joinPoint)
		    {
		        String methodName = joinPoint.getSignature().getName();
		
		        Object[] args = joinPoint.getArgs();
		
		        System.out.println("ValidationAspect==> The method "+methodName + "begin with " + Arrays.asList(args) );
		    }
		
		}

		
		/**
		 * 日志切面
		 */
		@Component  //标识为一个组件
		@Aspect      //标识为一个切面
		@Order(2)
		public class LoggingAspect {
			.....
		}

		//没设置@Order前的执行顺序
		/**
			com.sun.proxy.$Proxy11
			round before is add
			LoggingAspect==> The method is add,params is [1, 2]
			ValidationAspect==> The method addbegin with [1, 2]
		**/

		//设置@Order后
		/**
		com.sun.proxy.$Proxy12
		ValidationAspect==> The method addbegin with [1, 2]
		round before is add
		LoggingAspect==> The method is add,params is [1, 2]
		round return is 3
		round endding!
		LoggingAspect==> The method add is endding
		LoggingAspect==> The method add is afterReturningMethod,return result is 3
		Main result is 3
		**/

# 以XML方式配置切面 #

除了使用AspectJ注解声明切面，Spring也支持在bean配置文件中声明切面。这种声明是通过aop名称空间中的XML元素完成的。

正常情况下，基于注解的声明要优先于基于XML的声明。通过AspectJ注解，切面可以与AspectJ兼容，而基于XML的配置则是Spring专有的。由于AspectJ得到越来越多的 AOP框架支持，所以以注解风格编写的切面将会有更多重用的机会。

**配置细节**

在bean配置文件中，所有的Spring AOP配置都必须定义在<aop:config>元素内部。对于每个切面而言，都要创建一个<aop:aspect>元素来为具体的切面实现引用后端bean实例。
切面bean必须有一个标识符，供<aop:aspect>元素引用

**声明切入点**

1. 切入点使用<aop:pointcut>元素声明
2. 切入点必须定义在<aop:aspect>元素下，或者直接定义在<aop:config>元素下
	1.  定义在<aop:aspect>元素下：只对当前切面有效
	2.  定义在<aop:config>元素下：对所有切面都有效
3. 基于XML的AOP配置不允许在切入点表达式中用名称引用其他切入点

**声明通知**

1.在aop名称空间中，每种通知类型都对应一个特定的XML元素

2.通知元素需要使用<pointcut-ref>来引用切入点，或用<pointcut>直接嵌入切入点表达式

3.method属性指定切面类中通知方法的名称

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xmlns:aop="http://www.springframework.org/schema/aop"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">
	
	    <!-- 目标对象 -->
	    <bean id="arithmeticCalculatorImpl" class="com.spring.aop.xml.ArithmeticCalculatorImpl"/>
	
	    <!-- 切面 -->
	    <bean id="loggingAspect" class="com.spring.aop.xml.LoggingAspect"/>
	    <bean id="validationAspect" class="com.spring.aop.xml.ValidationAspect"/>
	
	    <!-- AOP: 切面  通知  切入点表达式 -->
	    <aop:config>
	        <!-- 切入点表达式 -->
	        <aop:pointcut id="myPointcut" expression="execution(* com.spring.aop.xml.*.*(..))"/>
	
	        <!-- 切面 -->
	        <aop:aspect ref="loggingAspect">
	
	            <!-- 通知 -->
	            <aop:before method="beforeMethod" pointcut-ref="myPointcut"/>
	            <aop:after method="afterMethod" pointcut-ref="myPointcut"/>
	            <aop:after-returning method="afterReturningMethod" pointcut-ref="myPointcut" returning="result"/>
	            <aop:after-throwing method="afterThrowingMethod" pointcut-ref="myPointcut" throwing="ex"/>
	            <aop:around method="roundMethod" pointcut-ref="myPointcut"/>
	        </aop:aspect>
	
	        <aop:aspect ref="validationAspect">
	            <aop:before method="beforeMethod" pointcut-ref="myPointcut"/>
	        </aop:aspect>
	    </aop:config>
	</beans>

# JdbcTemplate #

为了使JDBC更加易于使用，Spring在JDBC API上定义了一个抽象层，以此建立一个JDBC存取框架。 

作为Spring JDBC框架的核心，JDBC模板的设计目的是为不同类型的JDBC操作提供模板方法，通过这种方式，可以在尽可能保留灵活性的情况下，将数据库存取的工作量降到最低。 
	
可以将Spring的JdbcTemplate看作是一个小型的轻量级持久化层框架，和我们之前使用过的DBUtils风格非常接近。

**导入JAR包**

JdbcTemplate所需要的JAR包：

- spring-jdbc-4.3.18.RELEASE.jar
- spring-orm-4.3.18.RELEASE.jar
- spring-tx-4.3.18.RELEASE.jar

**在Spring配置文件中配置相关的bean**

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
	
	    <context:property-placeholder location="classpath:config/db.properties"/>
	
		<!-- 数据源 -->
	    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	        <property name="driverClass"  value="${jdbc.driverClass}"/>
	        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
	        <property name="user" value="${jdbc.user}"/>
	        <property name="password" value="${jdbc.password}"/>
	    </bean>
	
		<!-- JdbcTemplate -->
	    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	        <property name="dataSource" ref="dataSource" />
	    </bean>
	</beans>


**持久化操作**

1. 增删改	

		JdbcTemplate.update(String, Object...)
		
		private static void test1()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
	        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
	        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( ? , ? ,?)";
	        //jdbcTemplate.update(sql,"李四","test@test.com",1);
	        jdbcTemplate.update(sql,new Object[]{"张三","test2@test.com",2});
	    }
2. 批量增删改

		JdbcTemplate.batchUpdate(String, List<Object[]>)
		//Object[]封装了SQL语句每一次执行时所需要的参数
		//List集合封装了SQL语句多次执行时的所有参数
		
	  	private static void test2()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
	        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
	        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( ? , ? ,?)";
	
	        ArrayList<Object[]> list = new ArrayList<Object[]>();
	        list.add(new Object[]{"苍老师","changlaoshi@gmail.com",2});
	        list.add(new Object[]{"三上老师","sanshang@gmail.com",2});
	        list.add(new Object[]{"Julia","julia@gmial.com",2});
	        jdbcTemplate.batchUpdate(sql,list);
	    }
3. 查询单行

		JdbcTemplate.queryForObject(String, RowMapper<Department>, Object...)

		/**参数RowMapper是接口，用它的子类BeanPropertyRowMapper**/
		 private static void test3()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
	        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
	        String sql = "SELECT id,last_name,email,gender From tbl_employee Where id = ?";
	
	        //rowMapper: 行映射  将结果集的一条数据映射成具体的一个java对象. 
	        RowMapper<Employee> mapper = new BeanPropertyRowMapper<Employee>(Employee.class);
	
	        Employee employee = jdbcTemplate.queryForObject(sql, mapper, 5);
	        System.out.println(employee);
	    }

4. 查询多行

		JdbcTemplate.query(String, RowMapper<Department>, Object...)
		//RowMapper对象依然可以使用BeanPropertyRowMapper

		 private static void test5()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
	        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
	        String sql = "SELECT id,last_name,email,gender FROM tbl_employee";
	
	        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
	        List<Employee> employeeList = jdbcTemplate.query(sql, rowMapper);
	        System.out.println(employeeList);
	    }
5. 查询单一值

		JdbcTemplate.queryForObject(String, Class, Object...)

		private static void test4()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
	        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
	        String sql = "SELECT COUNT(id) FROM tbl_employee";
	
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
	        System.out.println(count);
	    }

**使用具名参数的JdbcTemplate**

1. 通过IOC容器创建NamedParameterJdbcTemplate对象

    <!-- NamedParameterJdbcTemplate -->
    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
		<!-- 没有无参构造器，必须传入数据源或JdbcTemplate对象 -->
        <constructor-arg ref="dataSource"/>
    </bean>

2. 具名参数在SQL语句中的格式

	INSERT INTO depts (dept_name) VALUES (:deptName)

3. 具名参数传入
	1. 通过Map对象传入
		
			 NamedParameterJdbcTemplate.update(String sql, Map<String, ?> map)
			//Map的键是参数名，值是参数值

			private static void test6()
		    {
		        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
		        npjt = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
		        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :ln , :e ,:g)";
		
		        HashMap<String, Object> map = new HashMap<String, Object>();
		        map.put("ln","王五");
		        map.put("e","wangwu@test.com");
		        map.put("g","1");
		
		        npjt.update(sql,map);
		    }

	2. 通过SqlParameterSource对象传入

				/**SqlParameterSource参数类型是接口，使用它的子类BeanPropertySqlParameterSource**/
			   	private static void test7()
			    {
			        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
			        npjt = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
			        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :lastName , :email ,:gender)";
			
			        Employee employee = new Employee("赵六", "zhaoliu@test.com", 1);
			
			        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);
			
			        npjt.update(sql,parameterSource);
			    }

**使用JdbcTemplate实现Dao**

通过IOC容器自动注入

**JdbcTemplate类是线程安全的**，所以可以在IOC容器中声明它的单个实例，并将这个实例注入到所有的Dao实例中

	@Repository
	public class EmployeeDao {
	
	    @Autowired
	    private  JdbcTemplate jdbcTemplate;
	
	    @Autowired
	    private  NamedParameterJdbcTemplate npjt;
	
	    public void insertEmployee(Employee employee)
	    {
	        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :lastName , :email ,:gender)";
	        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);
	        npjt.update(sql,parameterSource);
	    }
	}

# 声明式事务管理 #

**事务概述**

1. 在JavaEE企业级开发的应用领域，为了保证数据的**完整性** 和 **一致性** ，必须引入数据库事务的概念，所以事务管理是企业级应用程序开发中必不可少的技术
2. 事务就是一组由于逻辑上紧密关联而合并成一个整体(工作单元)的多个数据库操作，这些操作 **要么都执行，要么都不执行**
3. 事务的四个关键属性(ACID)
	1. **原子性**(atomicity)：“原子”的本意是“**不可再分**”，事务的原子性表现为一个事务中涉及到的多个操作在逻辑上缺一不可。事务的原子性要求事务中的所有操作要么都执行，要么都不执行
	2. **一致性**(consistency)：“一致”指的是数据的一致，具体是指：所有数据都处于**满足业务规则的一致性状态**。一致性原则要求：一个事务中不管涉及到多少个操作，都必须保证事务执行之前数据是正确的，事务执行之后数据仍然是正确的。如果一个事务在执行的过程中，其中某一个或某几个操作失败了，则必须将其他所有操作撤销，将数据恢复到事务执行之前的状态，这就是回滚。
	3. **隔离性**(isolation)：在应用程序实际运行过程中，事务往往是并发执行的，所以很有可能有许多事务同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏。隔离性原则要求多个事务在**并发执行过程中不会互相干扰**
	4. **持久性**(durability)：持久性原则要求事务执行完成后，对数据的修改**永久的保存**下来，不会因各种系统错误或其他意外情况而受到影响。通常情况下，事务对数据的修改应该被写入到持久化存储器中

**Spring事务管理**

**编程式事务管理**

1. 使用原生的JDBC API进行事务管理
	1. 获取数据库连接Connection对象
	2. 取消事务的自动提交
	3. 执行操作
	4. 正常完成操作时手动提交事务
	5. 执行失败时回滚事务
	6. 关闭相关资源
2. 评价

使用原生的**JDBC API**实现事务管理是所有事务管理方式的基石，同时也是最典型	的编程式事务管理。编程式事务管理需要将事务管理代码**嵌入到业务方法中** 来控制事务	的提交和回滚。在使用编程的方式管理事务时，必须在每个事务操作中包含额外的事务管理代码。相对于**核心业务**而言，事务管理的代码显然属于**非核心业务** ，如果多个模块都使用同样模式的代码进行事务管理，显然会造成较大程度的**代码冗余**。

**声明式事务管理**

大多数情况下声明式事务比编程式事务管理更好：它将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。

事务管理代码的固定模式作为一种横切关注点，可以通过AOP方法模块化，进而借助Spring AOP框架实现声明式事务管理。

Spring在不同的事务管理API之上定义了一个抽象层，通过配置的方式使其生效，从而让应用程序开发人员不必了解事务管理API的底层实现细节，就可以使用Spring的事务管理机制。

Spring既支持编程式事务管理，也支持声明式的事务管理。

**Spring提供的事务管理器**

Spring从不同的事务管理API中抽象出了一整套事务管理机制，让事务管理代码从特定的事务技术中独立出来。开发人员通过配置的方式进行事务管理，而不必了解其底层是如何实现的。

Spring的核心事务管理抽象是PlatformTransactionManager。它为事务管理封装了一组独立于技术的方法。无论使用Spring的哪种事务管理策略(编程式或声明式)，事务管理器都是必须的。

事务管理器可以以普通的bean的形式声明在Spring IOC容器中。

**事务管理器的主要实现**

1. DataSourceTransactionManager：在应用程序中只需要处理一个数据源，而且通过JDBC存取。
2. JtaTransactionManager：在JavaEE应用服务器上用JTA(Java Transaction API)进行事务管理
3. HibernateTransactionManager：用Hibernate框架存取数据库

spring-tx.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
		       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		       xmlns:context="http://www.springframework.org/schema/context"
		       xmlns:tx="http://www.springframework.org/schema/tx"
		       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">
		
		    <context:property-placeholder location="classpath:config/db.properties"/>
		
		    <context:component-scan base-package="com.spring.tx"/>
		
		    <!-- 数据源 -->
		    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		        <property name="driverClass"  value="${jdbc.driverClass}"/>
		        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
		        <property name="user" value="${jdbc.user}"/>
		        <property name="password" value="${jdbc.password}"/>
		    </bean>
		
		    <!-- JdbcTemplate -->
		    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		        <property name="dataSource" ref="dataSource" />
		    </bean>
		
		    <!-- NamedParameterJdbcTemplate -->
		    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
		        <!-- 没有无参构造器，必须传入数据源或JdbcTemplate对象 -->
		        <constructor-arg ref="dataSource"/>
		    </bean>
		
		    <!-- 事务管理器 -->
		    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		        <property name="dataSource" ref="dataSource"/>
		    </bean>
		
		    <!-- 开启事务注解
				transaction-manager 用来指定事务管理器， 如果事务管理器的id值 是 transactionManager，
				                                               可以省略不进行指定。
			-->
		    <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
		</beans>

Dao

		public interface BookShopDao {
		
		    //根据书号查询书的价格
		    public int findPriceByIsbn(String isbn );
		
		    //更新书的库存
		
		    public void  updateStock(String isbn);
		
		    //更新用户的余额
		
		    public void  updateUserAccount(String username,Integer price);
		}

		@Repository
		public class BookShopDaoImpl implements BookShopDao {
		
		    @Autowired
		    private JdbcTemplate jdbcTemplate;
		
		    @Override
		    public int findPriceByIsbn(String isbn) {
		        String sql =" SELECT price FROM book WHERE isbn = ?";
		        Integer price = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
		        return price;
		    }
		
		    @Override
		    public void updateStock(String isbn) {
		
		        //判断库存是否足够
		        String sql = "SELECT stock FROM book_stock WHERE isbn = ?";
		        Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
		        if (stock <= 0)
		        {
		            throw new BookStockException("库存不足.....");
		        }
		
		        String updateSql = "UPDATE book_stock SET stock = stock -1 WHERE isbn = ?";
		        jdbcTemplate.update(updateSql,isbn);
		    }
		
		    @Override
		    public void updateUserAccount(String username, Integer price) {
		        //判断余额是否足够
		        String sql ="SELECT balance FROM account WHERE username = ?";
		        Integer balance = jdbcTemplate.queryForObject(sql, Integer.class, username);
		
		        if (balance < price)
		        {
		            throw new UserAccountException("余额不足......");
		        }
		        String updateSql = "UPDATE account SET balance = balance - ? WHERE username = ?";
		        jdbcTemplate.update(updateSql,price,username);
		    }
		}

Service

		public interface BookShopService {
		    public void buyBook(String username,String isbn);
		}

		@Transactional       //对当前类中所有的方法都起作用
		@Service
		public class BookShopServiceImpl implements BookShopService{
		
		    @Autowired
		    private BookShopDao bookShopDao;
		
		
		    @Override
		    //@Transactional      //只对当前的方法起作用
		    public void buyBook(String username, String isbn) {
		        int price = bookShopDao.findPriceByIsbn(isbn);
		        bookShopDao.updateStock(isbn);
		        bookShopDao.updateUserAccount(username,price);
		    }
		}

Main

		//当加了@Transactional注解后，通过@Service查找会变成代理模式进行AOP
		//反之打印只是一个bookShopServiceImpl bean类型
	  	 private static void test1()
	    {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx.xml");
	        BookShopService bean = context.getBean("bookShopServiceImpl", BookShopService.class);
	        System.out.println(bean.getClass().getName());		//com.sun.proxy.$Proxy14
	        bean.buyBook("Tom","1001");
	    }

**事务的传播行为**

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行。

事务的传播行为可以由传播属性指定。Spring定义了7种类传播行为

	REQUIRED		//如果有事务在运行，当前的方法就在这个事务内运行，否则，就启动一个新的事务，并在自己的事务内运行
	REQUIRED_NEW	//当前的方法必须启动新事务,并在它自己的事务内运行.如果有事务正在运行,应该将它挂起
	SUPPORTS		//如果有事务在运行,当前的方法就在这个事务内运行.否则它可以不运行在事务中
	NOT_SUPPORTE	//当前的方法不应该运行在事务中,如果有运行的事务,将它挂起
	MANDATORY		//当前的方法必须运行在事务内部,如果没有正在运行的事务,就抛出异常
	NEVER			//当前的方法不应该运行在事务中,如果有运行的事务,就抛出异常
	NESTED			//如果有事务在运行,当前的方法就应该在这个事务的嵌套事务内运行.否则,就启动一个新的事务,并在它自己的事务内运行

事务传播属性可以在@Transactional注解的propagation属性中定义。

测试

	public interface Cashier {

	    public void purchase(String username, List<String> isbns);
	}

	@Service
	public class CashierImpl implements Cashier{
	
	    @Autowired
	    private BookShopService bookShopService;
	
	    @Override
	    @Transactional
	    public void purchase(String username, List<String> isbns) {
	
	        for (String isbn : isbns)
	        {
	            bookShopService.buyBook(username,isbn);
	        }
	    }
	}



1. **REQUIRED传播行为(默认,整个事务流程只有唯一事务，要么全部成功，要么全部失败,不受其它事务影响)**

	当bookService的buyBook()方法被另一个事务方法purchase()调用时，它默认会在现有的事务内运行。这个默认的传播行为就是REQUIRED。因此在purchase()方法的开始和终止边界内只有一个事务。这个事务只在purchase()方法结束的时候被提交，结果用户一本书都买不了

	![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/6.png)

2. **REQUIRES_NEW传播行为(在事务1执行过程中,如果有事务2,事务1会先挂起，等事务2执行完再执行事务1,之后的事务3,4,5...也一样，事务1挂起,执行完再执行事务1)**

	表示该方法必须启动一个新事务，并在自己的事务内运行。如果有事务在运行，就应该先挂起它

![](https://github.com/DragonChilde/MarkdownPhotos/blob/master/photos/7.png)

		@Service
		public class BookShopServiceImpl implements BookShopService{
		
		    @Autowired
		    private BookShopDao bookShopDao;
		
		
		    /**
		     * 事务属性:
		     * 	 1. 事务的传播行为 propagation: 一个事务方法被另外一个事务方法调用时，当前的事务如何使用事务.
		     * 			Propagation.REQUIRED  默认值.  使用调用者的事务.
		     * 			Propagation.REQUIRES_NEW     将调用者的事务挂起, 重新开启事务来使用.
		     */
		    @Override
		    @Transactional(propagation = Propagation.REQUIRES_NEW)      //只对当前的方法起作用
		    public void buyBook(String username, String isbn) {
		        int price = bookShopDao.findPriceByIsbn(isbn);
		        bookShopDao.updateStock(isbn);
		        bookShopDao.updateUserAccount(username,price);
		    }
		}

**事务的隔离级别isolation**

**数据库事务并发问题**

假设现在有两个事务：Transaction01和Transaction02并发执行

1. 脏读(可重复读)
	1. Transaction01将某条记录的AGE值从20修改为30
	2. ransaction02读取了Transaction01更新后的值：30。
	3. Transaction01回滚，AGE值恢复到了20。
	4. Transaction02读取到的30就是一个无效的值

2. 不可重复读(针对update操作)
	1. Transaction01读取了AGE值为20。
	2. Transaction02将AGE值修改为30。
	3. Transaction01再次读取AGE值为30，和第一次读取不一致。

3. 幻读(针对add操作)
	1. Transaction01读取了STUDENT表中的一部分数据。
	2. Transaction02向STUDENT表中插入了新的行。
	3. Transaction01读取了STUDENT表时，多出了一些行


**隔离级别**

数据库系统必须具有隔离并发运行各个事务的能力，使它们不会相互影响，避免各种并发问题。**一个事务与其他事务隔离的程度称为隔离级别**。SQL标准中规定了多种事务隔离级别，不同隔离级别对应不同的干扰程度，隔离级别越高，数据一致性就越好，但并发性越弱

1. **读未提交(脏读)**：READ UNCOMMITTED

	允许Transaction01读取Transaction02未提交的修改
2. **读已提交(不可重复读)(常用)**：READ COMMITTED

	 要求Transaction01只能读取Transaction02已提交的修改
3. **可重复读(幻读)(MYSQL默认)**：REPEATABLE READ

	确保Transaction01可以多次从一个字段中读取到相同的值，即Transaction01执行期间禁止其它事务对这个字段进行更新
4. **串行化(效率低)(排队)**：SERIALIZABLE

	确保Transaction01可以多次从一个表中读取到相同的行，在Transaction01执行期间，禁止其它事务对这个表进行添加、更新、删除操作。可以避免任何并发问题，但性能十分低下
5. 各个隔离级别解决并发问题的能力见下表

							脏读		不可重复读	幻读
		READ UNCOMMITTED	有		有			有
		READ COMMITTED		无		有			有
		REPEATABLE READ		无		无			有
		SERIALIZABLE		无		无			无
6. 各种数据库产品对事务隔离级别的支持程度

							Oracle		MySQL
		READ UNCOMMITTED			×		√
		READ COMMITTED			√(默认)	√
		REPEATABLE READ				×		√(默认)
		SERIALIZABLE			√		√

**在Spring中指定事务隔离级别**

1.**注解**

用@Transactional注解声明式地管理事务时可以在@Transactional的isolation属性中设置隔离级别

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED) 
    public void buyBook(String username, String isbn) {
		.....
    }

**触发事务回滚的异常**

**默认情况**

捕获到RuntimeException或Error时回滚，而捕获到编译时异常不回滚

**设置途经**

1. 注解@Transactional注解
	1. rollbackFor属性(class类型)：指定遇到时必须进行回滚的异常类型，可以为多个
	2. noRollbackFor属性(class类型)：指定遇到时不回滚的异常类型，可以为多个

		    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED,noRollbackFor = {UserAccountException.class})     
		    public void buyBook(String username, String isbn) {
				....
		    }

**事务的超时和只读属性**

由于事务可以在行和表上获得锁，因此长事务会占用资源，并对整体性能产生影响。

如果一个事务只读取数据但不做修改，数据库引擎可以对这个事务进行优化。

- 超时事务属性：事务在强制回滚之前可以保持多久。这样可以防止长期运行的事务占用资源。
- 只读事务属性: 表示这个事务只读取数据但不更新数据, 这样可以帮助数据库引擎优化事务。

事务的只读设置:

	readOnly
		true:  只读     代表着只会对数据库进行读取操作， 不会有修改的操作.
		如果确保当前的事务只有读取操作，就有必要设置为只读，可以帮助数据库引擎优化事务
	
		false: 非只读   不仅会读取数据还会有修改操作。

事务的超时设置:  设置事务在强制回滚之前可以占用的时间.

	timeout:

1. 注解

		@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED,noRollbackFor = {UserAccountException.class},readOnly = false,timeout = 3)
	    public void buyBook(String username, String isbn) {
			...
	    }

**Spring事务的XML方式**

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xmlns:tx="http://www.springframework.org/schema/tx"
	       xmlns:aop="http://www.springframework.org/schema/aop"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">
	
	    <context:property-placeholder location="classpath:config/db.properties"/>
	
	    <context:component-scan base-package="com.spring.tx.xml"/>
	
	    <!-- 数据源 -->
	    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	        <property name="driverClass"  value="${jdbc.driverClass}"/>
	        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
	        <property name="user" value="${jdbc.user}"/>
	        <property name="password" value="${jdbc.password}"/>
	    </bean>
	
	    <!-- JdbcTemplate -->
	    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	        <property name="dataSource" ref="dataSource" />
	    </bean>
	
	    <!-- NamedParameterJdbcTemplate -->
	    <bean id="namedParameterJdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
	        <!-- 没有无参构造器，必须传入数据源或JdbcTemplate对象 -->
	        <constructor-arg ref="dataSource"/>
	    </bean>
	
	    <!-- 事务管理器 -->
	    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	        <property name="dataSource" ref="dataSource"/>
	    </bean>
	
	    <!-- 基于xml配置事务管理    事务管理器   事务属性设置 -->
	    <tx:advice transaction-manager="dataSourceTransactionManager" id="txAdvice">
	        <!-- 配置事务属性 -->
	        <tx:attributes>
	            <!-- 具体的方法使用的事务属性 -->
	            <tx:method name="buyBook" isolation="READ_COMMITTED" propagation="REQUIRES_NEW" read-only="false" rollback-for="UserAccountException"/>
	            <tx:method name="purchase"/>
	
	            <!-- 约定方法的名字 -->
	            <!-- 查询操作: selectxxx  selectEmployee  selectUser -->
	            <tx:method name="select*" read-only="true"/>
	            <!-- 修改操作: updatexxx -->
	            <tx:method name="update*" />
	
	            <!-- 除了上述指定的方法之外的所有方法 -->
	            <tx:method name="*"/>
	        </tx:attributes>
	    </tx:advice>
	
	    <aop:config>
	        <!-- 切入点表达式 -->
	        <aop:pointcut id="txPointCut" expression="execution(* com.spring.tx.xml.server.*.*(..))"/>
	        <!-- 切入点表达式  与 事务配置的结合(不配置事务是不会生效的) -->
	        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
	    </aop:config>
	</beans>