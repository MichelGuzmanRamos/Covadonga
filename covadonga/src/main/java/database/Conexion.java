/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
     Connection connection=null;
    //Variables de usuario y contraseña de la Db 
    //
    String user="postgres";
    String pass="12345";
    String url= "jdbc:postgresql://localhost:5432/Covadonga";
    
    public Connection conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(url,user,pass);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"error al conectar"+e,
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }
}
