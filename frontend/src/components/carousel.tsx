import React from 'react'
import { CarouselProps, CarouselState } from './interface'

/**
 * 轮播图控件
 */
export class Carousel extends React.Component<CarouselProps, CarouselState> {

  /**
   * 构造参数
   */
  private arg: CarouselProps

  /**
   * 定时器，用来控制轮播图自动切换
   */
  private timer: number | null

  constructor(props: CarouselProps) {
    super(props);
    this.arg = {
      content: props.content,
      autoPlay: props.autoPlay == null,
      delay: props.delay || 3000,
      loop: props.loop == null,
      dots: props.dots || 'inside',
      onChange: props.onChange
    };
    this.state = {
      currentIndex: -1
    };
    this.timer = null
  }

  componentWillUnmount () {
    this.timer && clearTimeout(this.timer)
    this.timer = null
  }

  componentDidMount () {
    setTimeout(() => { this.startCount() }, 0)
  }

  /**
   * 切换当前展示的轮播图
   */
  public setIndex(index: number): void {
    if (index > this.arg.content.length) {
      throw new Error("u don't have such a pic")
    }
    this.setState({
      currentIndex: index
    })
  }

  /**
   * 轮播图索引在此控制逻辑
   * @param {number} i 传递i时，将会直接把i设置为展示单页的索引
   */
  private startCount (i?: number) {
    clearTimeout(this.timer as number)
    this.timer = null
    let index
    if (i == null) {
      index = this.state.currentIndex
      const picNum = this.arg.content.length - 1
      if (index < picNum) {
        index++
      } else if (index === picNum) {
        index = 0
      }
    } else {
      index = i
    }
    this.setIndex(index)
    this.timer = setTimeout(() => {
      this.startCount()
    }, this.arg.delay)
  }

  /**
   * 计算偏移量以实现单页滚动切换
   */
  private calcLeft (): string {
    const index = this.state.currentIndex
    return `translate3d(-${index * 100}%, 0px, 0px)`
  }

  render(): React.ReactNode {
    const { content, delay } = this.arg
    const { startCount } = this
    const { currentIndex } = this.state
    const translate3d = this.calcLeft()
    return (
      <div className="carousel-comp">
        <div className='carousel-comp-track' style={{ transform: translate3d }}>
          {
            content.map((item, index) => {
              return (<div className='carousel-item' key={index}>{item}</div>)
            })
          }
        </div>
        <div className="carousel-dots">
          {
            content.map((item, index) => {
              return <div className='carousel-dot' key={'dot' + index} onClick={startCount.bind(this, index)}>
                <div
                  className={['carousel-dot-inside', currentIndex === index ? 'current-carousel-dot' : null].join(' ')}
                  style={{ transitionDuration: currentIndex === index ? delay?.toString() + 'ms' : '0ms' }}></div>
              </div>
            })
          }
        </div>
      </div>
    )
  }
}
