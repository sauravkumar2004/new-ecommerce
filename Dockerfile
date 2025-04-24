# Build stage
FROM node:18-alpine AS build

WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Create public directory and copy index.html
RUN mkdir -p public
COPY index.html public/
COPY public/* public/

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Verify build output
RUN ls -la /app/build

# Production stage
FROM nginx:alpine

# Create directory for logs
RUN mkdir -p /var/log/nginx

# Copy the built files from the build stage
COPY --from=build /app/build /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Verify the files are copied correctly
RUN ls -la /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"] 