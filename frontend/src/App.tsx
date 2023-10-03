import { useState } from 'react';
import './App.css';
import Carousel, { CarouselItemProps } from './Carousel';
import img1 from './assets/iphone.png';
import img2 from './assets/tablet.png';
import img3 from './assets/airpods.png';
const dataList: CarouselItemProps[] = [
    {
        title: 'XPhone',
        description: 'Lots to love. Less to spend. Starting at $399.',
        image: img1,
        key: 1,
        textClassName: 'color-white'
    },
    {
        title: 'Tablet',
        description: 'Just the right amount of everything.',
        image: img2,
        key: 2,
    },
    {
        title: 'XPhone',
        description: 'Buy a Tablet or xPhone for college. GetarPods',
        image: img3,
        key: 3,
    },
];
function App() {
    const [data] = useState<CarouselItemProps[]>(dataList);
    return (
        <div className="App">
            <Carousel list={data} />
        </div>
    );
}

export default App;
