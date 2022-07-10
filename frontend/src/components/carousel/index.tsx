
import React, {useEffect, useState, useRef, useMemo} from "react";
import ProgressBar from '../progress'
import {imagesList, imgContainerWidth} from '../../interface/carousel'
import Airpods from "../../assets/airpods.png";
import Iphone from "../../assets/iphone.png";
import Tablet from "../../assets/tablet.png";
import "./index.css";

const imgs:Array<imagesList> = [
  {id:1,url:Iphone,text:'xPhone', smallText:`Lots to love. Less to spend. \n Starting at $399.`},
  {id:2,url:Tablet,text:'Tablet', smallText:'Just the right amount of everything.'},
  {id:3,url:Airpods,text:`Buy a Tablet or xPhone for college. \n Get arPods`}
]

function Carousel() {
  const [containerLeft, setContainerLeft] = useState<number>(0)
  const boxRef = useRef<HTMLUListElement>(null)
  const currentIndex = useRef<number>(-0)
  let timer:NodeJS.Timeout | null = null;
  const imgContainerW:imgContainerWidth = 800
  
  const containerWidth = useMemo(()=>imgContainerW * imgs.length, [imgs.length])

  const swapImg = () => {
    setContainerLeft(-currentIndex.current * imgContainerW)
    currentIndex.current++;
  }

  const swapFormat = () => {
    if (currentIndex.current >= 2) {
      swapImg();
      currentIndex.current = 0;
    }else{
      swapImg();
    }
  }

  useEffect(()=>{
    swapFormat()
    timer = setInterval(swapFormat, 3000)
    return ()=>{
      timer && clearInterval(timer)
      timer = null
    }
  },[])

  return <div className="carousel-wrap">
    <div className="banner_container">
      <ul className="img_box" ref={boxRef} style={{left:containerLeft+'px', width:containerWidth+'px'}}>
        {
          imgs.map((item, i)=><li key={item.id} style={{backgroundImage:`url(${item.url})`}}>
            <div className={i==0?'specialText':''}>
              <div>{item.text}</div>
              {item.smallText && <div className="sub-text">{item.smallText}</div>}
            </div>
            </li>)
        }
      </ul>
      <ProgressBar currentIndex={currentIndex.current} list={imgs}/>
    </div>
  </div>;
}

export default Carousel;