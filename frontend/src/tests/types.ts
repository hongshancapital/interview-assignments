interface Ikeyframe {
  transform: string
  transformOrigin: string
}

interface Ioption {
  duration: number
  fill: string
}

type Tcurrent = HTMLDivElement | null
type TkeyframArr = Ikeyframe[]

export class KeyframeEffect {
  constructor(dom: Tcurrent, kf: TkeyframArr, option: Ioption) {
    this.current = dom
    this.keyframes = kf
    this.option = option
  }
  current: Tcurrent
  keyframes: TkeyframArr
  option: Ioption
}

export class Animation {
  constructor(kf: KeyframeEffect) {
    this.finished = undefined
    this.duration = kf.option.duration
  }
  finished: Promise<void> | undefined
  duration: number
  play() {
    this.finished = new Promise((res, rej)=> { 
      setTimeout(()=>{
        res()
      }, 0)
    })
  }
}

export type TkeyframeInstance = InstanceType<typeof KeyframeEffect>
export type TanimationInstance = InstanceType<typeof Animation>