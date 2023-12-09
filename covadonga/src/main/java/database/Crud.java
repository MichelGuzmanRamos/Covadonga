package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            st.close();
            conexion.close();
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
            st.close();
            conexion.close();
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
            st.close();
            conexion.close();
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
    public void reciboDatos(String id_titular,JLabel nombre, JLabel tipo_cliente, JLabel direccion){
        Connection conexion= conectar();
        String consulta="select t.nombre||' '||t.apellido_paterno||' '||t.apellido_materno as nombre_completo, " +
                "tp.tipo_cliente as cliente, " +
                "c.nombre_calle||' '||d.num_domicilio as direccion " +
                "from titular as t " +
                "join tipo_cliente as tp " +
                "on t.tipo_cliente=tp.id " +
                "join direccion as d " +
                "on d.id_titular=t.id_titular " +
                "join calle as c " +
                "on c.id_calle=d.id_calle " +
                "where t.id_titular= '"+id_titular+"'";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            if(rs.next()){ 
               nombre.setText(rs.getString("nombre_completo"));
               tipo_cliente.setText(rs.getString("cliente"));
               direccion.setText(rs.getString("direccion"));
            }
            st.close();
            conexion.close();
        }catch(Exception e){
            
            }
        }
    public void cConcepto(JComboBox combo){
        
        Connection conexion= conectar();
        String consulta="select clave from tipo_concepto";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            
            while(rs.next()){
                combo.addItem(rs.getString("clave"));
            }
            st.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "error del sistema","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE); 
        }
    }
    public void montoPago(String clave, JLabel monto){
        Connection conexion= conectar();
        String consulta="select monto from tipo_concepto " +
                "where clave='"+clave+"'";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            if(rs.next()){ 
               monto.setText("$"+rs.getString("monto"));
            }
            st.close();
            conexion.close();
        }catch(Exception e){
            
        }   
    }
    public String id_concepto(String clave){
        Connection conexion= conectar();
        String consulta="select id_tconcepto as concepto from tipo_concepto " +
                "where clave='"+clave+"'";
        String concepto="";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            if(rs.next()){ 
               concepto=rs.getString("concepto");
            }
            st.close();
            conexion.close();
        }catch(Exception e){
            
        }   
        return concepto;
    }
    public void altaRecibo(String folio, String titular, String concepto, String pago,
             String monto, String empleado,String observacion,String fecha) {
        Connection conexion = conectar();
        String consulta = "INSERT INTO recibo(folio, id_titular, id_concepto, forma_pago, monto, empleado, observacion, fecha) " +
                "VALUES ('"+folio+"', '"+titular+"', '"+concepto+"', '"+pago+"', '"+monto+"', '"+empleado+"', '"+observacion+"', '"+fecha+"')";

        try {
            st = conexion.createStatement();
            int filasAfectadas = st.executeUpdate(consulta);

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null,
                        "RECIBO GUARDADO", "RECIBO",
                        JOptionPane.INFORMATION_MESSAGE);
            } 
            st.close();
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar el recibo", "RECIBO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void historialDatos(String id_titular,JLabel nombre, JLabel telefono, JLabel direccion,JLabel zona){
        Connection conexion= conectar();
        String consulta="select t.nombre||' '||t.apellido_paterno||' '||t.apellido_materno as nombre_completo, " +
                "t.telefono as telefono, " +
                "c.nombre_calle||' '||d.num_domicilio as direccion, " +
                "z.tipo_zona as tzona " +
                "from titular as t " +
                "join direccion as d " +
                "on d.id_titular=t.id_titular " +
                "join calle as c " +
                "on c.id_calle=d.id_calle " +
                "join zona as z " +
                "on z.id_zona=c.id_zona " +
                "where t.id_titular= '"+id_titular+"'";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            if(rs.next()){ 
               nombre.setText(rs.getString("nombre_completo"));
               telefono.setText(rs.getString("telefono"));
               direccion.setText(rs.getString("direccion"));
               zona.setText(rs.getString("tzona"));
            }
            st.close();
            conexion.close();
        }catch(Exception e){
            
            }
        }
    
    public void historialRecibos(JTable tablaG,String id_titular){
        Connection conexion= conectar();// conexion a la base de datos
        
        //consulta para mostrar a todos los emleados con su departamento
        String consulta="select r.fecha as fecha, " +
                "tc.descripcion as descripcion, " +
                "r.forma_pago as fpago, " +
                "r.monto as monto, " +
                "r.observacion as observacion, " +
                "r.empleado as empleado " +
                "from recibo as r " +
                "join tipo_concepto as tc " +
                "on tc.id_tconcepto = r.id_concepto " +
                "where id_titular= '"+id_titular+"'";
        //Se diseña la tabla a mostrar
        DefaultTableModel modelo= new DefaultTableModel();
        TableRowSorter<TableModel>OrdenarTabla=new TableRowSorter<TableModel>(modelo);
        
        tablaG.setRowSorter(OrdenarTabla);
        
        //cabeceras
        modelo.addColumn("FECHA");
        modelo.addColumn("CONCEPTO");
        modelo.addColumn("FORMA DE PAGO");
        modelo.addColumn("MONTO");
        modelo.addColumn("OBSERVACION");
        modelo.addColumn("EMPLEADO");
        
        tablaG.setModel(modelo);
        String[] datos=new String [6];
        
        try {
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);// se ejecuta la aconsulta 
            
            //Llenado de la tabla
            while(rs.next()){
                
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]="$"+rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                
                modelo.addRow(datos);
                
            }
            
            
            tablaG.setModel(modelo);
           
            st.close();
            conexion.close();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,
                        "error del sistema","Error de Busqueda",
                        JOptionPane.ERROR_MESSAGE);    
        }
    }
    
    public void altaCalle(String calle, String id_zona){
        Connection conexion= conectar();
        String consulta="INSERT INTO calle(nombre_calle, id_zona) " +
                "VALUES ('"+calle+"','"+id_zona+"');";
        try{
            st=conexion.createStatement();
            int valor = st.executeUpdate(consulta); 
            if(valor>0){
                JOptionPane.showMessageDialog(null,
                        "NUEVA CALLE DADA DE ALTA ", "NUEVA CALLE",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            st.close();
            conexion.close();
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,
                "Error al ingresar LA NUEVA CALLE", "Error de ALTA CALLE",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void ultimoPago(String id, JLabel fecha, JLabel folio, JLabel concepto,
            JLabel pago,JTextArea observacion){
        Connection conexion= conectar();
        String consulta="select r.fecha, r.folio, " +
                "c.clave as clave, r.monto, " +
                "r.observacion " +
                "from recibo as r " +
                "join tipo_concepto as c " +
                "on r.id_concepto = c.id_tconcepto " +
                "where id_titular='"+id+"' " +
                "order by DATE_TRUNC('day', r.fecha) + INTERVAL '1 day' - INTERVAL '1 second' DESC " +
                "LIMIT 1";
        try{
            st=conexion.createStatement();
            rs=st.executeQuery(consulta);
            if(rs.next()){ 
               fecha.setText(rs.getString("fecha"));
               folio.setText(rs.getString("folio"));
               concepto.setText(rs.getString("clave"));
               pago.setText(rs.getString("monto"));
               observacion.setText(rs.getString("observacion"));
            }
            st.close();
            conexion.close();
        }catch(Exception e){
            
        }
    }
    
}
