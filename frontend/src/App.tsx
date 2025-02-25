import React from "react";
import Carousel, {CarouselItem, CarouselInfo} from "./components/Carousel";
import image1 from './assets/1.jpeg'
import image2 from './assets/2.jpeg'
import image3 from './assets/3.jpeg'
import image4 from './assets/4.jpeg'

interface infoType {
    image: string;
    id: number;
    describe: string;
    title: string,
    backgroundColor: string
}

// 轮播数据
const info: any = [
    {
        id: 1,
        image: image1,
    },
    {
        id: 2,
        image: image2,
    },
    {
        id: 3,
        image: image3,
    },
    {
        id: 4,
        image: image4
    },
];


const App = () => {
    return (
        <Carousel>
            {info?.map((item: infoType) => {
                return (
                    <CarouselItem key={item.id}>
                        <CarouselInfo image={item.image}/>
                    </CarouselItem>
                );
            })}
        </Carousel>
    );
};

export default App;

