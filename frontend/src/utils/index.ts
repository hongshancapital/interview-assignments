import { isValidElement } from 'react'

export const getKey = (child: ValidReactChild, fallbackKey: string) =>
  (isValidElement(child) && child.key) || fallbackKey
