import React, {useEffect, useRef,  useState} from "react";
import carouselSource from '../../CarouselSource'
import "../../styles/carousel.css"

interface ScrollPointInter {
    currentPage: number,
    index: number,
}
function ScrollPoint( {currentPage,index,}: ScrollPointInter ){
    

    console.log(`currentPage: ${currentPage};; index: ${index}`)

    return <div className="scroll-point" style={{
        width: '30px'
    }}>
        <div className="scroll-list" 
            style={{animation:currentPage == index? `scrollMove 2s linear infinite` : '' }}
        ></div>
    </div>
}
export default function Carousel({children}: any){
    const [currentIndex, setCurrentIndex] = useState(0)
    useEffect(()=>{
        var timer = setInterval(()=>{
            if (currentIndex < carouselSource.length - 1) {
                setCurrentIndex(currentIndex + 1)
            } else {
                setCurrentIndex(0)
            }
            }, 2000)
     })
    return (
        <div className="container"
            style={{ 
                width: "100%",
                height: '100%'  
            }}
        >
            <div className="product-main"
            >
                {
                    children.map((item:any, index:number) => {
                        return (
                            <div key={index} className="slide"
                                style={{ width: '100vw', height: '100vh'}}
                            >{item}</div>
                        )
                    })
                }
            </div>
            <div className="tips-line">
                {
                    children.map((item:any, index: number) => {
                        return <ScrollPoint currentPage={currentIndex} index={index} key={index}></ScrollPoint>
                    })
                }               
            </div>
        </div>
    )
}