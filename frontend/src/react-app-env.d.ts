/// <reference types="react-scripts" />

declare namespace NodeJS {
  interface ProcessEnv {
    readonly NODE_ENV: 'development' | 'test' | 'beta' | 'production';
    readonly PUBLIC_URL: string;
  }
}
 
declare module '*.sass' {
  const classes: { readonly [key: string]: string };
  export default classes;
}

declare module '*.module.sass' {
  const classes: { readonly [key: string]: string };
  export default classes;
}
