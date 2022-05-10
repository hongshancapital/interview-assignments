import React from "react";
import "./App.css";
import Carousel, {CustomCarouselItemProps} from "./components/Carousel";
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

const carouselList: CustomCarouselItemProps[] = [
    {
        title: "xPhone",
        subTitle: "Lots to love. Less to spend.\n Starting at $399.",
        image: iphoneImg,
        style: {
            color: "#FCFCFC",
            background: '#111111'
        }
    },
    {
        title: "Tablet",
        subTitle: "Just the right amount of everything.",
        image: tabletImg,
        style: {
            color: "#000",
            background: '#FAFAFA'
        }
    },
    {
        title: "Buy a tablet or xPhone for college.\n Get airPods",
        subTitle: "",
        image: airpodsImg,
        style: {
            color: "#000",
            background: '#F1F1F3'
        }
    }
]

function App() {
    return (
        <div className="App">
            <Carousel
                style={{width: '100vw', height: '100vh'}}
                duration={4000}
                speed={400}
            >
                {
                    carouselList.map((item, index: number) => (
                        <Carousel.CustomItem key={index} {...item} />)
                    )
                }
            </Carousel>
        </div>
    );
}

export default App;
