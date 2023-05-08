import { createContext, useContext, useReducer, useState, useEffect } from 'react'
import {reducer} from './reducer'
import {initValue, IProps, BannerProps} from './state'
import './index.css'

// 创建数据共享模块
const CarouselContext = createContext({})

// banner底部状态DOM
const Footer = () => {
  const [styles, setStyle] = useState({})
  const { state: { footer = {}, options = [], index = 0, time = 3, direction = 'left' }, dispatch }: any = useContext(CarouselContext)

  // 监听banner图片下标，进行动态渲染
  useEffect(() => {
    const newTime = parseInt(time)
    console.log(time)
    setStyle({width: 0})
    const styleTime = setTimeout(() => {
      setStyle({transition: `width ${newTime - 0.1}s`, [direction]: 0, width: '100%'})
    }, 100)
    const cutTime = setTimeout(() => {
      dispatch({
        type: 'time',
        payload: {
          index: index + 1
        }
      })
    }, newTime * 1000)
    if (options.length < 2) {
      clearTimeout(styleTime)
      clearTimeout(cutTime)
    }
    return () => {
      clearTimeout(styleTime)
      clearTimeout(cutTime)
    }
  }, [index, direction, time])

  if (options.length > 1) {
    return(<div className={`footer ${footer.className || ''}`}>
      {
        options.map((item: BannerProps, value: number) => {
          let ActiveDOM = null
          if (value === index) {
            ActiveDOM = <div className='active' style={styles}></div>
          }
          return(<div className='list' key={value}>
            {ActiveDOM}
          </div>)
        })
      }
    </div>)
  } else {
    return null
  }
}

// banner渲染DOM
const Banner = () => {
  const { state: { footer = {}, options = [], time = 3, direction = 'left', index = 0 } }: any = useContext(CarouselContext)
  return(<div className={`container ${footer.className}`}>
    {
      options.map((item: BannerProps, value: number) => {
        let DOM: any = null
        let bannerClass = ''
        if (item.element) {
          DOM = item.element
        } else {
          DOM = <img src={item.image} />
        }
        if (index === value) {
          bannerClass = 'active' // 选中状态
        } else if ((index - 1) < 0 && (options.length - 1) === value) {
          bannerClass = 'prev' // 上一页面
        } else if ((index - 1) >= 0 && (index - 1) === value) {
          bannerClass = 'prev' // 上一页面
        } else if ((index + 1) < options.length && (index + 1) === value) {
          bannerClass = 'next' // 下一页面
        } else if ((index + 1) >= options.length && 0 === value) {
          bannerClass = 'next' // 下一页面
        }
        return(<div className={`list ${bannerClass}`} key={value}>
          { DOM }
        </div>)
      })
    }
  </div>)
}

// provider数据共享模板
const Provider = ({children, value}: { children: React.ReactElement, value: any }) => {
  const [state, dispatch] = useReducer(reducer, value)
  return(<CarouselContext.Provider value={{state, dispatch}}>
    {children}
  </CarouselContext.Provider>)
}

const Carousel = (props: IProps) => {
  return(<Provider value={{...initValue, ...props}}>
    <div className={`carousel ${props.className || ''}`} style={{width: `${props.width}px`, height: `${props.height}px`}}>
      <Banner />
      <Footer />
    </div>
  </Provider>)
}

export default Carousel
