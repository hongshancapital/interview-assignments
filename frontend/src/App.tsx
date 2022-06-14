import React from "react";
import Carousel from "./components/carousel";
import { SettingType } from "./components/carousel/types";
import ContentItem from "./components/contents";
import { contentConf, ContentItemType } from "./components/contents/conf";
import "./App.css";

function App() {

  const generateContentItems = (): [Function] => {
    return contentConf.map((i: ContentItemType) => {
      return (
        <ContentItem
          title={i.title}
          describetion={i.describetion}
          imgUrl={i.imgUrl}
          style={i.style}
        ></ContentItem>
      );
    });
  };

  const carouselSettings: SettingType = {
    content: generateContentItems(),
    intervalTime: 2400,
  };

  return (
    <div className="App">
      <Carousel {...carouselSettings}></Carousel>
    </div>
  );
}

export default App;
