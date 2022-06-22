export function getNext(current: number, length: number): number {
  return current + 1 < length ? current + 1 : 0
}

export function runLoopFactory() {
  let timer: NodeJS.Timeout | null = null;
  const runLoop = (fn: (t: number) => boolean, frameTime: number) => {
    timer = setTimeout(() => {
      timer = null
      fn(Date.now()) && runLoop(fn, frameTime)
    }, frameTime)
  }
  return {
    runLoop,
    cleanLoop() {
      timer !== null && clearTimeout(timer)
    }
  }
}
