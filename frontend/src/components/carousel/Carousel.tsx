import React, { useState, useRef, useEffect, useCallback } from 'react'
import './Carousel.css'
function Carousel(props: any) {
  const [imgIndex, setImgIndex] = useState<number>(0)
  const [imgBoxLeft, setImgBoxLeft] = useState<number>(0)
  const [lightBtnW, setBtnW] = useState<number>(0)
  const [imgInit, setImgInit] = useState<number>(0)
  const initSize = useRef<HTMLDivElement | null>(null)
  const autoPlay = useCallback((init) => {
    setBtnW(100)
    let index = 0
    const animat = (() => {
      const timer = setTimeout(() => {
        index++
        if (index >= props.children.length) {
          index = 0
          setImgBoxLeft(0)
        }
        setImgBoxLeft(0 - index * init)
        setImgIndex(index)
        setBtnW(0)
        const timer2 = setTimeout(() => {
          setBtnW(100)
          animat()
          clearTimeout(timer2)
        }, 1000)
        clearTimeout(timer)
      }, 1000)
    })
    animat()
  }, [])// eslint-disable-line react-hooks/exhaustive-deps
  useEffect(() => {
    if (initSize.current) {
      const initData: any = initSize.current || {}
      setImgInit(initData.clientWidth)
      autoPlay(initData.clientWidth)
    }

  }, [])// eslint-disable-line react-hooks/exhaustive-deps
  return (
    props.children instanceof Array ?
      <div className="carouselBox" ref={initSize}>
        <ul className="imgBox" style={{
          width: props.children && props.children.length * 100 + '%',
          transform: `translateX(${imgBoxLeft}px)  scale(1)`,
         // left:`${imgBoxLeft}px`,
          transition: 'transform 1s'
        }}>
          {props.children.map(((item: any, key: string | number) => {
            return (
              <li style={{ width: imgInit + 'px' }} className="imgItem" key={key + 'a'}>
                {React.createElement(item.type, { ...item.props,style:{...item.props.style,width:'100%',height:'100%'} })}
              </li>
            )
          }))}
        </ul>
        <ul className="carouselBtn">
          {props.children.map(((item: any, key: string | number) => {
            return (
              <li className="btn" key={key + 'a'} style={{
                marginRight: key === props.children.length - 1 ? '0' : '15px'
              }}>
                {imgIndex === key ? (<div style={{
                  width: lightBtnW + '%',
                  height: '100%',
                  transition: 'width 1s',
                  backgroundColor: '#fff',
                  borderRadius: '3px 3px'
                }}></div>) : ''}

              </li>
            )
          }))}
        </ul>
      </div>
      : <div>请传入多个元素</div>)
}
export default Carousel