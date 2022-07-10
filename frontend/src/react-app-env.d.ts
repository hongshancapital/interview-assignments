/// <reference types="react-scripts" />
declare namespace NodeJS {
  interface ProcessEnv {
    readonly NODE_ENV: 'development' | 'production' | 'test';
    readonly REACT_APP_ANT: 'dev' | 'alpha' | 'preprod' | 'prod';
    readonly PUBLIC_URL: string;
  }
}