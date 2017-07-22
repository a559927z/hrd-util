package net.chinahrd.guava;

import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MultiMapTest {

	@Ignore
	@Test
	// 一种key可以重复的map，子类有ListMultimap和SetMultimap，对应的通过key分别得到list和set
	public void  arrayListMultimapTest() {
		Multimap<String, Person> customersByType = ArrayListMultimap.create();
		customersByType.put("abc", new Person(1, 1, "a", "46546", 1, 20));
		customersByType.put("abc", new Person(1, 1, "a", "46546", 1, 30));
		customersByType.put("abc", new Person(1, 1, "a", "46546", 1, 40));
		customersByType.put("abc", new Person(1, 1, "a", "46546", 1, 50));
		customersByType.put("abcd", new Person(1, 1, "a", "46546", 1, 50));
		customersByType.put("abcde", new Person(1, 1, "a", "46546", 1, 50));
		Collection<Person> collection = customersByType.get("abc");
		
		for (Person person : collection) {
			System.out.println(person.getAge());
		}
	}

	class Person {
		private int id;
		private int showIndex;
		private String name;
		private String empNo;
		private int sex;
		private int age;

		public Person() {
			super();
		}

		public Person(int id, int showIndex, String name, String empNo, int sex, int age) {
			super();
			this.id = id;
			this.showIndex = showIndex;
			this.name = name;
			this.empNo = empNo;
			this.sex = sex;
			this.age = age;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getShowIndex() {
			return showIndex;
		}

		public void setShowIndex(int showIndex) {
			this.showIndex = showIndex;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmpNo() {
			return empNo;
		}

		public void setEmpNo(String empNo) {
			this.empNo = empNo;
		}

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}
}
