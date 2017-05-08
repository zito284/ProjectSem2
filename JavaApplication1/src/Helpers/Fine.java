/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Zito
 */
public class Fine {
    
    public static double calculateFine(int daylate,double priceBook){
        double result = daylate * 0.1;
        return result > priceBook ? priceBook : result;                
    }
    public static String calculateTotal(JTable table,int columnPrice){
        double result = 0;
        TableModel model = table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            
            result +=  Float.valueOf(model.getValueAt(i, columnPrice).toString()) ;
        }   
        result = Math.round(result*100.0)/100.0;
        DecimalFormat df = new DecimalFormat("$ ###.##");
        
        return df.format(result);
    }
}
