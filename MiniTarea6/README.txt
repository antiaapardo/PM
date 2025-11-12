# 🌱 SmartGarden - MiniTarea 1

Aplicación Android creada como práctica para demostrar el uso de **Toast**, **Snackbar**, **Notificaciones** y **menús**.  
Permite simular el registro de una planta, mostrar avisos, enviar notificaciones y navegar entre diferentes pantallas mediante un menú común.

---

## 🚀 Funcionalidades principales

### 🏠 Pantalla Principal (`MainActivity`)
- **Guardar datos:**  
  Al presionar el botón **GUARDAR**, aparece un diálogo de confirmación (`ConfirmDialog`).  
  - Si el usuario **acepta**, se muestra un **Snackbar** con el mensaje “Datos guardados”.  
  - Si el usuario **cancela**, se muestra un **Toast** con el mensaje “Operación cancelada”.

- **Mostrar aviso:**  
  El botón **MOSTRAR AVISO** muestra un **Snackbar** con la opción **"Deshacer"**.  
  Si el usuario presiona “Deshacer”, aparece un **Toast** confirmando que la acción fue cancelada.

- **Enviar notificación:**  
  El botón **ENVIAR NOTIFICACIÓN** crea una **notificación del sistema** con el mensaje:  
  “Riego automático activado a las 20:00 🌧️”.  
  Al tocar la notificación, la app se abre nuevamente.

---

### ⚙️ Pantalla de Ajustes (`SettingsActivity`)
- Muestra información de configuración.
- Contiene un botón **“Volver a Principal”** que muestra un **Toast** de confirmación.

---

### 🏡 Pantalla de Inicio (`HomeActivity`)
- Muestra un mensaje de bienvenida.
- Contiene un botón **“Volver a Principal”** con un **Toast** de confirmación.

---

### 📋 Menú superior (Toolbar)
Todas las pantallas comparten el mismo menú gracias a la clase base `BaseMenuActivity`.

El menú incluye:
- 🏠 **Inicio** → Abre `HomeActivity`
- ⚙️ **Ajustes** → Abre `SettingsActivity`
- 🚪 **Cerrar sesión** → Muestra un **Snackbar** confirmando la acción

---

## 💬 Diferencias entre Toast, Snackbar y Notificación

| Tipo | Descripción | Duración | Interacción | Dónde aparece |
|------|--------------|-----------|--------------|----------------|
| **Toast** | Mensaje corto y simple para el usuario. | Breve (automática) | ❌ No interactivo | En la parte inferior de la pantalla |
| **Snackbar** | Mensaje temporal que puede incluir una acción (botón). | Breve o indefinida | ✅ Tiene acción opcional (por ejemplo, “Deshacer”) | En la parte inferior del contenido de la app |
| **Notificación** | Mensaje persistente del sistema visible fuera de la app. | Hasta que el usuario la quite o interactúe | ✅ Interactiva, puede abrir actividades o ejecutar acciones | En la bandeja del sistema (barra de estado) |

---

## 🛠️ Tecnologías utilizadas
- **Kotlin**
- **Android SDK**
- **AndroidX / Material Components**
- **NotificationCompat**
- **Snackbar (Material Design)**
- **Toast**

---

## 📱 Capturas (opcional)
Agrega aquí imágenes de las pantallas:
