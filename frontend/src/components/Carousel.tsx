import React, {useState, useEffect} from "react";
interface Props {
  data: any;
  attr: any;
}


export const Carousel: any = (props: Props) => {
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
      zIndex: 1,
      WebkitTransition: `left ${props.attr.interval/1000}s ${props.attr.slideTransitionTiming} 0s`,/* Safari 和 Chrome */
      MozTransition: `left ${props.attr.interval/1000}s ${props.attr.slideTransitionTiming} 0s`,/* Firefox 4 */
      OTransition: `left ${props.attr.interval/1000}s ${props.attr.slideTransitionTiming} 0s`, /* Opera */
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

  // fill the bar progress
  const generateFill = (i: number, postIndex: number) => {
    let fillStyle = {
      zIndex: 1,
      width: '0%',
      height: 'inherit',
      background: props.attr.progressBarColor,
      WebkitTransition: `width ${props.attr.interval/1000}s ${props.attr.progressBarTransitionTiming} 0s`,/* Safari 和 Chrome */
      MozTransition: `width ${props.attr.interval/1000}s ${props.attr.progressBarTransitionTiming} 0s`,/* Firefox 4 */
      OTransition: `width ${props.attr.interval/1000}s ${props.attr.progressBarTransitionTiming} 0s`, /* Opera */
    }

    if (i === postIndex) {
      fillStyle.width = '100%'
      return fillStyle
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
  const renderSlide = (item: any, index: number) => {
    return (
      <div className="main-post" style={generateMove(index, postIndex)} key={index}>
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
            <div className="default-bar" style={generateFill(index, postIndex)} ></div>
          </div>
        )
      } else {
        return (
          <div className="bar-container" key={index}>
            <div className="default-bar" style={generateFill(index, postIndex)} ></div>
          </div>
        )
      }
  }
  return (
    <div className="carousel-wrapper">
      <div className="sildes">
      {
        props.data.length?
          props.data.map((item: any, index: any) => {
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
        {props.data.map((item: any, index: any) => {
          return (
            renderProgressBar(index)
          )
        })}
      </div>
    </div>
  )
}



