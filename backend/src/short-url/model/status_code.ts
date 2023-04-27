export enum StatusCode {
  // 200 - The request was successful 
  Success = 200,
  // 201 - The request was unsuccessful 
  Failes = 201,
  // 202 - The requested resource already exists 
  Exists = 202,
  // 203 - The requested resource was not found 
  NotFound = 203,
  // 204 - The provided parameters were null 
  ParamsIsNull=204,
  // 205 - The provided parameters were invalid
  ParamsInvalid=205,
}
