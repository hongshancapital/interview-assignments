import React from "react";
import Carousel from './components/Carousel/index'


import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

const imgUrl = [
    {
        img: iphone,
        title: 'xPhone',
        describe: 'Lots to love. Less to spend.\n Starting at $339',
        color: '#f1f1f3',
        bgColor: '#000000'
    },
    {
        img: tablet,
        title: 'Tablet',
        describe: 'Just the right amount of everything.',
        color: '#000000',
        bgColor: '#fff'
    },
    {
        img: airpods,
        title: 'Buy a Tablet or xPhone for college.\n Get arPods.',
        // describe:'tab描述',
        color: '#000',
        bgColor: '#fafafa'
    }
]

function App() {
    return <div className="App">
        <Carousel imgUrl={imgUrl}/>
    </div>;
}

export default App;
