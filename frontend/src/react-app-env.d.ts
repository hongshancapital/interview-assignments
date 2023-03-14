/// <reference types="react-scripts" />
// css、sass、scss支持以模块的形式引入
declare module "*.css" {
  const classes: { readonly [key: string]: string };
  export default classes;
}
declare module "*.sass" {
  const classes: { readonly [key: string]: string };
  export default classes;
}
declare module "*.scss" {
  const classes: { readonly [key: string]: string };
  export default classes;
}
