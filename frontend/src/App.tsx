import './App.css';
import Carousel from './Carousel'
import React from "react";
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'
import Item from './Carousel/Item'

const itemList = [
    {
        imgSrc: iphone,
        title: <div style={{color: 'white', fontSize: '60px', lineHeight: '60px', fontWeight: 'bold'}}>xPhone</div>,
        subTitle: <div style={{color: 'white', fontSize: '30px'}}>Lots to love. Less to spend.<br/>Starting at $399.
        </div>
    },
    {
        imgSrc: tablet,
        title: <h1 style={{color: 'black', fontSize: '50px'}}>Tablet</h1>,
        subTitle: <h2 style={{color: 'black', fontSize: '30px'}}>Just the right amount of everything.</h2>
    },
    {
        imgSrc: airpods,
        title: <h1 style={{color: 'black', fontSize: '50px'}}>Buy a Tablet or xPhone for college.<br/>Get arPods.</h1>,
        subTitle: null
    },
]

function App() {
    const {innerWidth, innerHeight} = window
    return <div className='App'>
        <Carousel defaultCounter={2} duration={.5} width={innerWidth} height={innerHeight}>
            {
                itemList.map((item, index) => (
                    <Item key={index} {...item}/>))
            }
        </Carousel>
    </div>;
}

export default App;
