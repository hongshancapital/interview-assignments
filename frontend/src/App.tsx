/*
 * @Description: demo示例
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-07 18:11:39
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-12 23:17:50
 */

import "./App.css";
import Carousel from "./components/Carousel";
const { CarouselItem } = Carousel;
function App() {
  return (
    <div className="app">
      <Carousel
        duration={4}
        animationSecends={0.8}
        onChange={(index) => {
          console.log("onChange index---", index);
        }}
      >
        <CarouselItem key={"carousel_item_phone"} >
          <div className="bg-iphone" data-testid="carousel-item-iphone">
            <div className="title">xPhone</div>
            <div className="text">Lots to love.Less to spend.</div>
            <div className="text">Starting at $399</div>
          </div>
        </CarouselItem>
        <CarouselItem key={"carousel_item_tablet"}>
          <div className="bg-tablet" data-testid="carousel-item-tablet">
            <div className="title">Tablet</div>
            <div className="text">Just the right amount of everything.</div>
          </div>
        </CarouselItem>
        <CarouselItem key={"carousel_item_airpods"} >
          <div className="bg-airpods" data-testid="carousel-item-airpods">
            <div className="title">Buy a Tablet or xPhone for college.</div>
            <div className="title">Get arPods.</div>
          </div>
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
