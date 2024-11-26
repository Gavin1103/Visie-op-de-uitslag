# Stage 1: Build the app
FROM node:lts-alpine AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy project files
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve the app
FROM node:lts-alpine AS runtime

# Install a lightweight HTTP server
RUN npm install -g http-server

# Copy only the build output from the previous stage
WORKDIR /app
COPY --from=build /app/dist /app/dist

# Start the server
CMD ["http-server", "dist", "-p", "7419"]