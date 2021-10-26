import { CSSProperties } from "react"

export type AlgMode = "loop" | 'one-side'

/**
 * 向左滑动一个单位 
 * @param v 
 * @param size 
 * @returns 
 */
export function prev(v : number, size: number) {
  return Math.min(v === 0 ? size - 1 : v - 1, size)
}

/**
 * 向右滑动一个单位 
 * @param v 
 * @param size 
 * @returns 
 */
export function next(v : number, size: number) {
  return Math.min(v === size- 1 ? 0 : v + 1, size)
}


export type AlgOptions = {
  dir? : "left" | "right",
  duration : number,
  current : number, // 当前幻灯片序号
  size: number // 幻灯片个数
}

/**
 * 轮播图的滑动的算法
 * 轮播图可能又不同形式的滑动算法(比如无限循环、单向折返、随机播放……)
 */
export abstract class AbstractAlg {
  protected options: AlgOptions
  protected size: number
  protected current: number
  protected playIndex : number
  protected styles : {
    prepare: CSSProperties
    enter: CSSProperties
    leave: CSSProperties
  } = {
    prepare: {},
    enter: {},
    leave: {},
  }

  constructor(options: AlgOptions) {
    this.current = options.current
    this.playIndex = this.current
    this.size = options.size
    this.options = Object.assign({}, {
      dir: "left",
    }, options)

    this.updateStyle()
  }

  getCurrent (){
    return this.current
  }
  
  protected movePrev() {
    // 向右滑动，相当于倒序播放
    this.current = prev(this.current, this.size)
  }

  protected moveNext() {
    this.current = next(this.current, this.size)
  }

  next() {
    this.options.dir === "left"
      ? this.movePrev()
      : this.moveNext()
    
    this.updateStyle()
    this.playIndex ++
  }

  public getPlayIndex() {
    return this.playIndex % this.size
  }

  abstract viewport(): number[]

  transitionStyle() {
    return this.styles
  }

  protected abstract updateStyle() : void;

  protected _updateStyle(
    enterValue: number,
    leaveValue: number
  ) {
    const duration = this.options.duration / 1000
    this.styles.prepare = {
      transform: `translateX(${enterValue}%)`,
    }

    this.styles.enter = {
      transform: `translateX(${leaveValue}%)`,
      transition: `transform ${duration}s ease`,
    }

    this.styles.leave = {
      transform: `translateX(${leaveValue}%)`,
    }
  }
}

