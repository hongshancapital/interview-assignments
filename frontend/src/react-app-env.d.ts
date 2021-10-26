/// <reference types="react-scripts" />


declare module "*.scss" {
  const classes : Record<string, string>
  export default classes
}