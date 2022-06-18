import React,{PropsWithChildren,useEffect,useState} from "react";
import './Carousel.css'
import IndicatorLines from '../IndicatorLines/IndicatorLines'
interface CarouselProps{
    
}
function Carousel(props:PropsWithChildren<CarouselProps>){
    const animationTime = 3
    let linesLength = 0
    let [currentPage,setCurrentPage] = useState(0)
    if(Array.isArray(props.children) && props.children.length>1){
        linesLength = props.children.length
    }else if(props.children){
        linesLength = 1
    }
    useEffect(()=>{
        if(linesLength){
            let timer = setInterval(()=>{
                setCurrentPage(prevCurrentPage=>{
                    if(prevCurrentPage<linesLength-1){
                        return prevCurrentPage+1
                    }else{
                        return 0
                    }
                })
            },animationTime*1000)
        }
        return
     },[])
    return <div className="carousel-wrap">
        <div className="carousel-pages-content" style={{transform: `translateX(${currentPage*(-100)}vw)`,width:`${linesLength*100}vw`}}>
            {props.children}
        </div>
        <IndicatorLines currentPage={currentPage} linesLength={linesLength} animationTime={animationTime} />
    </div>;
}
export default Carousel