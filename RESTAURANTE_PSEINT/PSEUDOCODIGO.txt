// El Proceso principal actúa como el punto de entrada (el "Main").
// Desde aquí se inicializan los datos y se inicia el bucle principal
// que depende de la Vista y el Controlador.
Proceso RestauranteReservacionesMVC
    ////////////////////////////////////////////////////////////////////////////
    // 1. MODELO (Simulación de la clase Reservacion y su almacenamiento)
    ////////////////////////////////////////////////////////////////////////////
    Definir max_reservaciones Como Entero
    max_reservaciones <- 100

    // Arreglos paralelos que representan una lista de "objetos" Reservacion
    Dimension nombres[max_reservaciones]
    Dimension celulares[max_reservaciones]
    Dimension dias[max_reservaciones]
    Dimension horas[max_reservaciones]

    // Variable para llevar la cuenta de cuántas reservaciones hay.
    Definir num_reservaciones Como Entero
    num_reservaciones <- 0

    ////////////////////////////////////////////////////////////////////////////
    // PUNTO DE ENTRADA DE LA APLICACIÓN
    ////////////////////////////////////////////////////////////////////////////

    // El controlador se encarga de cargar los datos iniciales.
    Controlador_CargarReservaciones(nombres, celulares, dias, horas, num_reservaciones)

    Definir opcion Como Entero
    Repetir
        // La Vista se encarga de mostrar el menú y obtener la opción del usuario.
        Vista_MostrarMenu(opcion)

        // El Controlador se encarga de ejecutar la acción correspondiente.
        Controlador_ManejarOpcion(opcion, nombres, celulares, dias, horas, num_reservaciones, max_reservaciones)

    Hasta Que opcion = 5

FinProceso


////////////////////////////////////////////////////////////////////////////
// 2. VISTA (Subprocesos encargados de la Interfaz con el Usuario)
////////////////////////////////////////////////////////////////////////////

// Muestra el menú principal y lee la opción del usuario.
SubProceso Vista_MostrarMenu(opcion Por Referencia)
    Limpiar Pantalla
    Escribir "****************************************"
    Escribir "*   SISTEMA DE RESERVACIONES (MVC)     *"
    Escribir "****************************************"
    Escribir "* 1. Listar todas las reservaciones    *"
    Escribir "* 2. Agregar nueva reservación         *"
    Escribir "* 3. Modificar una reservación         *"
    Escribir "* 4. Cancelar una reservación          *"
    Escribir "* 5. Guardar y Salir                   *"
    Escribir "****************************************"
    Escribir Sin Saltar "Seleccione una opción: "
    Leer opcion
FinSubProceso

// Muestra la lista formateada de todas las reservaciones.
SubProceso Vista_ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
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

// Pide al usuario los datos para una nueva reservación.
SubProceso Vista_PedirDatosReservacion(nombre Por Referencia, celular Por Referencia, dia Por Referencia, hora Por Referencia)
    Limpiar Pantalla
    Escribir "--- AGREGAR NUEVA RESERVACIÓN ---"
    Escribir Sin Saltar "Ingrese el nombre del cliente: "
    Leer nombre
    Escribir Sin Saltar "Ingrese el número de celular: "
    Leer celular
    Escribir Sin Saltar "Ingrese el día (YYYY-MM-DD): "
    Leer dia
    Escribir Sin Saltar "Ingrese la hora (HH:MM): "
    Leer hora
FinSubProceso

// Muestra un mensaje de éxito.
SubProceso Vista_MostrarMensajeExito(mensaje)
    Escribir ""
    Escribir "[ÉXITO] ", mensaje
FinSubProceso

// Muestra un mensaje de error.
SubProceso Vista_MostrarError(mensaje)
    Escribir ""
    Escribir "[ERROR] ", mensaje
FinSubProceso


////////////////////////////////////////////////////////////////////////////
// 3. CONTROLADOR (Subprocesos que contienen la lógica del negocio)
////////////////////////////////////////////////////////////////////////////

// Función principal que dirige el flujo según la opción del usuario.
SubProceso Controlador_ManejarOpcion(opcion, nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia, max_reservaciones)
    Segun opcion Hacer
        1:
            Vista_ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
        2:
            Controlador_AgregarReservacion(nombres, celulares, dias, horas, num_reservaciones, max_reservaciones)
        3:
            Controlador_ModificarReservacion(nombres, celulares, dias, horas, num_reservaciones)
        4:
            Controlador_CancelarReservacion(nombres, celulares, dias, horas, num_reservaciones)
        5:
            Controlador_GuardarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
            Escribir "Gracias por usar el sistema. ¡Adiós!"
        De Otro Modo:
            Vista_MostrarError("Opción no válida. Intente de nuevo.")
    FinSegun

    Si opcion <> 5 Entonces
        Escribir ""
        Escribir "Presione Enter para continuar..."
        Esperar Tecla
    FinSi
FinSubProceso

// Lógica para agregar una nueva reservación.
SubProceso Controlador_AgregarReservacion(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia, max_reservaciones)
    Si num_reservaciones >= max_reservaciones Entonces
        Vista_MostrarError("El sistema ha alcanzado su capacidad máxima.")
    SiNo
        Definir nombre_nuevo, celular_nuevo, dia_nuevo, hora_nueva Como Caracter
        Vista_PedirDatosReservacion(nombre_nuevo, celular_nuevo, dia_nuevo, hora_nueva)

        nombres[num_reservaciones] <- nombre_nuevo
        celulares[num_reservaciones] <- celular_nuevo
        dias[num_reservaciones] <- dia_nuevo
        horas[num_reservaciones] <- hora_nueva

        num_reservaciones <- num_reservaciones + 1

        Vista_MostrarMensajeExito("¡Reservación agregada!")
    FinSi
FinSubProceso

// Lógica para modificar una reservación existente.
SubProceso Controlador_ModificarReservacion(nombres, celulares, dias Por Referencia, horas Por Referencia, num_reservaciones)
    Definir celular_busqueda, dia_nuevo, hora_nueva Como Caracter
    Definir indice_encontrado Como Entero

    Limpiar Pantalla
    Escribir "--- MODIFICAR RESERVACIÓN ---"

    Si num_reservaciones = 0 Entonces
        Vista_MostrarError("No hay reservaciones para modificar.")
    SiNo
        Vista_ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
        Escribir ""
        Escribir Sin Saltar "Ingrese el celular de la reservación a modificar: "
        Leer celular_busqueda

        indice_encontrado <- Controlador_BuscarPorCelular(celulares, num_reservaciones, celular_busqueda)

        Si indice_encontrado = -1 Entonces
            Vista_MostrarError("No se encontró ninguna reservación con ese número de celular.")
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

            Vista_MostrarMensajeExito("¡Reservación modificada!")
        FinSi
    FinSi
FinSubProceso

// Lógica para cancelar una reservación.
SubProceso Controlador_CancelarReservacion(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia)
    Definir celular_busqueda, confirmacion Como Caracter
    Definir i, j, indice_encontrado Como Entero

    Limpiar Pantalla
    Escribir "--- CANCELAR RESERVACIÓN ---"

    Si num_reservaciones = 0 Entonces
        Vista_MostrarError("No hay reservaciones para cancelar.")
    SiNo
        Vista_ListarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
        Escribir ""
        Escribir Sin Saltar "Ingrese el celular de la reservación a cancelar: "
        Leer celular_busqueda

        indice_encontrado <- Controlador_BuscarPorCelular(celulares, num_reservaciones, celular_busqueda)

        Si indice_encontrado = -1 Entonces
            Vista_MostrarError("No se encontró ninguna reservación con ese número de celular.")
        SiNo
            Escribir "Reservación encontrada para: ", nombres[indice_encontrado]
            Escribir "¿Está seguro de que desea cancelar esta reservación? (S/N)"
            Leer confirmacion

            Si Minusculas(confirmacion) = "s" Entonces
                Para j <- indice_encontrado Hasta num_reservaciones - 2 Hacer
                    nombres[j] <- nombres[j+1]
                    celulares[j] <- celulares[j+1]
                    dias[j] <- dias[j+1]
                    horas[j] <- horas[j+1]
                FinPara

                num_reservaciones <- num_reservaciones - 1
                Vista_MostrarMensajeExito("¡Reservación cancelada!")
            SiNo
                Escribir "Operación cancelada."
            FinSi
        FinSi
    FinSi
FinSubProceso

// --- Lógica de Persistencia (Manejo de Archivos) ---

// Carga las reservaciones desde "reservaciones.txt"
SubProceso Controlador_CargarReservaciones(nombres Por Referencia, celulares Por Referencia, dias Por Referencia, horas Por Referencia, num_reservaciones Por Referencia)
    Definir archivo, linea Como Caracter
    archivo <- "reservaciones.txt"
    num_reservaciones <- 0
    Abrir archivo Como 1 Para Lectura
    Si NO EsFin(1) Entonces
        Mientras NO EsFin(1) Hacer
            Leer 1, linea
            Si num_reservaciones < 100 Entonces
                nombres[num_reservaciones] <- ObtenerCampo(linea, 1)
                celulares[num_reservaciones] <- ObtenerCampo(linea, 2)
                dias[num_reservaciones] <- ObtenerCampo(linea, 3)
                horas[num_reservaciones] <- ObtenerCampo(linea, 4)
                num_reservaciones <- num_reservaciones + 1
            FinSi
        FinMientras
    SiNo
        Abrir archivo Como 1 Para Escritura; Cerrar 1;
        Abrir archivo Como 1 Para Lectura;
    FinSi
    Cerrar 1
FinSubProceso

// Guarda las reservaciones actuales en "reservaciones.txt"
SubProceso Controlador_GuardarReservaciones(nombres, celulares, dias, horas, num_reservaciones)
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

// --- Funciones Auxiliares del Controlador ---

// Busca un celular en el arreglo y devuelve su índice. Devuelve -1 si no lo encuentra.
Funcion indice <- Controlador_BuscarPorCelular(celulares, num_reservaciones, celular_busqueda)
    Definir indice, i Como Entero
    indice <- -1
    Para i <- 0 Hasta num_reservaciones - 1 Hacer
        Si celulares[i] = celular_busqueda Entonces
            indice <- i
            i <- num_reservaciones
        FinSi
    FinPara
FinFuncion

// Extrae el N-ésimo campo de una cadena CSV.
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
                i <- Longitud(linea)
            FinSi
            inicio_campo <- i + 1
        FinSi
    FinPara
    Si campo = "" Entonces
        campo <- Subcadena(linea, inicio_campo, Longitud(linea))
    FinSi
FinFuncion
