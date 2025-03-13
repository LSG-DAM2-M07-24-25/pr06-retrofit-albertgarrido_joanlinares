pr06-retrofit-albertgarrido_joanlinares
=================

📖 Descripción
--------------

Esta aplicación Android desarrollada en Kotlin con Jetpack Compose permite explorar una colección de cartas Pokémon provenientes de la API [Pokémon TCG](https://api.pokemontcg.io/v2/). La aplicación sigue la arquitectura MVVM para asegurar una separación clara de responsabilidades y facilitar su escalabilidad y mantenimiento.

🚀 Características Principales
------------------------------

### 🛠️ Arquitectura MVVM

-   **📚 Model:** Gestión eficiente de datos con Room (local) y Retrofit (remota).
-   **🎨 View:** Interfaces Compose adaptables y responsivas.
-   **🧠 ViewModel:** Lógica desacoplada, manteniendo la UI limpia y reactiva.

### 📱 Diseño Responsive

-   Adaptado para pantallas pequeñas, medianas y grandes en modo vertical y horizontal.
-   📱Tablet:

<img src="https://github.com/user-attachments/assets/2b73474e-b07b-4587-a225-bf25da986bd3" alt="Pantalla Vertical" height="400"/>
<img src="https://github.com/user-attachments/assets/3297f532-46c8-4c67-a25e-1508f98ada42" alt="Pantalla Horizontal" width="400"/>

-   📱Movil:
<img src="https://github.com/user-attachments/assets/fde5b0a9-c2c6-45ee-86f1-b0f731a6b341" alt="Pantalla Horizontal" width="200"/>
<img src="https://github.com/user-attachments/assets/d495e6ea-af6e-4a40-959d-dd2097f3936c" alt="Pantalla Horizontal" height="200"/>



### 🃏 Lista de Cartas

-   Explora cartas obtenidas directamente desde la API Pokémon TCG.

### 🔍 Detalle de Carta

-   Vista completa con imagen detallada, tipo y precio de mercado actualizado.
<img src="https://github.com/user-attachments/assets/d3ca9824-9ba8-42a3-b793-68a58dabb563" alt="Pantalla Horizontal" width="400"/>
<img src="https://github.com/user-attachments/assets/20457531-be73-4fb3-a228-a8861e673639" alt="Pantalla Horizontal" height="400"/>

### 🛒 Carrito

-   Añade cartas al carrito, gestionadas con persistencia local mediante Room.
<img src="https://github.com/user-attachments/assets/59f34c53-a71f-40af-9f3a-fd94ce5ba4f7" alt="Pantalla Horizontal" width="400"/>



### 🔎 Barra de Búsqueda Inteligente

-   Búsqueda rápida con historial de términos recientes.
-   Gestión y limpieza sencilla del historial de búsquedas.
<img src="https://github.com/user-attachments/assets/62e684a1-4d3c-4e85-ad85-869a392d6619" alt="Pantalla Horizontal" width="400"/>
<img src="https://github.com/user-attachments/assets/e67f75fb-8f38-45e9-9a42-bc20cfffe4b1" alt="Pantalla Horizontal" width="400"/>


💾 Base de Datos
----------------

-   Room para persistencia local de cartas favoritas y búsquedas recientes.

⚙️ Tecnologías Utilizadas
-------------------------

-   **🌐 Retrofit:** Consumo eficiente de servicios web REST.
-   **📂 Room:** Persistencia local optimizada.
-   **⚡ Corrutinas:** Manejo asíncrono de operaciones.
-   **🖼️ Coil:** Carga eficiente de imágenes remotas.
-   **🌀 LiveData:** Gestión reactiva de estado en la UI.

📂 Estructura del Proyecto
--------------------------

```
com.example.pr06_retrofit_albertgarrido_joanlinares
├── api
│   ├── APIInterface.kt
│   └── CardRepository.kt
├── model
│   └── (Modelos de datos)
├── room
│   └── Repository.kt
├── ui
│   ├── theme (Temas personalizados)
│   └── util
│       ├── CardItem.kt
│       ├── CardList.kt
│       └── SearchBar.kt
├── view
│   ├── EntryPoint.kt
│   ├── HomeView.kt
│   ├── CardDetails.kt
│   ├── CartView.kt
│   └── SearchView.kt
├── viewmodel
│   ├── CartViewModel.kt
│   ├── HomeViewModel.kt
│   ├── CardListViewModel.kt
│   └── SearchBarViewModel.kt

```

✨ Autores
---------

-   👨‍💻 Albert Garrido
-   👨‍💻 Joan Linares
