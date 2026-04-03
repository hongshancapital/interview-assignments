import { requestAnimationFrame, cancelAnimationFrame, type Timer } from './requestAnimationFrame'

type Cb = (progress: number) => void

class Interval {

  progress: number = 0
  callback: Cb
  timer: Timer = 0
  delay: number = 0
  autoplay: boolean

  constructor(callback: Cb, delay: number, autoplay: boolean = true) {
    this.callback = callback
    this.delay = delay
    this.autoplay = autoplay
    this.play()
  }

  // 播放
  play() {
    if (!this.autoplay) return
    if (this.timer) this.paused()
    let startTime = performance.now()
    this.timer = requestAnimationFrame((timestamp) => {
      const elapsed = timestamp - startTime
      startTime = timestamp
      if (this.progress === 100) this.progress = 0
      this.progress += elapsed * (100 / this.delay)
      if (this.progress >= 100) this.progress = 100
      this.callback(this.progress)
      this.play()
    })
  }

  // 暂停
  paused() {
    if (this.timer) {
      cancelAnimationFrame(this.timer)
      this.timer = 0
    }
  }

  // 重置
  reset() {
    this.progress = 0
    this.callback(this.progress)
  }

}

export default Interval