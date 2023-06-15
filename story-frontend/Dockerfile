FROM node:18.16.0-alpine3.18 as react-build-stage

WORKDIR /app
COPY . /app/

RUN npm install --silent
RUN npm run test
RUN npm run build

FROM nginx:1.17.8-alpine

COPY --from=react-build-stage /app/build /var/www
COPY --from=react-build-stage /app/nginx/storysite.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
