import React, {
  ReactChild,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState
} from 'react'
import { Viewport, Slides, ProgressContainer } from './styles'
import Progress from '../Progress/index'

type IProps = {
  children: ReactChild | ReactChild[]
  width: string
  height: string
  /**
   * 单位：毫秒
   */
  duration: number
  /**
   * 单位：毫秒
   */
  transitionDuration: number
}

const Carousel = (props: IProps) => {
  const { width, height, duration, transitionDuration, children } = props

  let _children: ReactChild[] = children as ReactChild[]
  if (React.Children.count(props.children) <= 1) {
    _children = [children as ReactChild]
  }

  const n = useMemo<number>(() => _children.length, [_children.length])
  const [offset, setOffset] = useState<string>('0%')
  const [index, setIndex] = useState<number>(0)
  const timer = useRef<number>(-1)

  const setTimer = useCallback(() => {
    if (n <= 1) return
    // @ts-ignore
    timer.current = setTimeout(() => {
      let _index = index
      _index += 1
      _index === n && (_index = 0)
      setIndex(_index)
      setOffset(`${_index * -100}%`)
    }, duration)
  }, [n, duration, index])

  const onVisibilityChange = useCallback(() => {
    document.hidden ? clearTimeout(timer.current) : setTimer()
  }, [setTimer])

  useEffect(() => {
    setTimer()
  }, [setTimer])

  useEffect(() => {
    document.addEventListener('visibilitychange', onVisibilityChange)
    return () => {
      document.removeEventListener('visibilitychange', onVisibilityChange)
    }
  }, [onVisibilityChange])

  return (
    <Viewport width={width} height={height}>
      <Slides offset={offset} transitionDuration={transitionDuration}>
        {_children}
      </Slides>
      <ProgressContainer>
        {n > 1 &&
          _children.map((_, i) => (
            <Progress key={i} duration={duration} active={i === index} />
          ))}
      </ProgressContainer>
    </Viewport>
  )
}

Carousel.defaultProps = {
  width: '100%',
  height: '100%',
  duration: 3000,
  transitionDuration: 500
}

export default Carousel
