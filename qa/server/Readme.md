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