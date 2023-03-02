import './App.css';
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import React, {FC} from "react";

interface CarouselViewProps {
    children?: JSX.Element
}
const CarouselView: FC<CarouselViewProps> = (props) => {
    return <div className='carousel-view-container'>
        {props.children}
    </div>
}
function App() {
    return <div className='App'>
        {/* write your component here */}
        <Carousel duration={5000}>
            <CarouselView>
                <div className='iphone-view'>
                    <div className="carousel-container-content">
                        <div className="title">
                            xPhone
                        </div>
                        <div className="text">
                            <p>Lots to Love.Less to spend.</p>
                            <p>Starting at $399.</p>
                        </div>
                    </div>
                    <img src={iphone} alt='iphone' className='iphone-img'/>
                </div>
            </CarouselView>
            <CarouselView>
                <div className="tablet-view">
                    <div className="carousel-container-content">
                        <div className="title">
                            Tablet
                        </div>
                        <div className="text">
                            <p>Just the right amount of everything.</p>
                        </div>
                    </div>
                    <img src={tablet} alt='tablet' className='tablet-img'/>
                </div>
            </CarouselView>
            <CarouselView>
                <div className="airpods-view">
                    <div className="carousel-container-content">
                        <div className="title">
                            <p>Buys a Tablet or xPhone for collage.</p>
                            <p>Get arPods.</p>
                        </div>
                    </div>
                    <img src={airpods} alt='airpods' className='airpods-img'/>
                </div>
            </CarouselView>
        </Carousel>
    </div>;
}

export default App;
