import React from "react";
import "./App.css";
import PresetCarousel from "./PresetCarousel";
import airpodsPng from './assets/airpods.png'
import iphonePng from './assets/iphone.png'
import tabletPng from './assets/tablet.png'

function App() {
    const carouselData = [
        {
            key: 'iphone',
            className: 'pageIphone',
            titleClassName: 'pageIphoneTitle',
            title: 'xPhone',
            subTitle: (
                <>
                    <div>Lots to love, Less to spend.</div>
                    <div>Starting at $399</div>
                </>
            ),
            imgSrc: iphonePng
        },
        {
            key: 'tablet',
            className: 'pageTablet',
            title: 'Tablet',
            subTitle: 'Just the right amount of everything.',
            imgSrc: tabletPng
        },
        {
            key: 'airpods',
            className: 'pageAirpods',
            title: (
                <>
                    <div>Buy a Tablet or xPhone for college.</div>
                    <div>Get arPods.</div>
                </>
            ),
            subTitle: '',
            imgSrc: airpodsPng
        },
    ]
    return <div className="App">
        <PresetCarousel data={carouselData}/>
    </div>;
}

export default App;
