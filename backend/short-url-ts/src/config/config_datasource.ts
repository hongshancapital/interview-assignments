// pgsql config
export const dataBase = {
    type: "postgres",
    host: "127.0.0.1",
    port: 5432,
    username: "",
    password: "",
    database: "testdb",
    timezone: "+08:00",
    maxConnections: 99,
    minConnections: 0,
    maxIdleTime: 10000,
    logging: true,
    timestamps: false,
    freezeTableName: true
};

// redis config
export const redis = {
    host: "127.0.0.1",
    port: 6379,
    password: ""
};