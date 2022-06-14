interface ShufflingData {
  title: string
  text: string
  img: string
  imgStyle?: {
    [x: string]: string
  }
  style: {
    [x: string]: string
  }
}

interface ShufflingProps {
  data: Array<ShufflingData>
  intervalTime: number
}

export type { ShufflingProps, ShufflingData }
