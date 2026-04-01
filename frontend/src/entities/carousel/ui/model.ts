import { useState } from "react"

export const useDot = (count: number) => {
    const singleWidth = window.document.documentElement.clientWidth
    const wrapWidth = singleWidth * count
    const [index, setIndex] = useState(0)
    return {
        singleWidth,
        wrapWidth,
        index,
        setIndex
    }
}
