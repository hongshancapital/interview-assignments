## Development

### Preliminary

- `docker`, `docker compose`, `git`, `node`, `typescript` installed
- A running docker daemon

### Instructions

#### 1. Clone the codebase to your local machine

```bash
git clone https://github.com/floralain/domain.git && cd domain
```

#### 2. Docker compose up

```
docker compose up --build
```

#### 3. Install dependencies

- npm dependencies
  
```bash
npm i
```

#### 4. Start server

```bash
npm run dev
```

## Test

### You can see the test result in coverage files

#### 1. Docker compose up

```
docker compose up --build
```

#### 2. Test unit

```
npm run test:unit
```

#### 3. Test e2e

```
npm run test:e2e
```