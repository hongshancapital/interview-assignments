/*
 * @Author: longsiliang longsiliang@sogou-inc.com
 * @Date: 2022-06-10 19:21:03
 * @LastEditors: longsiliang longsiliang@sogou-inc.com
 * @LastEditTime: 2022-06-14 14:58:27
 * @FilePath: /interview-assignments/frontend/src/App.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React from "react";
import "./App.css";

import { Carousel, Carouselitem } from './components';

import carouselSettings from './constants';

function App() {
  return (
    <div className="App">
      <Carousel>
        {carouselSettings.map(
          ({ title, desc, img, backgroundColor, color, key }) => {
            return (
              <Carouselitem
                title={title}
                desc={desc}
                img={img}
                backgroundColor={backgroundColor}
                color={color}
                key={key}
              ></Carouselitem>
            )
          }
        )}
      </Carousel>
    </div>
  );
}

export default App;
