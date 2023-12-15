# Use an official Node.js runtime as the base image
FROM node:14-alpine

WORKDIR /

COPY package*.json ./

# Install project dependencies
RUN npm install


COPY . .


RUN npm run build


EXPOSE 8081


CMD ["npm", "run", "serve"]
