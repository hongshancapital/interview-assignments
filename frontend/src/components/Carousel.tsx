import React, { useState, useEffect, useRef } from 'react'
import './carousel.css'

type Props = {
    imgList: string[]
}

export default function Carousel(props: Props) {
    const { imgList } = props
    const [currentIndex,setCurrentIndex] = useState<number>(1)
    const ulRef:any = useRef(null)
    const [dataList,setDataList] = useState(imgList)
    const [dotLeft,setdotLeft] = useState<number>(-40)
    const [cls,setCls] = useState<string>('')
    let style = {
        width:(dataList.length + 1) * 1280 + 'px',
        left:-(currentIndex - 1)*1280 +'px',
        transition: `left 0.5s linear`
    }

    useEffect(()=>{
        if(imgList.length>0){
            setDataList([...imgList])
        }
    },[imgList])

    useEffect(()=>{
        const timer =  setInterval(() => {
            let index = currentIndex + 1;
            if (index > dataList.length){
                const ref = ulRef.current
                if(ref){
                    ref.style.transitionDuration = '';
                    ref.style.left = '0px';
                    ref.style.transitionDuration = '0.5s';
                    index = 1;
                    setCurrentIndex(index)
                } 
            }
            setCurrentIndex(index)
            
        },3000);

        return ()=>{
            clearInterval(timer)
        }
    })

    useEffect(()=>{
        setdotLeft(-40)
        setCls('')
        const timer2 = setTimeout(()=>{
            setdotLeft(0)
            setCls('on')
        },30)
        return ()=>{
            clearTimeout(timer2)
        }

    },[currentIndex])
    
    return (
        <div className = 'slider-container'>
            <ul style={style} ref={ulRef}>
                {dataList.map((item,index) => (
                    <li key={index}>
                        <img src={item} alt="无效图片"/>
                    </li>
                ))}
            </ul>     
            <div className='slider-dots'>
                {dataList.map((item,i)=>(
                    <span key={i} className={currentIndex ===i+1?'dots active':'dots'}>
                        <span className={cls}  style={{left:dotLeft + 'px'}}></span>
                    </span>
                ))}
            </div>  
        </div>
        
    )
}
