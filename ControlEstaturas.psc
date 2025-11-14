Proceso ControlEstaturas
    // Descripción: Programa para el control de estaturas del jardín de niños "Niños Felices".
    // Análisis: Este programa utiliza arreglos paralelos para almacenar los datos de 98 niños,
    // incluyendo nombre, edad y estatura. Un menú interactivo permite al usuario realizar
    // operaciones como registrar, mostrar, buscar y modificar los datos de los niños.
    // Autor: Jules
    // Fecha: 14/11/2025

    Dimension nombres[98], edades[98], estaturas[98]
    Definir contador Como Entero
    contador <- 0
    Definir opcion Como Caracter

    Repetir
        Escribir " "
        Escribir "Jardín de Niños 'Niños Felices'"
        Escribir "Menú Principal"
        Escribir "1. Ingresar Niño"
        Escribir "2. Mostrar Todos"
        Escribir "3. Buscar por Nombre"
        Escribir "4. Mostrar por Edad"
        Escribir "5. Mostrar por Estatura"
        Escribir "6. Modificar"
        Escribir "7. Salir"
        Escribir "Seleccione una opción:"
        Leer opcion

        Segun opcion Hacer
            "1":
                IngresarNino(nombres, edades, estaturas, contador)
            "2":
                MostrarTodos(nombres, edades, estaturas, contador)
            "3":
                BuscarPorNombre(nombres, edades, estaturas, contador)
            "4":
                MostrarPorEdad(nombres, edades, estaturas, contador)
            "5":
                MostrarPorEstatura(nombres, edades, estaturas, contador)
            "6":
                Modificar(nombres, edades, estaturas, contador)
            "7":
                Escribir "Saliendo del programa."
            De Otro Modo:
                Escribir "Opción no válida. Intente de nuevo."
        FinSegun
    Hasta Que opcion = "7"
FinProceso

SubProceso IngresarNino(nombres Por Ref, edades Por Ref, estaturas Por Ref, contador Por Ref)
    Si contador >= 98 Entonces
        Escribir "El registro está lleno."
    Sino
        Definir edadTemp Como Entero
        Escribir "Ingrese el nombre del niño:"
        Leer nombres[contador]
        Repetir
            Escribir "Ingrese la edad del niño (entre 3 y 6 años):"
            Leer edadTemp
            Si edadTemp < 3 O edadTemp > 6 Entonces
                Escribir "Edad fuera de rango. Intente de nuevo."
            FinSi
        Hasta Que edadTemp >= 3 Y edadTemp <= 6
        edades[contador] <- edadTemp
        Escribir "Ingrese la estatura del niño (en cm):"
        Leer estaturas[contador]
        contador <- contador + 1
        Escribir "Niño registrado exitosamente."
    FinSi
FinSubProceso

SubProceso MostrarTodos(nombres, edades, estaturas, contador)
    Si contador = 0 Entonces
        Escribir "No hay niños registrados."
    Sino
        Escribir "Lista de Niños:"
        Para i <- 0 Hasta contador - 1 Hacer
            Escribir "Registro No. ", i + 1
            Escribir "  Nombre: ", nombres[i]
            Escribir "  Edad: ", edades[i], " años"
            Escribir "  Estatura: ", estaturas[i], " cm"
        FinPara
    FinSi
FinSubProceso

SubProceso BuscarPorNombre(nombres, edades, estaturas, contador)
    Si contador = 0 Entonces
        Escribir "No hay niños registrados."
    Sino
        Definir nombreBuscado Como Caracter
        Definir hallado Como Logico
        hallado <- Falso
        Escribir "Ingrese el nombre del niño a buscar:"
        Leer nombreBuscado

        Para i <- 0 Hasta contador - 1 Hacer
            Si nombres[i] = nombreBuscado Entonces
                Escribir "Registro No. ", i + 1
                Escribir "  Edad: ", edades[i], " años"
                Escribir "  Estatura: ", estaturas[i], " cm"
                hallado <- Verdadero
            FinSi
        FinPara

        Si NO hallado Entonces
            Escribir "No se encontró ningún niño con ese nombre."
        FinSi
    FinSi
FinSubProceso

SubProceso MostrarPorEdad(nombres, edades, estaturas, contador)
    Si contador = 0 Entonces
        Escribir "No hay niños registrados."
    Sino
        Definir edadBuscada Como Entero
        Definir hallado Como Logico
        hallado <- Falso
        Escribir "Ingrese la edad a mostrar (entre 3 y 6 años):"
        Leer edadBuscada

        Si edadBuscada < 3 O edadBuscada > 6 Entonces
            Escribir "Edad fuera de rango."
        Sino
            Para i <- 0 Hasta contador - 1 Hacer
                Si edades[i] = edadBuscada Entonces
                    Escribir "Nombre: ", nombres[i]
                    Escribir "  Estatura: ", estaturas[i], " cm"
                    hallado <- Verdadero
                FinSi
            FinPara

            Si NO hallado Entonces
                Escribir "No se encontraron niños con esa edad."
            FinSi
        FinSi
    FinSi
FinSubProceso

SubProceso MostrarPorEstatura(nombres, edades, estaturas, contador)
    Si contador = 0 Entonces
        Escribir "No hay niños registrados."
    Sino
        Definir estaturaBuscada Como Entero
        Definir hallado Como Logico
        hallado <- Falso
        Escribir "Ingrese la estatura a mostrar (en cm):"
        Leer estaturaBuscada

        Para i <- 0 Hasta contador - 1 Hacer
            Si estaturas[i] = estaturaBuscada Entonces
                Escribir "Nombre: ", nombres[i]
                Escribir "  Edad: ", edades[i], " años"
                hallado <- Verdadero
            FinSi
        FinPara

        Si NO hallado Entonces
            Escribir "No se encontraron niños con esa estatura."
        FinSi
    FinSi
FinSubProceso

SubProceso Modificar(nombres Por Ref, edades Por Ref, estaturas Por Ref, contador)
    Si contador = 0 Entonces
        Escribir "No hay niños registrados para modificar."
    Sino
        Definir opcionMod, registro Como Entero
        Escribir "Submenú Modificar"
        Escribir "1. Modificar Nombre"
        Escribir "2. Modificar Edad"
        Escribir "3. Modificar Estatura"
        Escribir "Seleccione una opción:"
        Leer opcionMod

        Segun opcionMod Hacer
            1:
                Escribir "Ingrese el número de registro del niño a modificar (1-", contador, "):"
                Leer registro
                Si registro > 0 Y registro <= contador Entonces
                    Escribir "Ingrese el nuevo nombre:"
                    Leer nombres[registro - 1]
                    Escribir "Nombre modificado exitosamente."
                Sino
                    Escribir "Número de registro no válido."
                FinSi
            2:
                Escribir "Ingrese el número de registro del niño a modificar (1-", contador, "):"
                Leer registro
                Si registro > 0 Y registro <= contador Entonces
                    Definir edadTemp Como Entero
                    Repetir
                        Escribir "Ingrese la nueva edad (entre 3 y 6 años):"
                        Leer edadTemp
                        Si edadTemp < 3 O edadTemp > 6 Entonces
                            Escribir "Edad fuera de rango. Intente de nuevo."
                        FinSi
                    Hasta Que edadTemp >= 3 Y edadTemp <= 6
                    edades[registro - 1] <- edadTemp
                    Escribir "Edad modificada exitosamente."
                Sino
                    Escribir "Número de registro no válido."
                FinSi
            3:
                Escribir "Ingrese el número de registro del niño a modificar (1-", contador, "):"
                Leer registro
                Si registro > 0 Y registro <= contador Entonces
                    Escribir "Ingrese la nueva estatura (en cm):"
                    Leer estaturas[registro - 1]
                    Escribir "Estatura modificada exitosamente."
                Sino
                    Escribir "Número de registro no válido."
                FinSi
            De Otro Modo:
                Escribir "Opción no válida."
        FinSegun
    FinSi
FinSubProceso
