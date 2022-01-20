import React from 'react'
import './index.css'
/**
 * 类名 辅助函数
 * @param args
 * @returns
 */
export function classNames(...args: any[]): string {
  return args.filter(Boolean).join(' ')
}
/**
 * 节流 辅助函数
 * @param fn
 * @param long
 * @returns
 */
export function throttle(fn: Function, long: number = 100) {
  let waiting = false
  return () => {
    if (!waiting) {
      fn()
      waiting = true
      setTimeout(() => {
        waiting = false
        fn()
      }, long)
    }
  }
}
interface CarouselProps {
  children?: React.ReactNode
  /**
   * 是否自动播放
   */
  autoPlay?: boolean
  /**
   * 自动播放间隔时长 (ms)
   */
  intervalTime?: number
  /**
   * 要进入下一帧时触发的方法
   */
  onBeforeEnter?: (i: number) => void
  /**
   * 进入下一帧动画完毕之后触发的方法
   */
  onAfterEnter?: (i: number) => void
  /**
   * 容器的类名
   */
  className?: string
  /**
   * 容器的style属性
   */
  style?: React.CSSProperties
}
export interface CarouselRef {
  goTo: (index: number) => void
  next: () => void
  prev: () => void
}
export interface DotProps {
  playIndex: number
  intervalTime: number
  autoPlay: boolean
  index: number
  onEnd: (index: number) => void
  onDotClick: (index: number) => void
}
function Dot({
  playIndex,
  index,
  intervalTime,
  autoPlay,
  onEnd,
  onDotClick,
}: DotProps) {
  return (
    <div
      className={classNames(
        'dot',
        index === playIndex && 'active',
        autoPlay && 'autoPlay'
      )}
      style={{ marginLeft: 5, marginRight: 5 }}
      onClick={() => {
        onDotClick(index)
      }}
    >
      <div
        style={{ animationDuration: `${intervalTime}ms` }}
        onAnimationEnd={() => {
          onEnd(index)
        }}
        className="bar"
      />
    </div>
  )
}

const Carousel = React.forwardRef<CarouselRef, CarouselProps>(
  (
    {
      children,
      className,
      onBeforeEnter,
      onAfterEnter,
      style,
      intervalTime = 3000,
      autoPlay = true,
    },
    ref
  ) => {
    const contaienrRef = React.useRef<HTMLDivElement>()
    const [playIndex, setPlayIndex] = React.useState(0)
    const [width, setWidth] = React.useState(0)

    const count = React.Children.count(children)
    const prevCount = React.useRef(count)
    React.useImperativeHandle(
      ref,
      () => ({
        goTo: (index: number) => {
          setPlayIndex(index % count)
        },
        prev: () => {
          setPlayIndex((index) =>
            index === 0 ? count - 1 : (index - 1) % count
          )
        },
        next: () => {
          setPlayIndex((index) => (index + 1) % count)
        },
      }),
      [children]
    )

    React.useEffect(() => {
      if (prevCount.current !== count) {
        setPlayIndex(0)
        prevCount.current = count
      }
    }, [children])

    React.useLayoutEffect(() => {
      function calc() {
        if (contaienrRef.current) {
          const w = contaienrRef.current.getBoundingClientRect().width
          setWidth(w)
        }
      }
      const wrapperCalc = throttle(calc, 500)
      wrapperCalc()
      window.addEventListener('resize', wrapperCalc)
      return () => {
        window.removeEventListener('resize', wrapperCalc)
      }
    }, [])

    return (
      <div
        ref={(r: HTMLDivElement) => {
          contaienrRef.current = r
        }}
        style={style}
        className={classNames('pre-container', className)}
      >
        {width ? (
          <div
            className="sliderContainer"
            style={{
              marginLeft: -playIndex * width,
              width: count * width,
              transition: '0.5s',
            }}
            onTransitionEnd={() => {
              if (onAfterEnter) onAfterEnter(playIndex)
            }}
          >
            {React.Children.map(children, (child) => {
              return (
                <div className="slider" style={{ width }}>
                  {child}
                </div>
              )
            })}
          </div>
        ) : null}
        <div className="dot-container">
          {React.Children.map(children, (child, index) => {
            return (
              <Dot
                onDotClick={(i) => {
                  setPlayIndex(i)
                }}
                index={index}
                playIndex={playIndex}
                intervalTime={intervalTime}
                autoPlay={autoPlay}
                onEnd={(i: number) => {
                  const num = (i + 1) % count
                  setPlayIndex(num)
                  if (onBeforeEnter) onBeforeEnter(num)
                }}
              />
            )
          })}
        </div>
      </div>
    )
  }
)

export default Carousel
