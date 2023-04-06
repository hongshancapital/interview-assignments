import { useState, useEffect, useRef, FC, ReactElement } from "react"
import "./index.css"
import debounce from "../../utils/debounce"
import simulatInterval from "../../utils/simulatInterval"

interface CarouselProps {
  list: Array<string>
  delay?: number
  animationImg?: boolean
}
const Carousel: FC<CarouselProps> = ({ list, delay = 2000, animationImg = true }): ReactElement => {
  const intervalTime = delay
  const isFead = animationImg
  const imaArr = [...list]

  const [imgarrar, setImgarrar] = useState([...list])
  const [imgUrl, setImgUrl] = useState<string>()
  const [timer, setTimer] = useState<any>(null)
  const [time, setTime] = useState<number>(0)
  const [isShow, setIsShow] = useState(false)
  const renderRef = useRef(true)

  const handleClick = (t: string) => {
    clearTimeout(timer)
    let str = ''
    if (t === 'prev') {
      str = imgarrar[imgarrar.length - 1];
      imgarrar.splice(imgarrar.length - 1, 1)
      imgarrar.unshift(str)
    } else {
      str = imgarrar[0];
      imgarrar.splice(0, 1)
      imgarrar.push(str)
    }
    animationFun()
    setImgUrl(str)
    setImgarrar(imgarrar)
  }

  const onMouseOver = () => {
    setTime(new Date().getTime())
    clearTimeout(timer)
  }
  const onMouseOut = () => {
    clearTimeout(timer)
    if (!(new Date().getTime() - time > 200)) {
      console.log(new Date().getTime() - time > 200);
      // 函数式组件每渲染结束后都会释放变量，再次渲染时重新初始化变量，因此这里的函数每一次都注册和执行，
      // 我们需要保存某些释放的变量
      return delayedFn(startFun)
    }
    setTime(new Date().getTime())
    startFun()
  }

  useEffect(() => {
    if (renderRef.current) {
      renderRef.current = false
      return
    }
    startFun()
    return function clearup() {
      clearTimeout(timer)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  // 启动轮播的方法
  const startFun = () => {
    if (imgarrar.length > 1) {
      const temp = () => {
        let str = imgarrar[0];
        imgarrar.splice(0, 1)
        imgarrar.push(str)
        animationFun()
        setImgUrl(str)
        setImgarrar(imgarrar)
      }
      simulatInterval(temp, intervalTime, setTimer)
    }
  }
  const delayedFn = useRef(debounce(startFun, 500)).current

  const animationFun = () => {
    if (isFead && imgarrar.length > 0) {
      if (isShow) {
        document.getElementsByClassName("scroll_box_item_img")[0].className = 'scroll_box_item_img img_active'
      } else {
        document.getElementsByClassName("scroll_box_item_img")[0].className = 'scroll_box_item_img img_actives'
      }
      setIsShow(!isShow)
    }
  }

  return (
    <div className='scroll' style={{ width: '416px', height: '260px' }}
      onMouseEnter={onMouseOver}
      onMouseLeave={onMouseOut}
    >
      <div className='scroll_box'>
        {imgarrar.map((item, index) => {
          return (
            <div className='scroll_box_item' key={index}>
              <img className='scroll_box_item_img' src={item} alt="图片加载失败" />
            </div>
          )
        })}
      </div>

      <div className='scroll_dots'>
        {imaArr.map((t, i) => {
          return (
            <div key={i} className="scroll_dots_item">
              <div
                className={imgUrl === t ? 'scroll_dots_item_active' : ''}
                style={{ animation: imgUrl === t ? `moveLeft ${intervalTime / 1000}s forwards` : '' }}
              ></div>
            </div>
          )
        })}
      </div>
      <div className='scroll_option'>
        <div className='scroll_option_left' onClick={() => handleClick('prev')}>{'<'}</div>
        <div className='scroll_option_right' onClick={() => handleClick('next')}> {'>'} </div>
      </div>
    </div>
  )
}

export default Carousel