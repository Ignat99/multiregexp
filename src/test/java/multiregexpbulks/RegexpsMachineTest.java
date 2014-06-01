package multiregexpbulks;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

    @Test
    public void test1() {
        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper("src\\test\\java\\multiregexpbulks\\RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src\\test\\java\\multiregexpbulks\\TestSet.txt");
        boolean passed = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String expected = entry.getValue();
            String actual = regexpsMachineHelper.getCategoryForURL(entry.getKey());
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
        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper("src\\test\\java\\multiregexpbulks\\RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src\\test\\java\\multiregexpbulks\\TestSet.txt");
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
        RegexpsMachineHelper<String> regexpsMachineHelper = readFileAndConstructRegexpsMachineHelper("src\\test\\java\\multiregexpbulks\\RegExps.txt");
        Map<String, String> map = readTestSetFromFile("src\\test\\java\\multiregexpbulks\\TestSet.txt");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                regexpsMachineHelper.getCategoryForURL(entry.getKey());
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}