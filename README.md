# Fullstack Java Project

## Diego Russotto (3AONC)

## Folder structure

- Readme.md
- _architecture_: this folder contains documentation regarding the architecture of your system.
- `docker-compose.yml` : to start the backend (starts all microservices)
- _backend-java_: contains microservices written in java
- _demo-artifacts_: contains images, files, etc that are useful for demo purposes.
- _frontend-web_: contains the Angular webclient

Each folder contains its own specific `.gitignore` file.  
**:warning: complete these files asap, so you don't litter your repository with binary build artifacts!**

## How to setup and run this application

### Backend Setup

**Local Setup**  
To run the backend locally, first start the databases by running the `docker-compose.yml`.
    Then, manually start the services in the following order:
    - `config-service`
    - `discovery-service`
    - `gateway-service`
    - Then, start the other services as needed.

### Frontend Setup

**Local Setup**  
1. Navigate to the `frontend-web/Contentbeheer_backend` directory.
2. Install the necessary dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   ng serve
   ```
   The frontend will be available at `http://localhost:4200`

**Docker Ready**
1. Uncomment the frontend section in the `docker-compose.yml` file.
2. Run the `docker-compose.yml` file.
