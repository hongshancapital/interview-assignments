export function getSlickPositionLeft(index: number, active: number) {
    return `${(index - active) * 100}%`
}

export function getIdentifierAnimationStyle(millisecond: number) {
    return {
        transition: `${(millisecond / 1000)}s linear`,
        width: '100%'
    }
}
