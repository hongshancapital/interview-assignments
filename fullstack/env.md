```shell
docker run --restart=always --name=postgres -e POSTGRES_USER=sl -e POSTGRES_PASSWORD=password -p 5432:5432 -v $pwd/pg:/var/lib/postgresql/data -v $pwd/initdb.d:/docker-entrypoint-initdb.d -d postgres
```