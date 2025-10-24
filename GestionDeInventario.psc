Proceso GestionDeInventario

    // Dimension máxima de productos que podemos almacenar
    Definir MAX_PRODUCTOS Como Entero
    MAX_PRODUCTOS <- 100

    // Simulación de la estructura Producto con arreglos paralelos
    Dimension codigos[MAX_PRODUCTOS], cantidades[MAX_PRODUCTOS]
    Dimension nombres[MAX_PRODUCTOS], marcas[MAX_PRODUCTOS], descripciones[MAX_PRODUCTOS]
    Dimension precios[MAX_PRODUCTOS]

    // Arreglo para simular los punteros "siguiente" de la lista enlazada
    Dimension siguientes[MAX_PRODUCTOS]

    // Variables para controlar la lista enlazada
    // 'inicio' guarda el índice del primer elemento. -1 significa lista vacía (NULL)
    // 'disponible' guarda el próximo índice libre en los arreglos.
    Definir inicio, disponible Como Entero
    inicio <- -1
    disponible <- 0

    Definir opcion Como Entero

    Repetir
        Escribir ""
        Escribir "====== MENU PRINCIPAL ======"
        Escribir "1. Ingresar productos"
        Escribir "2. Ver todos los productos"
        Escribir "3. Buscar productos"
        Escribir "4. Eliminar productos"
        Escribir "5. Salir"
        Escribir "Seleccione una opcion: " Sin Saltar
        Leer opcion

        Segun opcion Hacer
            1:
                MenuIngresar(inicio, disponible, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX_PRODUCTOS)
            2:
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            3:
                MenuBuscar(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            4:
                MenuEliminar(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            5:
                inicio <- -1 // Simula eliminar la lista completa
                Escribir "Saliendo del programa..."
            De Otro Modo:
                Escribir "Opción inválida."
        FinSegun

    Hasta Que opcion = 5
FinProceso


// --- SUBPROCESOS DE MENÚS ---

SubProceso MenuIngresar(inicio Por Ref, disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
    Definir op2 Como Entero
    Repetir
        Escribir ""
        Escribir "--- MENU INGRESAR PRODUCTO ---"
        Escribir "1. Insertar al inicio"
        Escribir "2. Insertar al final"
        Escribir "3. Insertar antes de X (código)"
        Escribir "4. Insertar después de X (código)"
        Escribir "5. Volver al menú principal"
        Escribir "Seleccione una opción: " Sin Saltar
        Leer op2

        Segun op2 Hacer
            1:
                InsertarInicio(inicio, disponible, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            2:
                InsertarFinal(inicio, disponible, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            3:
                InsertarAntesDeX(inicio, disponible, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            4:
                InsertarDespuesDeX(inicio, disponible, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            5:
                // No hace nada, solo vuelve
            De Otro Modo:
                Escribir "Opción inválida."
        FinSegun
    Hasta Que op2 = 5
FinSubProceso

SubProceso MenuBuscar(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Definir op Como Entero
    Escribir ""
    Escribir "--- MENU BUSCAR PRODUCTO ---"
    Escribir "1. Buscar por código"
    Escribir "2. Buscar por nombre"
    Escribir "Seleccione una opción: " Sin Saltar
    Leer op

    Si op = 1 Entonces
        BuscarPorCodigo(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Sino
        Si op = 2 Entonces
            BuscarPorNombre(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
        Sino
            Escribir "Opción inválida."
        FinSi
    FinSi
FinSubProceso

SubProceso MenuEliminar(inicio Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Definir op3 Como Entero
    Repetir
        Escribir ""
        Escribir "--- MENU ELIMINAR PRODUCTO ---"
        Escribir "1. Eliminar al inicio"
        Escribir "2. Eliminar al final"
        Escribir "3. Eliminar X producto (por código)"
        Escribir "4. Volver al menú principal"
        Escribir "Seleccione una opción: " Sin Saltar
        Leer op3

        Segun op3 Hacer
            1:
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
                EliminarInicio(inicio, siguientes)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            2:
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
                EliminarFinal(inicio, siguientes)
                MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            3:
                EliminarXProducto(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
            4:
                // No hace nada
            De Otro Modo:
                Escribir "Opción inválida."
        FinSegun
    Hasta Que op3 = 4
FinSubProceso


// --- SUBPROCESOS DE LÓGICA DE LA LISTA ---

// Lee los datos y los coloca en los arreglos en la posición 'disponible'
SubProceso nuevoIndice <- CrearProducto(disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, MAX)
    Si disponible >= MAX Entonces
        Escribir "Error: No hay más espacio para productos."
        nuevoIndice <- -1
    Sino
        Escribir "Ingrese código: " Sin Saltar
        Leer codigos[disponible]
        Escribir "Ingrese nombre: " Sin Saltar
        Leer nombres[disponible]
        Escribir "Ingrese marca: " Sin Saltar
        Leer marcas[disponible]
        Escribir "Ingrese descripción: " Sin Saltar
        Leer descripciones[disponible]
        Escribir "Ingrese cantidad en existencia: " Sin Saltar
        Leer cantidades[disponible]
        Escribir "Ingrese precio: " Sin Saltar
        Leer precios[disponible]

        nuevoIndice <- disponible
        disponible <- disponible + 1
    FinSi
FinSubProceso

SubProceso InsertarInicio(inicio Por Ref, disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
    Definir nuevoIndice Como Entero
    nuevoIndice <- CrearProducto(disponible, codigos, nombres, marcas, descripciones, cantidades, precios, MAX)

    Si nuevoIndice <> -1 Entonces
        siguientes[nuevoIndice] <- inicio
        inicio <- nuevoIndice
    FinSi
FinSubProceso

SubProceso InsertarFinal(inicio Por Ref, disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
    Definir nuevoIndice Como Entero
    nuevoIndice <- CrearProducto(disponible, codigos, nombres, marcas, descripciones, cantidades, precios, MAX)

    Si nuevoIndice <> -1 Entonces
        siguientes[nuevoIndice] <- -1
        Si inicio = -1 Entonces
            inicio <- nuevoIndice
        Sino
            Definir temp Como Entero
            temp <- inicio
            Mientras siguientes[temp] <> -1 Hacer
                temp <- siguientes[temp]
            FinMientras
            siguientes[temp] <- nuevoIndice
        FinSi
    FinSi
FinSubProceso

SubProceso InsertarAntesDeX(inicio Por Ref, disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
    Si inicio = -1 Entonces
        Escribir "La lista está vacía."
    Sino
        Definir codigoX, nuevoIndice, temp, ant Como Entero
        Escribir "Ingrese el código de referencia (X): " Sin Saltar
        Leer codigoX

        temp <- inicio
        ant <- -1
        Mientras temp <> -1 Y codigos[temp] <> codigoX Hacer
            ant <- temp
            temp <- siguientes[temp]
        FinMientras

        Si temp = -1 Entonces
            Escribir "No se encontró el producto con ese código."
        Sino
            nuevoIndice <- CrearProducto(disponible, codigos, nombres, marcas, descripciones, cantidades, precios, MAX)
            Si nuevoIndice <> -1 Entonces
                Si ant = -1 Entonces // Insertar al principio
                    siguientes[nuevoIndice] <- inicio
                    inicio <- nuevoIndice
                Sino
                    siguientes[ant] <- nuevoIndice
                    siguientes[nuevoIndice] <- temp
                FinSi
            FinSi
        FinSi
    FinSi
FinSubProceso

SubProceso InsertarDespuesDeX(inicio Por Ref, disponible Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes, MAX)
    Si inicio = -1 Entonces
        Escribir "La lista está vacía."
    Sino
        Definir codigoX, nuevoIndice, temp Como Entero
        Escribir "Ingrese el código de referencia (X): " Sin Saltar
        Leer codigoX

        temp <- inicio
        Mientras temp <> -1 Y codigos[temp] <> codigoX Hacer
            temp <- siguientes[temp]
        FinMientras

        Si temp = -1 Entonces
            Escribir "No se encontró el producto con ese código."
        Sino
            nuevoIndice <- CrearProducto(disponible, codigos, nombres, marcas, descripciones, cantidades, precios, MAX)
            Si nuevoIndice <> -1 Entonces
                siguientes[nuevoIndice] <- siguientes[temp]
                siguientes[temp] <- nuevoIndice
            FinSi
        FinSi
    FinSi
FinSubProceso

SubProceso MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Si inicio = -1 Entonces
        Escribir ""
        Escribir "No hay productos en la lista."
    Sino
        Definir temp Como Entero
        temp <- inicio
        Escribir ""
        Escribir "=== LISTA DE PRODUCTOS ==="
        Mientras temp <> -1 Hacer
            Escribir "Código: ", codigos[temp]
            Escribir "Nombre: ", nombres[temp]
            Escribir "Marca: ", marcas[temp]
            Escribir "Descripción: ", descripciones[temp]
            Escribir "Cantidad: ", cantidades[temp]
            Escribir "Precio: ", precios[temp]
            Escribir "-----------------------------"
            temp <- siguientes[temp]
        FinMientras
    FinSi
FinSubProceso

SubProceso BuscarPorCodigo(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Definir codigoBusca, temp Como Entero
    Escribir "Ingrese el código del producto: " Sin Saltar
    Leer codigoBusca

    temp <- inicio
    Definir encontrado Como Logico
    encontrado <- Falso

    Mientras temp <> -1 Y NO encontrado Hacer
        Si codigos[temp] = codigoBusca Entonces
            encontrado <- Verdadero
        Sino
            temp <- siguientes[temp]
        FinSi
    FinMientras

    Si encontrado Entonces
        Escribir ""
        Escribir "Producto encontrado:"
        Escribir "Nombre: ", nombres[temp], ", Marca: ", marcas[temp]
        Escribir "Descripción: ", descripciones[temp]
        Escribir "Cantidad: ", cantidades[temp], ", Precio: ", precios[temp]
    Sino
        Escribir "Producto no encontrado."
    FinSi
FinSubProceso

SubProceso BuscarPorNombre(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Definir nombreBusca Como Caracter
    Definir temp Como Entero
    Escribir "Ingrese el nombre del producto: " Sin Saltar
    Leer nombreBusca

    temp <- inicio
    Definir encontrado Como Logico
    encontrado <- Falso

    Mientras temp <> -1 Y NO encontrado Hacer
        Si nombres[temp] = nombreBusca Entonces
            encontrado <- Verdadero
        Sino
            temp <- siguientes[temp]
        FinSi
    FinMientras

    Si encontrado Entonces
        Escribir ""
        Escribir "Producto encontrado:"
        Escribir "Código: ", codigos[temp], ", Marca: ", marcas[temp]
        Escribir "Descripción: ", descripciones[temp]
        Escribir "Cantidad: ", cantidades[temp], ", Precio: ", precios[temp]
    Sino
        Escribir "Producto no encontrado."
    FinSi
FinSubProceso

SubProceso EliminarInicio(inicio Por Ref, siguientes)
    Si inicio = -1 Entonces
        Escribir "La lista está vacía."
    Sino
        inicio <- siguientes[inicio]
        Escribir "Producto eliminado al inicio."
    FinSi
FinSubProceso

SubProceso EliminarFinal(inicio Por Ref, siguientes)
    Si inicio = -1 Entonces
        Escribir "La lista está vacía."
    Sino
        Si siguientes[inicio] = -1 Entonces // Solo hay un elemento
            inicio <- -1
        Sino
            Definir temp, ant Como Entero
            temp <- inicio
            ant <- -1
            Mientras siguientes[temp] <> -1 Hacer
                ant <- temp
                temp <- siguientes[temp]
            FinMientras
            siguientes[ant] <- -1
        FinSi
        Escribir "Producto eliminado al final."
    FinSi
FinSubProceso

SubProceso EliminarXProducto(inicio Por Ref, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
    Si inicio = -1 Entonces
        Escribir "La lista está vacía."
    Sino
        Definir codigoEliminar, temp, ant, resp Como Entero
        Escribir "Ingrese el código del producto a eliminar: " Sin Saltar
        Leer codigoEliminar

        temp <- inicio
        ant <- -1

        Mientras temp <> -1 Y codigos[temp] <> codigoEliminar Hacer
            ant <- temp
            temp <- siguientes[temp]
        FinMientras

        Si temp = -1 Entonces
            Escribir "Producto no encontrado."
        Sino
            Escribir ""
            Escribir "Producto encontrado:"
            Escribir "Código: ", codigos[temp], ", Nombre: ", nombres[temp]
            Escribir "¿Desea eliminar este producto? (1=Sí / 0=No): " Sin Saltar
            Leer resp

            Si resp = 1 Entonces
                Si ant = -1 Entonces // Es el primer elemento
                    inicio <- siguientes[temp]
                Sino
                    siguientes[ant] <- siguientes[temp]
                FinSi
                Escribir "Producto eliminado."
            Sino
                Escribir "El producto sigue en la lista."
            FinSi

            MostrarProductos(inicio, codigos, nombres, marcas, descripciones, cantidades, precios, siguientes)
        FinSi
    FinSi
FinSubProceso
