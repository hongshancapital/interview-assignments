import * as React from "react";
import { Carousel } from "components";

import { Template } from "./Template";

import { data } from "./mock";
import "./index.css";

export const StoryBoard: React.FC<any> = () => {
  return (
    <div className="storyboard-layout">
      <Carousel autoplay={true}>
        {data.map((item) => (
          <Template {...item} key={item?.key}/>
        ))}
      </Carousel>
    </div>
  );
};
