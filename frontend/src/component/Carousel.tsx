import React,{useState,useEffect, useRef, useMemo} from 'react'
import { sourceArr } from '../constant'
import { SourceItem } from '../types'
import Progress from './Progress'
import './index.css'

const windowWidth = document.body.clientWidth

const Carousel:React.FC<{duration?:number}> = ({duration=3000}) => {
    const [activeIndex,setActiveIndex] = useState(0)
    const [stopStatus,setStopStatus] = useState(false)
    const [progressPercentage,setProgressPercentage] = useState(1)

    const myDuration = useMemo(() => {
        return duration * progressPercentage
    },[duration,progressPercentage])

    useEffect(() => {
        const timer = setInterval(() => {
            setActiveIndex(activeIndex === sourceArr.length - 1? 0 : activeIndex + 1)
            setProgressPercentage(1)
        }, myDuration)
        if(stopStatus){
            clearInterval(timer)
        }
        return () => {
            clearInterval(timer)
        }
    },[activeIndex,sourceArr,stopStatus,myDuration])

    const mouseEntryHandle = () => {
        setStopStatus(true)
    }
    const mouseOutHandle = () => {
        setStopStatus(false)
    }

    return <div className='container' onMouseOver={mouseEntryHandle} onMouseLeave={mouseOutHandle}>
        <div 
            className='carouselContainer'
            style={{
                width: `${windowWidth * sourceArr.length}px`,
                transform:`translateX(${-activeIndex * windowWidth}px)`
            }}
        >
            <ImgCom sourceArr={sourceArr}/>
        </div>
        <Progress 
            count={sourceArr.length}
            activeIndex={activeIndex}
            duration={duration}
            stopStatus={stopStatus}
            setProgressPercentage={setProgressPercentage}
        />
    </div>
}
const ImgCom:React.FC<{sourceArr:Array<SourceItem>}> = ({sourceArr}) => {
    return <div className='imgContainer'>
        {sourceArr.map(source => <div className='imgItem' {...source}>
        <p>{source.title}</p>
        {source.subTitle && <p>{source.subTitle}</p>}
        </div>)}
    </div>
}
export default Carousel