FROM node:alpine:3.18.3

WORKDIR /app

COPY package.json ./

RUN yarn install

COPY . .

ENV PORT=5000

RUN yarn build
EXPOSE $PORT

CMD ["yarn", "start:dev"]
