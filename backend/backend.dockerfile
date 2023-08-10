FROM node:current-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

ENV PORT=5000

RUN npm run build
EXPOSE $PORT

CMD ["npm", "start:dev"]
