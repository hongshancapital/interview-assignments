import React,{ useState, useEffect } from "react";
import "./style.css";
import airpods from '../assets/1.png';
import iphone from '../assets/2.png';
import tablet from '../assets/3.png';

// 基于时间重复调用
function useInterval(callback:any, interval:any){
  useEffect(() => {
    const start = new Date().getTime()
    const I = setInterval(() => {
      callback(new Date().getTime() - start) //diff
    },interval)
    return () => clearInterval(I)
    // eslint-disable-next-line
  }, [])
}


function useSlider(N:any, speed = 3000) {
  const [slider,setSlider] = useState(-1)
  useInterval( (diff:any) => {
    setSlider( _ => Math.floor(diff / speed) % N)
  },3000)
  return {
    setSlider,
    slider
  }
}
const imgs = [
  airpods,
  iphone,
  tablet
];

const  Carousel = () => {
  const {slider,setSlider} = useSlider(imgs.length);
  useEffect(()=>{
      setSlider(0)
    // eslint-disable-next-line
  },[])
  
  return (
    <div className='scroller'>
      <div className='inner' 
      style={{
        width: `${imgs.length * 100}%`,
        transform: `translateX(-${100*slider/imgs.length}%)`
      }}>
        <div style={{
          width:`${100 / imgs.length}%`,
          position:"relative",
          color:"#fff",
        }}>
          <div className='item-slider1'  style={{
            background: ` url(${airpods}) #000 50% 75% no-repeat`,
            backgroundSize:'5%',
          }}></div>
          <div className='item-slider1_text'>
            <p className='item-slider1_text_title'>xPhone</p>
            <p>Lots to love.Less to spend</p>
            <p>Starting at $399.</p>
          </div>
          
        </div>
        <div style={{
          width:`${100 / imgs.length}%`,
          position:"relative",
        }}>
          <div className='item-slider1'  style={{
            background: ` url(${iphone}) #fefefe 50% 75% no-repeat`,
            backgroundSize:'5%',
          }}></div>
          <div className='item-slider1_text'>
            <p className='item-slider1_text_title'>Tablet</p>
            <p>Just the right amount of everything</p>
          </div>
          
        </div>
        <div style={{
          width:`${100 / imgs.length}%`,
          position:"relative",
        }}>
          <div className='item-slider1'  style={{
            background: ` url(${tablet}) #eee 50% 75% no-repeat`,
            backgroundSize:'5%',
          }}></div>
          <div className='item-slider1_text'>
            <p className='item-slider2_text_title'>Buy a Tablet or xPhone for college.</p>
            <p className='item-slider2_text_title'>Get arPods.</p>
          </div>
        </div>
      </div>
      {/* //slider-bar */}
          <div className='slider-view' >
            <div className='slider-parent'>
              <div>
                <div style={{
                  	width: `${slider=== 0 ? "30px": 0}`,
                    transition: `${slider=== 0? "width 3s" :""}`
                }}></div>
              </div>
              <div>
                <div style={{
                  	width: `${slider === 1? "30px": 0}`,
                    transition: `${slider=== 1? "width 3s" :""}`
                }}></div>
              </div>
              <div>
                <div style={{
                  	width: `${slider === 2? "30px": 0}`,
                    transition: `${slider=== 2? "width 3s" :""}`
                }}></div>
              </div>
            </div>
            
          </div>
    </div>
  );
}
export default Carousel
