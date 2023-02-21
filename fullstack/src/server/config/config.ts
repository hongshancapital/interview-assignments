import { IConfig } from "../interfaces/IConfig";

/**
 * db configuration
*/
export const config: IConfig = {
  mongodb: {
    url: "localhost",
    port: 27017,
    collection: "urlShorten",
    options: {
      useCreateIndex: true,
      useNewUrlParser: true,
      useUnifiedTopology: true,
    }
  },
};

export const defaultApiVersion = 'v1';