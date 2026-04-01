import { nanoid } from "nanoid"
import { useMemo } from "react"

const useUniqueId = (arrLength: number) => {
    const uniqueIdsArr: string[] = []
    for (let i = 0; i < arrLength; i++) {
        uniqueIdsArr.push(nanoid())
    }
    return useMemo(() => uniqueIdsArr, [])
}

export { useUniqueId }