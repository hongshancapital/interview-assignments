import React,{useState,useEffect,useRef} from 'react'
import './style.css'

interface CarouselProps{
    data?:{src:string,content:React.ReactNode}[]
}
const TimeIndicator=({total=0,index=-1}:{total:number,index:number})=>{
    const [_index,setIndex]=useState(-1)
    useEffect(()=>{
        setIndex(index)
    },[index])
    if(total===0)return null
    return <div className='indicator'>
        {Array.from({length:total}).map((_,_i)=><div key={_i} className={`indicator-item ${_i===_index?'indicator-item-active':''}`}>
            </div>)}
    </div>
}
const Carousel:React.FC<CarouselProps>=({data=[]})=>{
    const [playIndex,setPlayIndex]=useState(0)
    useEffect(()=>{
        const timer=setInterval(()=>{
            setPlayIndex((preIndex)=>(preIndex+1)%data.length)
        },2000)
        return ()=>clearInterval(timer)
    },[])
    console.log('playIndex',playIndex,data.length)
    return (<section className='carousel-wrap'>
       <div className="carousel" style={{transform:`translateX(${-playIndex*100}%)`}}> 
       {
            data.map(item=><div className='carousel-item' key={item.src}>
            {item.content}
            <img src={item.src} />
            </div>)
        }
     
       </div>
       <div className="indicator-wrap">
        <TimeIndicator 
                total={data.length}
                index={playIndex}
            />
       </div>
    </section>)

}
export default Carousel