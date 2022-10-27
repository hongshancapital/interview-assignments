import React, { useState, useEffect, useRef, FC } from 'react';
import classnames from 'classnames'

export interface Porps {
  autoplay?: boolean,     // autoplay	是否自动切换	boolean	
  dotPosition?: string,   // dotPosition	面板指示点位置，可选 top bottom left right
  dots?: boolean,         // dots	是否显示面板指示点 boolean	
  easing?: string,        // easing	动画效果	string	linear  // 目前只有两种动画 默认不传 或者传linear 是无缝轮播  传其他任意字符串 是显示隐藏轮播
  time?: number           //自动轮播时间 默认 3S
  initialSwipe?: number   //初始位置索引值 默认0 
  onChange?: () => void   //切换面板的回调
  data: Array<Record<string, any>>  //轮播图数据源数组  必传参数且确保必带 url   不限制源数组的长度

}



const Carousel: FC<Porps> = ({ initialSwipe = 0, autoplay = true, dotPosition = 'bottom', dots = true, easing = 'linear', time = 3000, onChange,data }) => {

  // 获取当前轮播图部分的DOM 便于取其宽度
  const content = useRef(null)

  // 当前轮播图部分的宽度 每次轮播索引*宽度 就是需要过渡 left 值
  const [width, setWidth] = useState(0)

  // 获取包裹容器
  const container = useRef(null);

  // 动态设置包裹容器的宽度
  const [containerWidth, setContainerWidth] = useState(0)

  // 切换轮播需要过渡的 left 值
  const [left, setLeft] = useState(0);


  // 当前正在显示轮播图的索引
  const active = useRef(initialSwipe);

  //  存储定时器
  const { current } = useRef({ timer: null });


  useEffect(() => {

    // 设置当前轮播图部分的宽度
    setWidth((content.current as any).clientWidth)

    // 动态设置包裹容器的宽度
    setContainerWidth(data.length * width);

    // 是否自动轮播
    if (autoplay)
      // 设置定时器 自动轮播
      (current as any).timer = (
        setInterval(() => {
          active.current === data.length - 1 ? active.current = 0 : active.current = active.current + 1
          setLeft(width * active.current)
        }, time))

    // 清除定时器
    return () => { clearInterval(current.timer as any) }

  }, [width, left, active, time, current, autoplay,data]);



  const setTransition = (index: number) => {
    // 点击按钮 设置对应索引
    // setActive(index)
    active.current = index
    // 设置对应需要改变的left值
    setLeft(width * index)
    // 切换回调
    onChange?.()
  }

  // 根据传入的dotPosition 改变class 动态设置 dots 的位置 
  const setDotPosition = classnames('dots', { [`dotPosition-${dotPosition}`]: dotPosition })

  return (
    <div className='carousel' ref={content}>
      {easing === 'linear' ?
        (<ul style={{ marginLeft: -left, width: containerWidth }} ref={container}> {data.map((item, index) => (<li style={{ width }} key={index}>
          <img src={item.url} alt="" /></li>))}
        </ul>) :
        (<ul >{data.map((item, index) => (<li className={index === active.current ? 'active' : ''} style={{ display: "none" }} key={index}><img src={item.url} alt="" /></li>))}</ul>)}
      {dots ? (<div className={setDotPosition}>{data.map((item, index) => (<span key={index} onClick={() => { setTransition(index) }}><small style={{ animationDuration: time / 1000 + 's' }} className={index === active.current ? 'active' : ''}></small></span>))}
      </div>) : null}

    </div>
  )
}

export default Carousel

