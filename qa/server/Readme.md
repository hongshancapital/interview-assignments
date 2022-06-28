# QA Engineer's toolbox: server

Please provide a test suite for this project, and submit your PR.

PS: Mind pointing out some potential flaws or pitfalls?


## API

### get '/toolbox/options'

Provide options for client to choose

### post '/toolbox/create'

Create entry

```
{name: string, tools: string}
```

### get '/toolbox'

List entry

## Test - Just implement part of happy path tests for API level, not include unit test

### Mock test - No need to start the server
```
yarn test:mock
```

### Integration test - Need to start the server
```
yarn test:integration
```