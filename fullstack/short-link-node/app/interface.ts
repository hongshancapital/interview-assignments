export interface ServiceErrorInterface {
  code: string
  msg?: string
  e?: string
}

export interface ServiceReturnInterface<T> {
  error?: ServiceErrorInterface
  data?: T
}

export interface ErrorInterface {
  code: string
  msg?: string
  e?: Error
}
