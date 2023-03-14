import React from 'react';
import './App.css';
import Carousel from './components/Carousel';
import SlidCard from './components/SliderCard/SlidCard';
import iphoneSrc from './assets/iphone.png';
import ipadSrc from './assets/tablet.png';
import ipodsrc from './assets/airpods.png';

function App() {
    return <div className="App">
        <Carousel duration={2}>
            <SlidCard
                title="xPhone"
                imgSrc={iphoneSrc}
                theme="dark"
                description={
                    <>
                        <p>Lots to love. Less to spend</p>
                        <p>Starting ad $399</p>
                    </>
                }
                background="#111"
            />
            <SlidCard
                title="Tablet"
                imgSrc={ipadSrc}
                theme="light"
                description="Just the right amount of everything"
                background="#FAFAFA"
            />
            <SlidCard
                title={
                    <>
                        <p>Buy a Tablet or xPhone for college.</p>
                        <p>Get airPods</p>
                    </>
                }
                imgSrc={ipodsrc}
                theme="light"
                background="#F1F1F3"
            />
        </Carousel>
    </div>;
}

export default App;
