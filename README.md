# Generación de Archivo XML a partir de Datos de Alumnos

Este programa en Java lee un archivo de texto con información de alumnos y genera un archivo XML que contiene los datos estructurados. Utiliza las bibliotecas `FileChannel`, `ByteBuffer` para leer el archivo de texto y `DOM` para crear el archivo XML.

## Funcionalidades

- Lectura de un archivo de texto con datos de alumnos (matrícula, nombre y nota).
- Almacenamiento de los datos en una lista de objetos `Alumno`.
- Generación de un archivo XML con la información de los alumnos.
  
## Estructura del Archivo de Entrada

El archivo de texto debe contener los datos de los alumnos en formato CSV, con las siguientes columnas por línea:
### Ejemplo de Entrada

```csv
12345,Juan Pérez,6.5
67890,Ana López,4.0
11223,María Díaz,7.8

<aula>
  <Alumnos>
    <alumno matricula="12345">
      <nombre>Juan Pérez</nombre>
      <nota>6.5</nota>
    </alumno>
    <alumno matricula="11223">
      <nombre>María Díaz</nombre>
      <nota>7.8</nota>
    </alumno>
  </Alumnos>
</aula>
