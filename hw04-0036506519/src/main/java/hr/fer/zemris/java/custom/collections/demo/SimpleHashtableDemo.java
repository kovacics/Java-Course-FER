package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

import java.util.Iterator;


/**
 * Demo class for testing {@link SimpleHashtable} class.
 *
 * @author Stjepan Kovačić
 */
public class SimpleHashtableDemo {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

//		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(5);
//		
//		
//		System.out.printf("Size: %d   Capacity: %d     Slots full: %d\t %s%n", 
//				examMarks.size(), examMarks.getCapacity(), examMarks.slotsFull, examMarks);
//		examMarks.put("Ivana", 5);
//		System.out.printf("Size: %d   Capacity: %d     Slots full: %d\t %s%n", 
//				examMarks.size(), examMarks.getCapacity(), examMarks.slotsFull, examMarks);
//		examMarks.put("Ante", 2);
//		System.out.printf("Size: %d   Capacity: %d     Slots full: %d\t %s%n", 
//				examMarks.size(), examMarks.getCapacity(), examMarks.slotsFull, examMarks);
//		examMarks.put("Jasna", 2);
//		System.out.printf("Size: %d   Capacity: %d     Slots full: %d\t %s%n", 
//				examMarks.size(), examMarks.getCapacity(), examMarks.slotsFull, examMarks);
//		examMarks.clear();
//		System.out.printf("Size: %d   Capacity: %d     Slots full: %d\t %s%n", 
//				examMarks.size(), examMarks.getCapacity(), examMarks.slotsFull, examMarks);

        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		
/* ================================================================================================	
              					PRINTING PAIRS
================================================================================================ */

//		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//		}

/* ================================================================================================	
              					CARTESIAN PRODUCT
================================================================================================ */

//		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
//			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
//				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), 
//					pair1.getValue(), pair2.getKey(),pair2.getValue());
//			}
//		}

/* ================================================================================================	
             					TESTING REMOVE METHOD
================================================================================================ */

//		System.out.println(examMarks);
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}
//		System.out.println(examMarks);

/* ================================================================================================	
             		WILL THROW EXC. , REMOVING TWICE ON SAME ENTRY
================================================================================================ */

//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}
		
/* ================================================================================================	
             WILL THROW EXC. , TABLE CHANGED IN BEETWEEN NEXT() and HASNEXT() OPs
================================================================================================ */

//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}
		
/* ================================================================================================	
              				FINAL TEST , PRINTING THEN CLEAR
================================================================================================ */

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            iter.remove();
        }
        System.out.printf("Veličina: %d%n", examMarks.size());
    }
}
