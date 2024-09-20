/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javapokeapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

/**
 *
 * @author DUSTIN
 */
public class CPokeApi {
    public void MostrarPokemon(JTable tablapokemon,JTextField buscador,JTextField nombre,JTextField peso,JTextField altura,JTextField experienciabase, JLabel foto){
        DefaultTableModel modelo = new DefaultTableModel();
        String[] nombreColumnas = {"Nombre","Peso","Altura"};
        modelo.setColumnIdentifiers(nombreColumnas);
        
        tablapokemon.setModel(modelo);
        
        try{
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/"+ buscador.getText());
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while((line = reader.readLine())!=null){
            response.append(line);
        }
            reader.close();
            
            JSONObject jsonObject = new JSONObject(response.toString());
            String name = jsonObject.getString("name");
            int weight = jsonObject.getInt("weight");
            int height = jsonObject.getInt("height");
            int experience = jsonObject.getInt("base_experience");
            
            modelo.addRow(new Object[]{name,weight,height});
            
            nombre.setText(name);
            peso.setText(String.valueOf(weight));
            altura.setText(String.valueOf(height));
            experienciabase.setText(String.valueOf(experience));
            
            String imageUrl = jsonObject.getJSONObject("sprites").getString("front_default");
            
            ImageIcon icon = new ImageIcon(new URL(imageUrl));
            
            foto.setIcon(icon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ingrese solo id o nombre del Pokemon existente, error: "+ e.toString());
        }  
    }
}
