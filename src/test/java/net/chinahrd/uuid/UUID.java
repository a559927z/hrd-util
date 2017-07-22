package net.chinahrd.uuid;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.chinahrd.utils.Identities;

public class UUID {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void uuid2() {
		// 201701011601716011
		// 201701011601404
		for (int i = 0; i < 10; i++) {
			String uuid = Identities.uuid2();
			System.out.println(uuid);
		}
	}
	@Ignore
	@Test
	public void getOrderNo() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Identities.getOrderNo());
		}
		System.out.println();

	}

}
