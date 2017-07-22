package net.chinahrd.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.After;

/**
 * 
 * @author jxzhang
 *
 */
public class ParameterizedTypeTest {
	public static void main(String[] args) throws NoSuchMethodException {
		new Sub();
	}
}

class Parent {

}

class Child extends Parent {

}

class UpperGeneric {
	static class Generic<T, R> {
		T t;
		R r;

		protected Generic(T t, R r) {
			this.t = t;
			this.r = r;
		}

		@After
		void foo() {
			System.err.println(t + " " + r);
		}
	}
}

class Sub extends UpperGeneric.Generic<Child, Integer> {

	protected Sub() {
		super(new Child(), 1);
		Class<?> clazz = getClass().getSuperclass();
		// class net.chinahrd.reflect.UpperGeneric$Generic
		System.err.println("super clazz:" + clazz);

		// net.chinahrd.reflect.UpperGeneric.net.chinahrd.reflect.UpperGeneric$Generic
		// <net.chinahrd.reflect.Child, java.lang.Integer>
		Type type = getClass().getGenericSuperclass();
		System.err.println("generic super class type:" + type);

		// class net.chinahrd.reflect.Child
		Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
		System.err.println("generic super class type:" + trueType);

		// class java.lang.Integer
		trueType = ((ParameterizedType) type).getActualTypeArguments()[1];
		System.err.println("type:" + trueType);

		// class net.chinahrd.reflect.UpperGeneric$Generic
		trueType = ((ParameterizedType) type).getRawType();
		System.err.println("raw type:" + trueType);

		// net.chinahrd.reflect.UpperGeneric
		trueType = ((ParameterizedType) type).getOwnerType();
		System.err.println("owner type:" + trueType);
	}
}