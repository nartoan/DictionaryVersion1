package dictionary.algorithmOffine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author DUC TOAN
 */
public class MyTable {
    private final String base = "aáàảãạăắằẳẵặâấầẩẫậbcdeéèẻẽẹêếềểễệfghiíìỉĩịjklmno"
            + "óòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz";
    // muc dich mang base la de xac dinh xem 1 tu se nam o dau dua theo
    // chu cai dau cua no
    private final List<Pair>[] data;
    private final ArrayList<String> suggestSet = new ArrayList<>();
    private final ArrayList<String> keySet = new ArrayList<>();

    public MyTable() {
        data = new List[base.length() + 1];
    }

    public void put(String key, String value) {
        if (key.isEmpty()) {
            return;
        }
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        if (data[position] == null) {
            data[position] = new ArrayList<>();
        } else {
            data[position].add(new Pair(key, value));
        }
    }

    public void putInOrder(String key, String value) {
        if (key.isEmpty()) {
            return;
        }
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        if (data[position] == null) {
            data[position] = new ArrayList<>();
            data[position].add(new Pair(key, value));
        } else {
            int size = data[position].size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1 || data[position].get(i).key.compareTo(key) >= 0) {
                    data[position].add(i, new Pair(key, value));
                    break;
                }
            }
        }
    }

    public String get(String key) {
        if (key.length() == 0) {
            return null;
        }
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        if (data[position] == null) {
            return null;
        }
        int size = data[position].size();
        for (int i = 0; i < size; i++) {
            Pair keyValue = data[position].get(i);
            if (key.equals(keyValue.key)) {
                return keyValue.value;
            }
        }
        return null;
    }

    //Kiem tra da co chua
    public boolean containsKey(String key) {
        if (key.length() == 0) {
            return false;
        }
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        if (data[position] == null) {
            return false;
        }
        int size = data[position].size();
        for (int i = 0; i < size; i++) {
            Pair keyValue = data[position].get(i);
            if (key.equals(keyValue.key)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String key) {
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        int size = data[position].size();
        for (int i = 0; i < size; i++) {
            Pair keyValue = data[position].get(i);
            if (key.equals(keyValue.key)) {
                data[position].remove(i);
                return;
            }
        }
    }

    public void replace(String key, String replaceStr) {
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        int size = data[position].size();
        for (int i = 0; i < size; i++) {
            Pair keyValue = data[position].get(i);
            if (key.equals(keyValue.key)) {
                keyValue.value = replaceStr;
                return;
            }
        }
    }

    public ArrayList<String> suggestSet(String key) {
        suggestSet.clear();
        if (key.isEmpty()) {
            return suggestSet;
        }
        int n = key.length();
        key = key.toLowerCase();
        int position = base.indexOf(key.charAt(0)) + 1;
        if (data[position] == null) {
            return suggestSet;
        }
        int size = data[position].size();
        for (int i = 0; i < size; i++) {
            String temp = data[position].get(i).key;
            if (n <= temp.length() && key.equals(temp.substring(0, n))) {
                suggestSet.add(temp);
            }
            if (suggestSet.size() == 30) {
                break;
            }
        }
        return suggestSet;
    }

    //Chua biet dung lam gi
    public ArrayList<String> keySet() {
        keySet.clear();
        int size = base.length();
        for (int i = 0; i < size; i++) {
            if (data[i] != null) {
                int dataSize = data[i].size();
                for (int j = 0; j < dataSize; j++) {
                    keySet.add(data[i].get(j).key);
                }
            }
        }
        return keySet;
    }

    public void outToFile(String fileName) throws IOException {
        try (FileWriter output = new FileWriter(new File(fileName))) {
            int pos = 1;
            StringBuilder line = new StringBuilder();
            int size = base.length();
            for (int i = 0; i < size; i++) {
                if (data[i] != null) {
                    int dataSize = data[i].size();
                    for (int j = 0; j < dataSize; j++) {
                        line.delete(0, line.length());
                        line.append(pos).append("|").append(data[i].get(j).key).append("|");
                        line.append(data[i].get(j).value).append("\n");
                        output.write(line.toString());
                        pos++;
                    }
                }
            }
        }
    }

    private class Pair {

        public String key;
        public String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
