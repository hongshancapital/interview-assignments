import React, {useState,useRef, useEffect,useCallback} from 'react'
import './Carousel.css'
function Carousel (props:any) {
  const [imgIndex, setImgIndex] = useState<number>(0)
  const [imgBoxLeft, setImgBoxLeft] = useState<number>(0)
  const [lightBtnW, setBtnW] = useState<number>(0)
  const [imgInit,setImgInit] = useState<number>(0)
  const initSize =useRef<HTMLDivElement | null>(null) 
  const autoPlay = useCallback((init)=>{
    let btnw = 0
    let index = 0
    let imgLeft = 0
    let backGap = init/10
    let nomalGap = init/10
    const animat = () => {
      const timer = setInterval(() => {
        btnw += 5
        setBtnW(btnw)
        setImgIndex(index)
        if (btnw >= 100) {
          btnw = 0
          setBtnW(btnw)
          index++
          clearInterval(timer)
          if (index >= props.children.length) {
            index = 0
            const timer2 = setInterval(() => {
              imgLeft -= backGap
              setImgBoxLeft(0 - imgLeft)
              if (imgLeft <= 0) {
                imgLeft = 0
                setImgBoxLeft(0)
                clearInterval(timer2)
                animat()
              }
            }, 60)
          } else {
            const timer1 = setInterval(() => {
              imgLeft += nomalGap
              setImgBoxLeft(0 - imgLeft)
              if (imgLeft >= index * init) {
                clearInterval(timer1)
                animat()
              }
            }, 60)
          }

        }
      }, 60)
    }
    animat()
  },[])// eslint-disable-line react-hooks/exhaustive-deps
  useEffect(() => {
    if(initSize.current){
      const initData:any = initSize.current || {}
      setImgInit(initData.clientWidth)
      autoPlay(initData.clientWidth)
    }
    
  }, [])// eslint-disable-line react-hooks/exhaustive-deps
  return (
    props.children instanceof Array?
    <div  className="carouselBox" ref={initSize}>
      <ul className="imgBox" style={{
        width:props.children && props.children.length * 100 + '%',
        left: imgBoxLeft + 'px'
      }}>
        {props.children.map(((item: any, key: string | number) => {
          return (
            <li style={{width:imgInit+'px'}} className="imgItem" key={key + 'a'}>
              {React.createElement(item.type,{...item.props,width:"100%",height:'100%'})}
            </li>
          )
        }))}
      </ul>
      <ul className="carouselBtn">
        {props.children.map(((item: any, key: string | number) => {
          return (
            <li className="btn" key={key + 'a'} style={{
              marginRight: key === props.children.length - 1 ? '0' : '5px'
            }}>
              {imgIndex === key ? (<div style={{
                width: lightBtnW + '%',
                height: '100%',
                backgroundColor: '#fff'
              }}></div>) : ''}

            </li>
          )
        }))}
      </ul>
    </div>
  :<div>请传入多个元素</div>  )
}
export default Carousel