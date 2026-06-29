import React from 'react';
import { Carousel } from 'antd';

const contentStyle: React.CSSProperties = {
  margin: 0,
  height: '740px',
  // color: '#fff',
  // lineHeight: '500px',
  // textAlign: 'center',
  // background: '#364d79',
  // images: string[]; // 图片数组

};
interface CarouselProps {
    images: string[]; // 图片数组
  }

const App: React.FC<CarouselProps> = ({images}) => {
  // const onChange = (currentSlide: number) => {
  //   console.log(currentSlide);
  // };

  return (
    <Carousel autoplay={true}>
            {
            images.map((item:any,index:number)=>{
                return <div>
                    <h3 style={contentStyle}>
                        <img src={item} alt="" />
                    </h3>
                </div> 
            })
            } 
    </Carousel>
  );
};

export default App;