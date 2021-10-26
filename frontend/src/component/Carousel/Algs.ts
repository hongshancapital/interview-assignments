import { AbstractAlg, prev, next } from "./AbstractAlg"

/**
 * 无限循环滑动算法
 */
export class InfiniteLoopAlg extends AbstractAlg{
  protected updateStyle(){
    super._updateStyle(
      -100,
      this.options.dir === "left" ? 0 : -200 
    )
  }
  
  viewport(){
    return [
      prev(this.current, this.size),
      this.current,
      next(this.current, this.size),
    ]
  }
}


/**
 * 单向滑动(再折返)算法
 */
export class OneSideAlg extends AbstractAlg {
  private static defaultOptions = {
    dir: "left",
  }

  protected updateStyle() {
    super._updateStyle(
      -this.current * 100,
      -this.tryNext() * 100
    )
  }


  private tryNext(){
    if(this.options.dir === 'left') {
      return prev(this.current, this.size)
    } else {
      return next(this.current, this.size)
    }
  }
  
  viewport(){
    return [...new Array(this.size)].map((_, i) => i)
  }
}


