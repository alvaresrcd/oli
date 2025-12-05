Proceso RestauranteReservaciones
    // -- ESTRUCTURA DE DATOS PRINCIPAL --
    // Se utilizan arreglos paralelos para simular una lista de objetos "Reservacion".
    // Cada índice en los arreglos corresponde a una misma reservación.
    Definir max_reservaciones Como Entero
    max_reservaciones <- 100 // Capacidad máxima del sistema

    Dimension nombres[max_reservaciones]
    Dimension celulares[max_reservaciones]
    Dimension dias[max_reservaciones]
    Dimension horas[max_reservaciones]

    // Variable para llevar la cuenta de cuántas reservaciones hay actualmente.
    Definir num_reservaciones Como Entero
    num_reservaciones <- 0

    // -- LÓGICA PRINCIPAL --
    // Cargar las reservaciones existentes desde el archivo de texto.
    CargarReservaciones(nombres, celulares, dias, horas, num_reservaciones)

    Definir opcion Como Entero
    Repetir
        MostrarMenu()
        Leer opcion

        Segun opcion Hacer
            1:
                ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
            2:
                AgregarReservacion(nombres, celulares, dias, horas, num_reservaciones, max_reservaciones)
            3:
                ModificarReservacion(nombres, celulares, dias, horas, num_reservaciones)
            4:
                CancelarReservacion(nombres, celulares, dias, horas, num_reservaciones)
            5:
                // Guardar antes de salir
                GuardarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
                Escribir "Gracias por usar el sistema. ¡Adiós!"
            De Otro Modo:
                Escribir "Opción no válida. Intente de nuevo."
        FinSegun

        Si opcion <> 5 Entonces
            Escribir "" // Línea en blanco para separar
            Escribir "Presione Enter para continuar..."
            Esperar Tecla
        FinSi

    Hasta Que opcion = 5

FinProceso

// Subproceso para mostrar el menú principal
SubProceso MostrarMenu()
    Limpiar Pantalla
    Escribir "****************************************"
    Escribir "*   SISTEMA DE RESERVACIONES           *"
    Escribir "****************************************"
    Escribir "* 1. Listar todas las reservaciones    *"
    Escribir "* 2. Agregar nueva reservación         *"
    Escribir "* 3. Modificar una reservación         *"
    Escribir "* 4. Cancelar una reservación          *"
    Escribir "* 5. Guardar y Salir                   *"
    Escribir "****************************************"
    Escribir Sin Saltar "Seleccione una opción: "
FinSubProceso

// --- SUBPROCESOS DE MANEJO DE ARCHIVOS ---

// --- FUNCIONES AUXILIARES ---

// Pseint no tiene una función para dividir cadenas (split). Esta función extrae el N-ésimo campo de una cadena CSV.
// Por ejemplo, ObtenerCampo("a,b,c", 2) devolvería "b".
Funcion campo <- ObtenerCampo(linea, indice_campo)
    Definir campo Como Caracter
    Definir i, contador_comas, inicio_campo Como Entero

    contador_comas <- 0
    inicio_campo <- 1
    campo <- ""

    Para i <- 1 Hasta Longitud(linea)
        Si Subcadena(linea, i, i) = "," Entonces
            contador_comas <- contador_comas + 1
            Si contador_comas = indice_campo Entonces
                campo <- Subcadena(linea, inicio_campo, i - 1)
                i <- Longitud(linea) // Terminar el bucle
            FinSi
            inicio_campo <- i + 1
        FinSi
    FinPara

    // Si es el último campo (no termina en coma)
    Si campo = "" Entonces
        campo <- Subcadena(linea, inicio_campo, Longitud(linea))
    FinSi
Fin Funcion


// --- SUBPROCESOS DE MANEJO DE ARCHIVOS ---

// Carga las reservaciones desde "reservaciones.txt"
SubProceso CargarReservaciones(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia)
    Definir archivo, linea Como Caracter
    archivo <- "reservaciones.txt"

    num_reservaciones <- 0
    Abrir archivo Como 1 Para Lectura

    // Si el archivo no está vacío, leerlo
    Si NO EsFin(1) Entonces
        Mientras NO EsFin(1) Hacer
            Leer 1, linea
            Si num_reservaciones < 100 Entonces // Asegurarse de no exceder la capacidad
                nombres[num_reservaciones] <- ObtenerCampo(linea, 1)
                celulares[num_reservaciones] <- ObtenerCampo(linea, 2)
                dias[num_reservaciones] <- ObtenerCampo(linea, 3)
                horas[num_reservaciones] <- ObtenerCampo(linea, 4)
                num_reservaciones <- num_reservaciones + 1
            FinSi
        FinMientras
    SiNo
        // El archivo no existe o está vacío, se puede crear un archivo vacío para la primera ejecución.
        Abrir archivo Como 1 Para Escritura
        Cerrar 1
        Abrir archivo Como 1 Para Lectura // Reabrir para lectura (ahora vacío)
    FinSi
    Cerrar 1
FinSubProceso

// Guarda las reservaciones actuales en "reservaciones.txt"
SubProceso GuardarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
    Definir archivo, linea Como Caracter
    Definir i Como Entero
    archivo <- "reservaciones.txt"

    Abrir archivo Como 1 Para Escritura
    Para i <- 0 Hasta num_reservaciones - 1 Hacer
        linea <- nombres[i] + "," + celulares[i] + "," + dias[i] + "," + horas[i]
        Escribir 1, linea
    FinPara
    Cerrar 1
FinSubProceso

// --- SUBPROCESOS DE FUNCIONALIDADES ---

// Muestra todas las reservaciones en un formato de tabla.
SubProceso ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
    Definir i Como Entero
    Limpiar Pantalla
    Escribir "--- LISTA DE RESERVACIONES ---"
    Si num_reservaciones = 0 Entonces
        Escribir "No hay reservaciones registradas."
    SiNo
        Escribir "-----------------------------------------------------------------"
        Escribir "No. | Nombre         | Celular        | Día        | Hora"
        Escribir "-----------------------------------------------------------------"
        Para i <- 0 Hasta num_reservaciones - 1 Hacer
            Escribir i+1, "   | ", nombres[i], " | ", celulares[i], " | ", dias[i], " | ", horas[i]
        FinPara
        Escribir "-----------------------------------------------------------------"
    FinSi
FinSubProceso

// Permite al usuario ingresar los datos para una nueva reservación.
SubProceso AgregarReservacion(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia, max_reservaciones)
    Limpiar Pantalla
    Escribir "--- AGREGAR NUEVA RESERVACIÓN ---"

    Si num_reservaciones >= max_reservaciones Entonces
        Escribir "El sistema ha alcanzado su capacidad máxima. No se pueden agregar más reservaciones."
    SiNo
        Definir nombre_nuevo, celular_nuevo, dia_nuevo, hora_nueva Como Caracter

        Escribir Sin Saltar "Ingrese el nombre del cliente: "
        Leer nombre_nuevo
        Escribir Sin Saltar "Ingrese el número de celular: "
        Leer celular_nuevo
        Escribir Sin Saltar "Ingrese el día (YYYY-MM-DD): "
        Leer dia_nuevo
        Escribir Sin Saltar "Ingrese la hora (HH:MM): "
        Leer hora_nueva

        nombres[num_reservaciones] <- nombre_nuevo
        celulares[num_reservaciones] <- celular_nuevo
        dias[num_reservaciones] <- dia_nuevo
        horas[num_reservaciones] <- hora_nueva

        num_reservaciones <- num_reservaciones + 1

        Escribir "¡Reservación agregada con éxito!"
    FinSi
FinSubProceso

// Busca una reservación por celular y permite cambiar el día y la hora.
SubProceso ModificarReservacion(nombres, celulares, dias Por Referencia, horas Por Referencia, num_reservaciones)
    Definir celular_busqueda, dia_nuevo, hora_nueva Como Caracter
    Definir i, indice_encontrado Como Entero

    Limpiar Pantalla
    Escribir "--- MODIFICAR RESERVACIÓN ---"

    Si num_reservaciones = 0 Entonces
        Escribir "No hay reservaciones para modificar."
    SiNo
        ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
        Escribir ""
        Escribir Sin Saltar "Ingrese el celular de la reservación a modificar: "
        Leer celular_busqueda

        indice_encontrado <- -1
        Para i <- 0 Hasta num_reservaciones - 1 Hacer
            Si celulares[i] = celular_busqueda Entonces
                indice_encontrado <- i
                i <- num_reservaciones // Terminar bucle
            FinSi
        FinPara

        Si indice_encontrado = -1 Entonces
            Escribir "No se encontró ninguna reservación con ese número de celular."
        SiNo
            Escribir "Reservación encontrada para: ", nombres[indice_encontrado]
            Escribir "Día actual: ", dias[indice_encontrado], " | Hora actual: ", horas[indice_encontrado]
            Escribir ""
            Escribir Sin Saltar "Ingrese el nuevo día (YYYY-MM-DD): "
            Leer dia_nuevo
            Escribir Sin Saltar "Ingrese la nueva hora (HH:MM): "
            Leer hora_nueva

            dias[indice_encontrado] <- dia_nuevo
            horas[indice_encontrado] <- hora_nueva

            Escribir "¡Reservación modificada con éxito!"
        FinSi
    FinSi
FinSubProceso

// Busca una reservación por celular y la elimina de los arreglos.
SubProceso CancelarReservacion(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia)
    Definir celular_busqueda, confirmacion Como Caracter
    Definir i, j, indice_encontrado Como Entero

    Limpiar Pantalla
    Escribir "--- CANCELAR RESERVACIÓN ---"

    Si num_reservaciones = 0 Entonces
        Escribir "No hay reservaciones para cancelar."
    SiNo
        ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
        Escribir ""
        Escribir Sin Saltar "Ingrese el celular de la reservación a cancelar: "
        Leer celular_busqueda

        indice_encontrado <- -1
        Para i <- 0 Hasta num_reservaciones - 1 Hacer
            Si celulares[i] = celular_busqueda Entonces
                indice_encontrado <- i
                i <- num_reservaciones // Terminar bucle
            FinSi
        FinPara

        Si indice_encontrado = -1 Entonces
            Escribir "No se encontró ninguna reservación con ese número de celular."
        SiNo
            Escribir "Reservación encontrada para: ", nombres[indice_encontrado]
            Escribir "¿Está seguro de que desea cancelar esta reservación? (S/N)"
            Leer confirmacion

            Si Minusculas(confirmacion) = "s" Entonces
                // Eliminar el elemento moviendo los elementos subsecuentes una posición hacia arriba.
                Para j <- indice_encontrado Hasta num_reservaciones - 2 Hacer
                    nombres[j] <- nombres[j+1]
                    celulares[j] <- celulares[j+1]
                    dias[j] <- dias[j+1]
                    horas[j] <- horas[j+1]
                FinPara

                num_reservaciones <- num_reservaciones - 1
                Escribir "¡Reservación cancelada con éxito!"
            SiNo
                Escribir "Operación cancelada."
            FinSi
        FinSi
    FinSi
FinSubProceso
