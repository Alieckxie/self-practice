package com.alieckxie.self.functiondetail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CollectionTest {

	@Test
	public void testSetAddNull(){
		Set<String> set = new HashSet<String>();
		set.add(null);
		set.add(null);
		set.add(null);
		set.add(null);
		System.out.println(set.size());
		System.out.println(set);
		set.remove(null);
		System.out.println(set.size());
		System.out.println(set);
	}
	
}
