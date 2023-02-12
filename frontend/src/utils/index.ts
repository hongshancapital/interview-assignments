import type { ReactElement } from "react"

export const getKey = (child: ReactElement, fallbackKey: string) =>
  child.key || fallbackKey
