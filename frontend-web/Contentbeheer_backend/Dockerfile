# Use a base image with Node.js to build the Angular app
FROM node:18 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install --production

# Copy the rest of the app
COPY . .

# Build the Angular application
RUN npm run build --prod

# Use an Nginx image to serve the Angular app
FROM nginx:1.21

# Copy the build files to the Nginx directory
COPY --from=build /app/dist/your-angular-app /usr/share/nginx/html

# Copy a custom Nginx configuration file (optional)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Set the port that the application will use
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]