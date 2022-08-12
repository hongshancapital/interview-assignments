interface Ikeyframe {
  transform: string
  transformOrigin: string
}

interface Ioption {
  duration: number
  fill: string
}

type current = HTMLDivElement | null
type keyframArr = Ikeyframe[]

export class KeyframeEffect {
  constructor(dom: current, kf: keyframArr, option: Ioption) {}
}

export class Animation {
  constructor(kf: string) {
    this.finished = new Promise((res, rej)=> { res() })
  }
  finished: Promise<void>
  play() {}
}

export type TkeyframeInstance = InstanceType<typeof KeyframeEffect>
export type TanimationInstance = InstanceType<typeof Animation>