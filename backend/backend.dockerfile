FROM node:16.20.1-alpine3.18

WORKDIR /app

COPY package.json ./

RUN yarn install

COPY . .

ENV PORT=5000

EXPOSE $PORT

CMD ["yarn", "start:dev"]
