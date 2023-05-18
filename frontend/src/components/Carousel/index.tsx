import { memo, useEffect, useState } from "react";
import classNames from "classnames";
import './index.css'
interface bannerType {
    id: string,
    src: string,
    descript: string,
    color: string
  }
interface ICarousel {
    bannerDataList: bannerType[];
    autoplay?: boolean;
  }
const Carousel = ({bannerDataList,autoplay=true}:ICarousel) => {
    const [activeIndex,setActiveIndex] = useState<number>(0)
    useEffect(()=>{
        changeIndex(activeIndex)
    },[activeIndex])
    useEffect(() => {
         const interval = setInterval(() => {
            console.log(activeIndex)
            if (activeIndex === bannerDataList.length-1) {
                setActiveIndex(0)
            } else {
                setActiveIndex(activeIndex+1)
            }
        }, 3000);
        return () => clearInterval(interval);
      }, [autoplay,activeIndex]);
    
    const handleClick = (index:number) => {
        setActiveIndex(index)
    }
    const changeIndex = (index:number) => {
        let x : number = 0
        const swipperWidth =  (document.getElementsByClassName('swiper-item')[0] as any).offsetWidth;
        const swipperWrapper = document.getElementsByClassName('swipperList')[0] as any
        x = -index * swipperWidth
        swipperWrapper.style.transform = `translate(${x}px, 0)`
    }
    return (
        <div className="swipperWrapper">
            <div className="swipperList">
                {
                    bannerDataList&& bannerDataList.map(data=> (
                        <div key={data.id} className="swiper-item">
                            <span className="descriptWrapper" style={{color:data.color}}>{data.descript}</span>
                            <img src={data.src} />
                        </div>
                    ))
                }
            </div>
            <div className="bannerSwitchWrapper">
                {
                    bannerDataList&& bannerDataList.map((data,index)=> (
                        <div key={data.id+'bannder'} className={classNames("switch-item",{"active-item":index===activeIndex})} onClick={()=>handleClick(index)}>
                            <div className="switch-fill"></div>
                        </div>

                    ))
                }
            </div>

        </div>
    )
}

export default memo(Carousel);