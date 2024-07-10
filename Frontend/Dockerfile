# Etapa de construcción
FROM node:22-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /home/app

# Copia los archivos necesarios para instalar dependencias
COPY package*.json ./
COPY next.config.mjs ./

# Instala las dependencias
RUN npm install

# Copia el resto de los archivos del proyecto
COPY . .

# Construye la aplicación Next.js
RUN npm run build


# Etapa de producción
FROM nginx:1.27.0-alpine

# Elimina los archivos predeterminados de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copia los archivos estáticos generados en la etapa de construcción
COPY --from=builder /home/app/out /usr/share/nginx/html/

# Copia la configuración de Nginx
COPY --from=builder /home/app/nginx.conf /etc/nginx/conf.d/default.conf

# Expone el puerto en el que se ejecutará la aplicación
EXPOSE 80

# Define el comando predeterminado para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
