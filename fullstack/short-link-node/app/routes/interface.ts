import { ErrorInterface } from "../interface"

export interface RouterResInterface<ErrorInterface, T> {
  error?: ErrorInterface
  data?: T
}

export interface AddLinkResInterface {
  slink: string
}

export interface UpdateLinkResInterface {
  slink: string
  oriUrl: string
}

export interface GetLinkResInterface {
  slink: string
  oriUrl: string
}
