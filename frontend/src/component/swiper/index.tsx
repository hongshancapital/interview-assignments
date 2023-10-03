import React, { useState, useEffect, useRef } from 'react'
import { Props, dataType } from './swiper'
import './swiper.scss'


const Swiper = (props: Props) => {
  const swiperRef = useRef(null)
  const [count, setCount] = useState<number>(0)
  const { data, timeObj } = props
  const [curIndex, setCurIndex] = useState<number>(0)
  const [bannerData, setBannerData] = useState<dataType[]>(data)
  const [isclear, setIsclear] = useState<boolean>(false)
  const { playTime = 0, lazyTime = 0 } = timeObj
  const isPlay = () => {
    return new Promise((res) => {
      let index = curIndex + 1
      if (index >= bannerData.length) {
        res(0)
      } else (
        res(index)
      )
    })
  }
  const setIndex = (num: any) => {
    setTimeout(() => {
      setIsclear(num ? false : true)
      setCurIndex(num)
    }, lazyTime)
  }
  useEffect(() => {
    isPlay().then((res: any) => {
      if (res) {
        setCurIndex(res)
      } else {
        setCurIndex(0)
      }
    })
  }, [])
  useEffect(() => {
    setTimeout(() => {
      isPlay().then((res: any) => {
        if (res) {
          setIndex(res)
        } else {
          setIndex(0)
        }
      })
    }, playTime)
  }, [curIndex])


  return (
    <div style={{overflow:'hidden'}}>
      <div className='swiper'
        style={{
          width: `${data.length * 100}vw`, display: ' flex',
          overflow: 'hidden',
          height: "100vh",
          transform: `translateX(${-100 * curIndex}vw)`,
          transition: isclear ? 'transform 1s ease-out' : `transform ${playTime/1000}s linear`
        }}
        ref={swiperRef}
      >
        {bannerData.length && (
          bannerData.map((val, index) => {
            return (
              <div key={index} className='banner_item'>
                <div className='banner_item_text' style={{ color: val.color }}>{val.text}</div>
                <div className='banner_item_img'><img src={val.banner} alt="加载失败" /></div>
              </div>
            )
          })
        )}

      </div>
      <div className='page'>{data.length && (
        data.map((val, index) => {
          return <span style={{ background: curIndex === index ? 'red' : '' }}></span>
        })

      )}

      </div>
    </div>


  )
}

export default Swiper