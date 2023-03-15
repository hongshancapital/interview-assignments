export interface IConfigs {
    mongodb: IMongo,
}

interface IMongo {
    url: string,
    port: number,
    username: string,
    password: string,
    collection: string,
}
