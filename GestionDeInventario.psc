Proceso GestionDeInventario
    // Descripción: Programa para gestionar el inventario de CDs.
    // Análisis: Este programa utiliza arreglos paralelos para almacenar
    // los datos de los CDs y un menú interactivo para realizar operaciones
    // como agregar, mostrar, buscar y vender CDs.
    // Autor: Jules
    // Fecha: 13/11/2025

    Dimension nombres[50], artistas[50]
    Dimension anios[50], existencias[50]
    Dimension precios[50]
    Definir contador Como Entero
    contador <- 0
    Definir opcion Como Caracter

    Repetir
        Escribir " "
        Escribir "Menú Principal"
        Escribir "1. Ingresar un nuevo CD"
        Escribir "2. Mostrar CDs"
        Escribir "3. Mostrar por nombre del artista"
        Escribir "4. Venta"
        Escribir "5. Salir"
        Escribir "Seleccione una opción:"
        Leer opcion

        Segun opcion Hacer
            "1":
                IngresarCD(nombres, artistas, anios, precios, existencias, contador)
            "2":
                MostrarCDs(nombres, artistas, anios, precios, existencias, contador)
            "3":
                MostrarPorArtista(nombres, artistas, anios, precios, existencias, contador)
            "4":
                Venta(nombres, precios, existencias, contador)
            "5":
                Escribir "Saliendo del programa."
            De Otro Modo:
                Escribir "Opción no válida. Intente de nuevo."
        FinSegun
    Hasta Que opcion = "5"
FinProceso

SubProceso IngresarCD(nombres Por Ref, artistas Por Ref, anios Por Ref, precios Por Ref, existencias Por Ref, contador Por Ref)
    Si contador >= 50 Entonces
        Escribir "El inventario está lleno."
    Sino
        Escribir "Ingrese el nombre del CD:"
        Leer nombres[contador]
        Escribir "Ingrese el nombre del artista:"
        Leer artistas[contador]
        Escribir "Ingrese el año:"
        Leer anios[contador]
        Escribir "Ingrese el precio:"
        Leer precios[contador]
        Escribir "Ingrese la cantidad en existencia:"
        Leer existencias[contador]
        contador <- contador + 1
        Escribir "CD agregado exitosamente."
    FinSi
FinSubProceso

SubProceso MostrarCDs(nombres, artistas, anios, precios, existencias, contador)
    Si contador = 0 Entonces
        Escribir "No hay CDs en el inventario."
    Sino
        Escribir "Lista de CDs:"
        Para i <- 0 Hasta contador - 1 Hacer
            Escribir "CD ", i + 1, ":"
            Escribir "  Nombre: ", nombres[i]
            Escribir "  Artista: ", artistas[i]
            Escribir "  Año: ", anios[i]
            Escribir "  Precio: $", precios[i]
            Escribir "  Existencia: ", existencias[i]
        FinPara
    FinSi
FinSubProceso

SubProceso MostrarPorArtista(nombres, artistas, anios, precios, existencias, contador)
    Si contador = 0 Entonces
        Escribir "No hay CDs en el inventario."
    Sino
        Definir artistaBuscado Como Caracter
        Definir hallado Como Logico
        hallado <- Falso
        Escribir "Ingrese el nombre del artista:"
        Leer artistaBuscado

        Para i <- 0 Hasta contador - 1 Hacer
            Si artistas[i] = artistaBuscado Entonces
                Escribir "Nombre: ", nombres[i]
                Escribir "Artista: ", artistas[i]
                Escribir "Año: ", anios[i]
                Escribir "Precio: $", precios[i]
                Escribir "Existencia: ", existencias[i]
                hallado <- Verdadero
            FinSi
        FinPara

        Si NO hallado Entonces
            Escribir "No se encontraron CDs del artista: ", artistaBuscado
        FinSi
    FinSi
FinSubProceso

SubProceso Venta(nombres, precios, existencias Por Ref, contador)
    Si contador = 0 Entonces
        Escribir "No hay CDs para vender."
    Sino
        Definir cdBuscado Como Caracter
        Definir indiceEncontrado Como Entero
        indiceEncontrado <- -1
        Escribir "Ingrese el nombre del CD a vender:"
        Leer cdBuscado

        Para i <- 0 Hasta contador - 1 Hacer
            Si nombres[i] = cdBuscado Entonces
                indiceEncontrado <- i
            FinSi
        FinPara

        Si indiceEncontrado = -1 Entonces
            Escribir "El CD no se encuentra en el inventario."
        Sino
            Definir cantidad, total, pago, cambio Como Real
            Escribir "CD: ", nombres[indiceEncontrado], " - Existencia: ", existencias[indiceEncontrado]
            Escribir "¿Cuántos desea comprar?"
            Leer cantidad

            Si cantidad <= 0 Entonces
                Escribir "La cantidad debe ser mayor a cero."
            Sino
                Si cantidad > existencias[indiceEncontrado] Entonces
                    Escribir "No hay suficientes existencias."
                Sino
                    total <- cantidad * precios[indiceEncontrado]
                    Escribir "El total a pagar es: $", total
                    Escribir "Ingrese el monto del pago:"
                    Leer pago

                    Si pago < total Entonces
                        Escribir "El pago es insuficiente."
                    Sino
                        cambio <- pago - total
                        existencias[indiceEncontrado] <- existencias[indiceEncontrado] - cantidad
                        Escribir "Venta exitosa:"
                        Escribir "Total a pagar: $", total
                        Escribir "Pagado: $", pago
                        Escribir "Cambio: $", cambio
                        Escribir "Existencia restante: ", existencias[indiceEncontrado]
                    FinSi
                FinSi
            FinSi
        FinSi
    FinSi
FinSubProceso
