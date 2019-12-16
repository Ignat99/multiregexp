package multiregexpbulks;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

//import multiregexpbulks.State;
import multiregexpbulks.finite.FiniteState;
import multiregexpbulks.finite.FiniteAutomaton;

public class RegexpsMachineTest {

    private static RegexpsMachineHelper<String> readFileAndConstructRegexpsMachineHelper(String regexpsFileName) {

        FileReader fr;
        BufferedReader br;
        AtomicReference<RegexpsMachineHelper<String>> regexpsMachineHelper;
        regexpsMachineHelper = new AtomicReference<RegexpsMachineHelper<String>>();
        regexpsMachineHelper.set(new RegexpsMachineHelper<String>());
        try {
            fr = new FileReader(regexpsFileName);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";;");
                regexpsMachineHelper.get().add(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        regexpsMachineHelper.get().constructAutomatonMapping();
        return regexpsMachineHelper.get();
    }

    private static Map<String, String> readTestSetFromFile(String regexpsFileName) {

        FileReader fr;
        Map<String, String> map = new LinkedHashMap<String, String>();
        BufferedReader br;
        try {
            fr = new FileReader(regexpsFileName);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";;");
                map.put(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

/*
    @Test
    public void test1() {

        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
                ("src/test/java/multiregexpbulks/RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src/test/java/multiregexpbulks/TestSet.txt");
        boolean passed = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String expected = entry.getValue();
            String actual = regexpsMachineHelper.getValueForURL(entry.getKey());
            String url = entry.getKey();
            if (!expected.equals(actual)) {
                passed = false;
                System.out.println("Category for " + url + " is " + actual);
                System.out.println("Expected " + expected);
            }
        }
        Assert.assertTrue(passed);
    }

    @Test
    public void test2() {

        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
                ("src/test/java/multiregexpbulks/RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src/test/java/multiregexpbulks/TestSet.txt");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String expected = entry.getValue();
            List<String> actuals = regexpsMachineHelper.getAllCategoriesForURL(entry.getKey());
            String url = entry.getKey();
            System.out.println("Category for " + url + " is " + actuals);
            System.out.println("Expected " + expected);
        }
    }

    @Test
    public void test3() {

        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
                ("src/test/java/multiregexpbulks/RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src/test/java/multiregexpbulks/TestSet.txt");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                regexpsMachineHelper.getValueForURL(entry.getKey());
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
*/

/*
    @Test
    public void test4() {

        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
                ("src/test/java/multiregexpbulks/RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src/test/java/multiregexpbulks/TestSet.txt");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String expected = entry.getValue();
            String actual = regexpsMachineHelper.getValueForURLEager(entry.getKey());
            String url = entry.getKey();
            System.out.println("Category for " + url + " is " + actual);
            System.out.println("Expected " + expected);
        }
    }

    @Test
    public void test6() {

        try {
            RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
                    ("src/test/java/multiregexpbulks/RegExps.txt");
            FileOutputStream fileOut = new FileOutputStream("src/test/java/multiregexpbulks/serTest.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(regexpsMachineHelper);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
*/


    @Test
    public void test5() {

//        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper
//                ("src/test/java/multiregexpbulks/RegExps.txt");
//        Map<String, String> map = readTestSetFromFile("src/test/java/multiregexpbulks/TestSet.txt");
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                regexpsMachineHelper.getValueForURL(entry.getKey());
//            }
//        }
        FiniteState s0 = new FiniteState("s0");
        FiniteState g1 = new FiniteState("g1");
        FiniteState p2 = new FiniteState("p2");
        FiniteState x3 = new FiniteState("x3");
        FiniteState l4 = new FiniteState("l4");
//        FiniteState r5 = new FiniteState("r5");

        s0.setFinal();

        // this finite automaton is deterministic
        s0.addTransition(x3, 'c');
        s0.addTransition(x3, 'h');

        x3.addTransition(s0, 'g');

        g1.addTransition(s0, 'h');
        g1.addTransition(s0, 'c');
        g1.addTransition(x3, 'h', 'c');

        p2.addTransition(s0, 'p');

        l4.addTransition(l4, 'd');
        l4.addTransition(x3, 'h', 'c');
        l4.addTransition(g1, 'h');
        l4.addTransition(s0, 'c');


        FiniteAutomaton automat = new FiniteAutomaton(l4);

//        assertTrue(automat.testWord("ptvtvvt").isValid());

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("Test 5 " + automat.testWord("hd"));
    }


}