package uebung3;

import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitTest {

	@Test
	public void test() {
		BinaeryHashTree<Object, Object> tree = 
				new BinaeryHashTree<Object, Object>();
		tree.put(5, "fuenf");
		tree.put(8, "acht");
		tree.put(4, "vier");
		tree.put(7, "sieben");
		tree.put(6, "sechs");
		tree.put(3, "drei");
		tree.put(10, "zehn");
		
		BinaeryHashTree<Object, Object> baum = 
				new BinaeryHashTree<Object, Object>();
		
		//containsKey
		assertEquals(true, tree.containsKey(5));
		assertEquals(true, tree.containsKey(3));
		assertEquals(false, tree.containsKey(2));
		assertEquals(false, tree.containsKey(11));
		
		//containsValue
		assertEquals(true, tree.containsValue("acht"));
		assertEquals(true, tree.containsValue("drei"));
		assertEquals(false, tree.containsValue("eins"));
		assertEquals(false, tree.containsValue("zwei"));
		
		//get
		assertEquals("fuenf", tree.get(5));
		assertEquals("drei", tree.get(3));
		assertNotEquals("eins", tree.get(10));
		assertNotEquals("zwei", tree.get(6));
		
		//isEmpty
		assertEquals(false, tree.isEmpty());
		assertEquals(true, baum.isEmpty());
		
		baum.put("Miller", "Lisa");
		baum.put("Schweizer", "Jochen");
		baum.put("Wagner", "Valerian");
		
		//putAll
		tree.putAll(baum);
		assertEquals(true, tree.containsKey("Miller"));
		assertEquals(true, tree.containsValue("Valerian"));
		
		//remove
		tree.put(11, "elf");
		assertEquals(true, tree.containsKey(11));
		tree.remove(11);
		assertEquals(false, tree.containsKey(11));
		
		//size
		BinaeryHashTree<String, String> leer = 
				new BinaeryHashTree<String, String>();
		assertEquals(0, leer.size());
		assertEquals(10, tree.size());
		assertEquals(3, baum.size());
		
		//update
		tree.update("Miller", "Elisabeth");
		tree.update(4, "four");
		
	}

}
