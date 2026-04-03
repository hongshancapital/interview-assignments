
export type Timer = number | ReturnType<typeof setTimeout>

export function requestAnimationFrame(callBack: FrameRequestCallback) {
    if (!window.requestAnimationFrame) {
      const timer = setTimeout(() => {
        const timestamp = performance.now()
        callBack(timestamp)
      }, 0)
      return timer
    }
    return window.requestAnimationFrame(callBack)
}

export function cancelAnimationFrame(timer: Timer) {
  if (!window.cancelAnimationFrame) {
    return clearTimeout(timer)
  }
  return window.cancelAnimationFrame(timer as number)
}