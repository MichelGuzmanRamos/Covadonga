package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.*;


public class Crud extends Conexion {
    java.sql.Statement st;
    ResultSet rs;
    public String mayusculas(String dato){
        return dato.toUpperCase();
    }
    public boolean autenticacion (String roll, String pass){
        Connection conexion= conectar(); //conexion a la base de datos
        boolean aceptar;
        String nombre;
        String consulta="select nombre from personal " //Consulta para encontrar al usuario correcto
                    + "where roll='"+roll+"'and password='"+pass+"'";
        try {
            st=conexion.createStatement(); //
            rs=st.executeQuery(consulta);//Verificacion de datos
            
            //Se comprueba datos de la consulta
            if (rs.next()){
                nombre=rs.getString("nombre");
                JOptionPane.showMessageDialog(null,
                        "Bienvenido: "+nombre+", Tipo de Usuario: "+roll,"Bienvenido",
                        JOptionPane.INFORMATION_MESSAGE);
                aceptar=true;//respuesta de la consulta 
            }
            else{
                //error de usuario 
                JOptionPane.showMessageDialog(null,
                        "no se encontro usuario","Sin registro",
                        JOptionPane.INFORMATION_MESSAGE);
                aceptar=false;//respuesta de la consulta 
                
            }
            st.close();
            conexion.close();
        } catch(Exception e){
            //fallo en la conexio de datos
            JOptionPane.showMessageDialog(null,
                        "error del sistema","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE);
            aceptar=false;
        }
        return aceptar;// se manda la respuesta
            
    }
    public String name(String roll,String pass){
        String nombre="";
        Connection conexion= conectar(); //conexion a la base de datos
        String consulta="select nombre from personal " //Consulta para encontrar al usuario correcto
                    + "where roll='"+roll+"'and password='"+pass+"'";
        try {
            st=conexion.createStatement(); //
            rs=st.executeQuery(consulta);//Verificacion de datos
            
            //Se comprueba datos de la consulta
            if (rs.next()){
                nombre=rs.getString("nombre");
            }
            else{
                //error de usuario 
                JOptionPane.showMessageDialog(null,
                        "no se encontro usuario","Sin registro",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            st.close();
            conexion.close();
        } catch(Exception e){
            //fallo en la conexio de datos
            JOptionPane.showMessageDialog(null,
                        "error del sistema","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE);
        }
        return nombre;
    }
    public String zona(String zona){
        Connection conexion= conectar();
        String id="";
        String consulta="select tipo_zona from zona";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                id=rs.getString("tipo_zona");
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error tabla Zona ","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        }
        return id;
    }
    public void calle(JComboBox combo, String zona){
        Connection conexion= conectar();
        String consulta="select c.nombre_calle " +
                "from calle as c " +
                "join zona as z on c.id_zona = z.id_zona " +
                "where Z.tipo_zona= '"+zona+"' ";
        
        
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                combo.addItem(rs.getString("nombre_calle"));
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error de tabla Calle","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    public String fechaActual(){
        String fecha;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaactual = new Date();
        fecha=sdf.format(fechaactual);
        return fecha;
    } 
    public void tipoCliente(JComboBox combo){
        Connection conexion= conectar();
        String consulta="select tipo_cliente from tipo_cliente";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                combo.addItem(rs.getString("tipo_cliente"));
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error tabla Tipo_cliente","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        } 
    }
    public int idCalle(String calle){
        Connection conexion= conectar();
        int id=0;
        String consulta="select id_calle from calle "
                + "where nombre_calle='"+calle+"'";
        
        
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                id=rs.getInt("id_calle");
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error de tabla Calle","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        }
        
        return id;
        
    }
    public int idTipoCliente(String tcliente){
        Connection conexion= conectar();
        int id=0; /**/
        String consulta="select id from tipo_cliente where tipo_cliente='"+tcliente+"'";
        try{ 
            st=conexion.createStatement(); /*hace la conexion*/
            rs=st.executeQuery(consulta);  /*ejecuta la consulta*/ 
            
            while(rs.next()){
                id=rs.getInt("id"); /*se obtiene el id*/
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error tabla Tipo_cliente","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        } 
        return id;
    }
    public void altaDomicilio(String nombre,int calle, String numDom,int tcliente,
            String telefono,String registro, String referencia, int construccion){
        
        Connection conexion= conectar();
        String consulta="insert into cliente " +
                "(nombre,numero_domicilio,telefono,referencia,id_calle,id_tipo_cliente,fecha_registro, id_construccion) " +
                "values ('"+nombre+"','"+numDom+"','"+telefono+"','"+referencia+"',"+calle+","+tcliente+",'"+registro+"',"+construccion+")";
        try{
            st=conexion.createStatement();
            int valor = st.executeUpdate(consulta); 
            if(valor>0){
                JOptionPane.showMessageDialog(null,
                    "Ingresado al sistema al Titular: " +nombre, "Actualización",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,
                "Error al ingresar los datos a la tabla Cliente", "Error de Actualización",
                JOptionPane.ERROR_MESSAGE);
            
        }
    }
    public void antiguoTitular(JComboBox combo){
        Connection conexion= conectar();
        String consulta="select nombre from titular " +
                "order by nombre asc";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                combo.addItem(rs.getString("nombre"));
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error en Titular","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        } 
        
    }
    public void altaTitular(String nombre, String fecha, String telefono, boolean estatus, int t_cliente){
        Connection conexion= conectar();
        String consulta="insert into titular(nombre,fecha_nacimiento,telefono,estatus,tipo_cliente) " +
                "values ('"+nombre+"','"+fecha+"','"+telefono+"',"+estatus+","+t_cliente+")";
        try{
            st=conexion.createStatement();
            int valor = st.executeUpdate(consulta); 
            if(valor>0){
                
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,
                "Error al ingresar los datos del Titular", "Error de ALTA TITULAR",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void edificio(JComboBox combo, String calle){
        Connection conexion= conectar();
        String consulta="select e.nombre_edificio as edificio " +
                "from edificio as e " +
                "join calle as c on e.id_calle = c.id_calle " +
                "where c.nombre_calle='"+calle+"'";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                combo.addItem(rs.getString("edificio"));
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error tabla Tipo_cliente","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        } 

    }
    public int idConstruccion(String construccion){
        Connection conexion= conectar();
        int id=0;
        String consulta="select id_construccion from construccion " +
                "where tipo_construccion= '"+construccion+"'";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                id=rs.getInt("id_construccion");
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "Error tabla Tipo_cliente","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        } 
        return id;
    }
    public void altaUsuarioDept(int idedificio, int idcliente, String numDept, String usuario){
        Connection conexion= conectar();
        String consulta="insert into departamento(id_edificio,id_cliente,num_departamento,tipo_usuario) " +
                "values("+idedificio+","+idcliente+",'"+numDept+"','"+usuario+"')";
        
        try{
            st=conexion.createStatement();
            int resultado=st.executeUpdate(consulta);
            if(resultado>0){ 
                JOptionPane.showMessageDialog(null,
                    "Ingresado a la seccion de departamento", "Actualización",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            
        }
        
    }
    public String fecha(){
        String fecha;
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        // Definir un formato para la fecha y hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatear la fecha y hora según el formato definido
        fecha= fechaHoraActual.format(formato);
        return fecha;
        }
    }