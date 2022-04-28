import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { items, ItemProps } from './mock/data';

const renderCarouselItems = (item: ItemProps, index: number) => {
  return <div className="item" key={`itemKey${item.id}`} style={item.style}>
    <div className="head-wrap">
      { item.title && <h3 className="headline">{item.title}</h3> }
      { item.content && <h4 className="subhead">{item.content}</h4> }
    </div>
    <figure className="image-wrap">
      <img
        className="image"
        src={item.imgUrl}
        alt={item.title}
        // 优先加载首屏图片（最新chrome版本已支持），比较适用于轮播图场景
        fetchpriority={index === 0 ? 'high' : 'auto'}
      />
    </figure>
  </div>
}

function App() {
  return <div className="App">
      <Carousel >
        {
          items.map((item, index) => renderCarouselItems(item, index))
        }
      </Carousel>
  </div>;
}

export default App;
