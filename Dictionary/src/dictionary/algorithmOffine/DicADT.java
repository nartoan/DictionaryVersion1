/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.algorithmOffine;

import java.util.ArrayList;

/**
 *
 * @author DUC TOAN
 */
public interface DicADT {
    public String search(StringBuilder eng);
    public boolean add(StringBuilder eng, String viet);
    public void delete(StringBuilder eng) ;
    public void replace(StringBuilder eng, StringBuilder newViet);
    public ArrayList<String> wordSuggest(String key);
}
