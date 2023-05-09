import './App.css';
import Img1 from './assets/airpods.png'
import Img2 from './assets/iphone.png'
import Img3 from './assets/tablet.png'

import Carousel from "./components/Carousel";
import { CarouselInfo }from "./components/info/CarouselInfo";
import { CarouselItem }from "./components/item/CarouselItem";

// 轮播图数据
const info = [
  {
    id: 1,
    title: "xPhone",
    describe: "Lots to love,Less to spend.",
    image: Img1,
    color:"#000000"
  },
  {
    id: 2,
    title: "Tablet",
    describe: "Just the right amount of everything.",
    image: Img2,
    color:"#ffffff"
  },
  {
    id: 3,
    title: "Buy a Tablet or xPhone for college.",
    image: Img3,
    color:"#000000"
  }
];

function App() {
  return <div className='App'>
            <Carousel>
              {info?.map((item) => {
                return (
                  <CarouselItem
                    key={item.id}
                    styles={{ color:item.color }}
                  >
                    {<CarouselInfo
                      title={item.title}
                      describe={item.describe}
                      image={item.image}
                    /> as any}
                  </CarouselItem>
                );
              }) as any}
          </Carousel>
        </div>;
}

export default App;
