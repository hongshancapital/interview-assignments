import React, { useRef } from "react";
import "./App.css";

import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

import Carousel, { ICarouselProps,ICarouselItemProps } from "./components/Carousel/Index";

/* 
示例提供的三张图片大小不一，个别像素极高，此处通过background特殊处理（此处理屏幕高度变化时会产生异常）；理论应该要求图片大小统一，可使用img标签实现。
 */
const demoData: ICarouselItemProps[] = [
  {
    id: 1,
    title: "xPhone",
    description: <>Lots to love. Less to spend.<br />Starting at $399.  </>,
    style: {
      backgroundImage:`url(${iphone})`,
      backgroundSize: 'cover',
      backgroundPosition:'50% 100%',
      color:'#fff'
    }
  },
  {
    id: 2,
    title: "Tablet",
    description: "Just the right amount of everything.",
    style: {
      backgroundImage:`url(${tablet})`,
      backgroundSize: 'cover',
      backgroundPosition:'50% 15%',
    }
  },
  {
    id: 3,
    title: <>Buy a Tablet or xPhone for college. <br />Get arPods</>,
    style: {
      backgroundImage:`url(${airpods})`,
      backgroundSize: 'cover',
      backgroundPosition:'50% 15%',
    }
  }
];


function App(): JSX.Element {
  const refCarousel = useRef<any>()
  const onChange = (currentSlide: number) => {
    console.log("当前值:", currentSlide);
  };
  // console.log('refCarousel:',refCarousel)
/* 
  refCarousel.current.goTo(2) 指定面板
  refCarousel.current.prev()  上一个
  refCarousel.current.next()  下一个
  */

  const renderData = (data: ICarouselItemProps[]) =>
    data.map((item: ICarouselItemProps) => <Carousel.Item key={item.id} {...item} />);


  const props: ICarouselProps = {
    autoplay: true,
    easing: "linear",
    current:0,
    interval:1500,
    dots:true,
    onChange,
  };

  return (
    <div className="App">
      <Carousel ref={refCarousel} {...props}>
        {renderData(demoData)}
      </Carousel>
    </div>
  );
}

export default App;
