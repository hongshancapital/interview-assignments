# Short URL

Another short url service, based on TypeScript, Node.js and Express.js

## Prerequisites
- node version >= 18.12.1
- pnpm version >= 7.20.0
- PostgresSQL 11.15

## Getting Started

### Prepare

Install dependencies
```shell
pnpm install
```

Generate prisma client
```shell
pnpm run prisma 
```

### Initial Database

Create `.env` file and config DATABASE_URL,
```.dotenv
DATABASE_URL=postgresql://<USER>:<PASSWORD>@<HOST>:<PORT>/<DATABASE>?schema=<SCHEMA>
```
 
Then run migration
```shell
# init production database
npx prisma migration deploy 
```
or 
```shell
# initial a development db
npx prisma db push
```
> Check https://www.prisma.io/docs for more usage.

### Run the app
Start in dev mode:
```shell
pnpm run dev
```

Start in dev mode with watch:
```shell
pnpm run dev:watch
```

Start in prodaction mode:
```shell
pnpm start
```

### Run Test
Run Unit Test:
```shell
pnpm run test:unit
```

## Configuration

Create a `.env` or setup environment variables.

| Name           | Description                               | Default |
|----------------|-------------------------------------------|---------|
| `PORT`         | Desired host port used for http requests. | 3000    |
| `DATABASE_URL` | database connection config                |         |
