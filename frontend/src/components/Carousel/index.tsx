import React,{ useState,useEffect } from 'react'
import './style.css'

let style = {
    container:{
        overflow:'hidden',
        height:'100vh'
    }
}

export const CarouselItem = ({id='',key = '', title = '',tips= '',img = ''}:any) => {
    return (<div 
        className='innerC'>
            <div className='itemInfo' data-id={key}>
                <h1 style={id === 0 ? {color:'#ffffff'} : {color:'#000000'}}>{title}</h1>
                <span style={id === 0 ? {color:'#ffffff'} : {color:'#000000'}}>{tips}</span>
            </div>
            <div className='itemImg'>
                <img src={img}></img>
            </div>
        </div>)
}




const Carousel = ({
    children = React.createElement('div'),
    tim
}:any) => {
    const [aindex,setAindex] = useState(0) //设置当前点击的表示 0、1、2
    const time = ((tim % 6000) /1000).toFixed(0)
    useEffect(() => {
       var time = setTimeout(() => {
            updateIndex(aindex+1)
        },tim)
        return () => {
            if(time) {
                clearTimeout(time)
            }
        }
    })
    
    //重复动画
    const replayAnimate = () => {
        document.getAnimations().forEach((animation) => {
            animation.cancel();
            animation.play()
        })
    }
    //更新点击标识
    const updateIndex = (nindex:number) => {
      if(nindex < 0) {
          nindex = React.Children.count(children) - 1
      }else if(nindex >= React.Children.count(children)) {
          nindex = 0
      }
      setAindex(nindex)
      replayAnimate()
    }

    //点击标识的方法
    const oncarHandle = (index:number) => {
        updateIndex(index)
        replayAnimate()
    }

    return (<div style={style.container}>
        <div 
        className="inner"
        style={{transform:`translateX(-${0})`}}>
            {React.Children.map(children,(child) => {
                return React.cloneElement(child,{width:'100%',height:'100vh'})
            })}
        </div>
        <div className='outer'>
            {React.Children.map(children,(child,index) => {
                return (<div  
                    className='outerI'
                    onClick={() => oncarHandle(index)}>
                        <div className='inside'
                        style={{
                            animationDuration:index == aindex ? `${time}s` : '0s',
                            backgroundColor:index == aindex ? '#ffffff' : '',
                        }}>
                            
                        </div>
                    </div>)
            })}
        </div>
    </div>)
}

export default Carousel;
