package ejerciciosXml;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class EscribirXmlAlumnos {
public static void main(String[] args) throws IOException {
    	
		ArrayList<Alumno> alumnosList = new ArrayList<>();
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Escribe la ruta en la que se encuentra el archivo txt con los alumnos");
    	String ruta = scan.nextLine();
        Path rutaArchivo = Paths.get(ruta);
        
        System.out.println("Dame la ruta donde se creara alumnos.xml: ");
        String rutaAlumnos = scan.nextLine();
        
        
        
        try (FileChannel canal = FileChannel.open(rutaArchivo, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) canal.size());
            
            canal.read(buffer);
            
            buffer.flip();
            
            String contenido = new String(buffer.array(), StandardCharsets.UTF_8);
            
            String[] lineas = contenido.split("\\n");
            
            System.out.println("Contenido del archivo:");
            System.out.println("---------------------");
            int contador = 0;
            for (String linea : lineas) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                	Alumno alumno = new Alumno();
                	alumnosList.add(alumno);
                    String[] datos = linea.split(",");
                    String matricula = datos[0];
                    String nombre = datos[1];
                    double nota = Double.parseDouble(datos[2]);
                    alumno.setMatricula(matricula);
                    alumno.setNombre(nombre);
                    alumno.setNota(nota);
                    System.out.println("ID: " + datos[0].getClass());
                    System.out.println("Nombre: " + datos[1]);
                    System.out.println("Nota: " + datos[2]);
                    System.out.println("---------------------");
                }
            }
            
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	try {
				DocumentBuilder builder = factory.newDocumentBuilder();//hasta acá es javax
				DOMImplementation implementation = builder.getDOMImplementation();//apartir de aca es org
				
				Document documento = implementation.createDocument(null, "aula", null);//este es el nombre del elemento padre
				documento.setXmlVersion("1.0");
				
					
				Element alumnos = documento.createElement("Alumnos");//empezamos a crear los elementos hijos
				
				for(Alumno persona : alumnosList) {
	            	
					Element alumno = documento.createElement("alumno");
					alumno.setAttribute("matricula", persona.getMatricula());///los dos tienen que ser String tipo
					
					Element nombre = documento.createElement("nombre");
					Text textNombre = documento.createTextNode(persona.getNombre());//con Text estamos creado un texto dentro de nuestras etiquetas xml pero ojo que aun no se agrega al elemento xml
					nombre.appendChild(textNombre);//con el appendChild anexa el contenido que esta entre parentedir como valor del elemento que hace el metodo appendChild
					alumno.appendChild(nombre);
					
					Element nota = documento.createElement("nota");
					Text textNota = documento.createTextNode(String.valueOf(persona.getNota()));
					nota.appendChild(textNota);
					alumno.appendChild(nota);
					alumnos.appendChild(alumno);
	            }
				
				
				
				
				documento.getDocumentElement().appendChild(alumnos);
				
				
				Source source = new DOMSource(documento);
				
				Result reslut = new StreamResult(new File(rutaAlumnos));//interfaz resul
				
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				
				transformer.transform(source, reslut);
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }
}
