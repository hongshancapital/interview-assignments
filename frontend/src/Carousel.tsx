import React, {useState, useEffect,useRef} from "react"
import "./assets/css/carousel.css"

interface imgArrs {
    id : number ,
    color : string ,
    title : string,
    subTitleOne : string,
    subTitleTwo : string,
}

interface imgArrsProps {
    imgArrs : imgArrs[]
}

function Carousel(props : imgArrsProps){
  const {imgArrs} = props ;
  const carouselContainerRef = useRef<HTMLDivElement>(null);

    //当前的索引
  let [currentIndex,setCurrentIndex] = useState(0);

    function movePics(currentIndex:number) : void {
        if(carouselContainerRef && carouselContainerRef.current){
            carouselContainerRef.current.style.left = "-" + (currentIndex) * 1000 + 'px';
            carouselContainerRef.current.className = "carousel-container moveTransition";
        }

    }

  useEffect(()=>{
    let picTimeOutObj = setTimeout(()=>{
        if(currentIndex < imgArrs.length - 1){
            setCurrentIndex(currentIndex + 1)
        }else{
            setCurrentIndex(()=> currentIndex = 0)
        }
    },2000);

    //移动图片
    movePics(currentIndex);

    return ()=>{
        clearTimeout(picTimeOutObj)
    }
  },[currentIndex]);

  return (
      <div className="carousel-warp">
          <div className="carousel-container" ref={carouselContainerRef}>
              {
                  imgArrs.map((item,index)=>{
                    return (
                        <div className={["carousel-item","carousel-item-"+index].join(' ')} key={item.id}>
                            <div className="pic-desc" style={{color: item.color}}>
                                <div className="title">{item.title}</div>
                                <div className={index < 2 ? "sub-title-one" : 'title'}>{item.subTitleOne}</div>
                                <div className="sub-title-two">{item.subTitleTwo}</div>
                            </div>
                            {/*<img src={item.src} alt=""/>*/}
                        </div>
                    )
                  })
              }
          </div>
          <div className="dot-block">
              {
                  imgArrs.map((item,index)=>{
                      return (
                          <div className="dot-item">
                              <div className={["dot-inside", index === currentIndex ? "dot-active" : ''].join(' ')}>

                              </div>
                          </div>
                      )
                  })

              }
          </div>
      </div>
  )
}

export default Carousel;

