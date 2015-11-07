package dictionary.algorithmOffine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DUC TOAN
 */
public class Dict implements DicADT {

    private final MyTable data = new MyTable();

    //Constructor Doc va lay du lieu tu file EngViet vao data
    public Dict(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (BufferedReader input = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = input.readLine()) != null) {
                String[] buffer = mySplit(line, '|');
                data.put(buffer[0], buffer[1]);
            }
        }
    }

    //Tim kiem eng tron tu dien
    @Override
    public String search(StringBuilder eng) {
        standardize(eng);
        return data.get(eng.toString());
    }

    @Override
    public boolean add(StringBuilder eng, String viet) {
        standardize(eng);
        if (data.containsKey(eng.toString())) {
            return false;
        }
        data.putInOrder(eng.toString(), viet);
        return true;
    }

    @Override
    public void delete(StringBuilder eng) {
        standardize(eng);
        data.remove(eng.toString());
    }

    @Override
    public void replace(StringBuilder eng, StringBuilder newViet) {
        StringBuilder re = newViet;
        for (int i = 1; i < re.length(); i++) {
            if (re.charAt(i) == '\n' || (re.charAt(i) == ' ' && re.charAt(i - 1) == '>')) {
                re.deleteCharAt(i);
                i--;
            }
        }
        re.delete(0, 25); // xoa may phan header
        re.delete(re.length()-14, re.length()); // xoa phan duoi
        //System.out.println(re);
        data.replace(eng.toString(), re.toString());
    }

    public void outToFile(String fileName) throws IOException {
        data.outToFile(fileName);
    }

    @Override
    public ArrayList<String> wordSuggest(String key) {
        return data.suggestSet(key);
    }

    //Xoa bo dau cach o dau hoac cuoi
    private void standardize(StringBuilder word) {
        while (word.length() > 0 && word.charAt(0) == ' ') {
            word.deleteCharAt(0);
        }
        while (word.length() > 0 && word.charAt(word.length() - 1) == ' ') {
            word.deleteCharAt(word.length() - 1);
        }
    }

    private String[] mySplit(String str, char limit) {
        String[] result = new String[2];
        if (str.isEmpty()) {
            return result;
        }
        int start = 0;
        while (str.charAt(start) != limit) {
            start++;
        }
        int end = start + 1;
        while (str.charAt(end) != limit) {
            end++;
        }
        result[0] = str.substring(start + 1, end).toLowerCase();
        result[1] = str.substring(end + 1);
        return result;
    }
}
