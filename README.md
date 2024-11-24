CHESS MASTER - APLICACIÓN DE AJEDREZ
===================================

Descripción
-----------
Chess Master es una aplicación de ajedrez que permite jugar partidas y guardarlas en formato PGN (Portable Game Notation). La aplicación cuenta con una interfaz gráfica moderna y funcionalidades para registrar y guardar partidas completas.

Ejecución de la Aplicación
-------------------------
1. Asegúrese de tener Java instalado en su sistema
2. Ejecute el archivo Principal.java ubicado en el paquete 'main'
3. O utilice el archivo JAR compilado:
   java -jar ChessMaster.jar

Funcionalidades Principales
--------------------------
1. CONFIGURACIÓN INICIAL
   - Formulario de metadatos para ingresar detalles de la partida
   - Campos para: Evento, Sitio, Fecha, Ronda, Jugadores y Resultado

2. REGISTRO DE MOVIMIENTOS
   - Panel lateral que muestra los movimientos en notación PGN
   - Actualización automática tras cada jugada
   - Visualización en formato estándar de ajedrez

3. GUARDADO DE PARTIDAS
   - Opción para guardar la partida en formato PGN
   - Menú Archivo > Guardar PGN
   - Selector de ubicación para guardar el archivo

Notas de Uso
------------
- Los metadatos deben ingresarse al inicio de la partida
- El formato de fecha debe ser YYYY.MM.DD
- Los resultados válidos son: 1-0, 0-1, 1/2-1/2, *
- La partida se puede guardar en cualquier momento
