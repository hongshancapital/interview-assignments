import React, {useState, useEffect} from "react";
import { IProps, IImageData } from "./interface"


export const Carousel = (props: IProps) => {
  const [postIndex, setPostIndex] = useState(0)
  // change post index
  const progress = () => {
    if(postIndex === props.data.length - 1) {
      setPostIndex(0)
    } else {
      setPostIndex(postIndex + 1)
    }
  }

  // move slide
  const generateMove = (i: number, postIndex: number) => {
    let style = {
      left:  ''
    }
    if (i < postIndex) {
      style.left = '-'+ (postIndex-i)*100 + '%'
      return style
    } 
    if (i === postIndex) {
      style.left = '0%'
      return style
    }
    if (i > postIndex) {
      style.left = (i-postIndex)*100 + '%'
      return style
    }
  }

  // handle  click bar progress get to the correct slide
  const handleBarClick = (key: number) => {
    setPostIndex(key)
  }
  
  // set interval 
  useEffect(() => {
    const timer = setInterval(progress, props.attr.interval)
    return () => {
      clearInterval(timer)
    }
  })

  // render slide, @params item: img data, index: loop slide index
  const renderSlide = (item: IImageData, index: number) => {
    return (
      // to do leftEaseFiveSeconds add conditions
      <div className={['main-post', props.attr.slideTransitionClassName].join(' ')} style={generateMove(index, postIndex)} key={index}>
        <div className="image" >
          <img src={item.url} alt=""/>
        </div>
        <div className="content">
          <h1 className="title" style={{color: item.titleColor}}>{item.title}</h1>
          <p className="text" style={{color: item.textColor}}>{item.text}</p>
        </div>
      </div>
    )
  }
  
  // render progress bar, @params index: loop slide index
  const renderProgressBar = (index: number) => {
      if (props.attr.barClickChangeImg) {
        return (
          <div className="bar-container" key={index} onClick={() => handleBarClick(index)} style={{cursor: 'pointer'}}>
            <div className={['default-bar', index === postIndex? props.attr.progressBarClassName :''].join(' ')}></div>
          </div>
        )
      } else {
        return (
          <div className="bar-container" key={index}>
            <div className={['default-bar', index === postIndex? props.attr.progressBarClassName :''].join(' ')}></div>
          </div>
        )
      }
  }
  return (
    <div className="carousel-wrapper">
      <div className="sildes">
      {
        props.data.length?
          props.data.map((item: IImageData, index: number) => {
            return renderSlide(item, index)
          }) :    
          <div className="main-post" >
            <div className="content">
              <p style={{color: 'black'}}>{'There is no image'}</p>
            </div>
          </div>
      }
      </div>
      <div className="progress-bar">
        {props.data.map((item: IImageData, index: number) => {
          return (
            renderProgressBar(index)
          )
        })}
      </div>
    </div>
  )
}



