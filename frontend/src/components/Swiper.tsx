import React,{ useEffect } from "react";

import './swiper.css'
interface childProps{
  img_urls:Array<string>,
  time:number
}

const Swiper:React.FC<childProps> = (props)=> {
  let {img_urls,time} = props;
  let urls:Array<any> = JSON.parse(JSON.stringify(img_urls))
  urls.push(urls[0])


  //只执行一次 传参[] componentDidMount，传参数[img_urls] componentDidUpdate 和 在函数里面return componentWillUnmount
  useEffect(()=>{
    const w:number = document.documentElement.clientWidth
    const h:number = document.documentElement.clientHeight
    const nodes:any = document.getElementsByClassName("swiper-item")
    for(let v of nodes){
      v.style.width = w+'px'
      v.style.height = h+'px'
    }
    
    let length = urls.length
    let l = 0
    let i = 0
    let p = 0
    let t:number = time/100
    setInterval(()=>{
      let t = i*30
      p = Number((t/time).toFixed(2))
      if(p===1){
        let s:any =  document.getElementsByClassName('swiper')[0]
      if(l<length-2){
        l = l+1
        s.style.transitionDuration = '1s'
      }else{
        s.style.transitionDuration="0s"
        l=0
      }
      s.style.transform = 'translateX('+(-w*l)+'px)'
      }
        let dots:any = document.getElementsByClassName('dot')
        
        dots[l].childNodes[0].style.width = p*51+'px'
      if(i===100){
        i = 0
        p = 0
        for(let item of dots){
          item.childNodes[0].style.width = p*50+'px'
        }
      }else{
        i+=1
      }
    },t)
  },[time,urls])
  function getText(type:number) {
    if(type===1){
      return (
        <div className="t1">
          <p>Tablet</p>
          <p>Just the right amount of everything.</p>
        </div>
      )
    }else if(type === 2){
      return (
        <div className="t2">
          <p>Buy a Tablet or Xphone for college.</p>
          <p>Get arPods.</p>
        </div>
      )
    }else{
      return (
        <div className="t0">
          <p>xPhone</p>
          <p>Lots to love.Less to spend.</p>
          <p>Starting at $399.</p>
        </div>
      )
    }
  }
  return (
    <div className="swiperContainer">
      <div className="swiper">
      {
        urls.map((v:any,key:number)=>{
          const showText:any = getText(key)
           return (
             <div key={key} style={{background:'url('+v.default+') center no-repeat',backgroundSize:'100% 100%',flexShrink:0
            }} className="swiper-item">
                {showText}
             </div>
           )
        })
      }
    </div>
    <div className="dots">
    {
      img_urls.map((v:any,key:any)=>{
        return (
          <div className="dot" key={key}>
                <div></div>
          </div>
        )
      })
    }
    </div>
    </div>
  );
}

export default Swiper;