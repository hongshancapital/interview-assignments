import React, { useEffect, useState } from 'react';
import "./App.scss";

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
const Images = [airpods, iphone, tablet]

function App() {
    const imgList = Images.map((imgUrl, index) =>
      <li key={index}><img src={imgUrl}/></li>
    );
    const barList = Images.map((imgUrl, index) =>
      <li key={index}></li>
    );
    // active index
    let [activePointIndex, setActivePointIndex] = useState(0);

    useEffect(() => {
        changeImage(activePointIndex);
        changBar(activePointIndex);
    }, [activePointIndex])

    useEffect(() => {
        let timers = setTimeout(() => {
            let activeIndex = activePointIndex == imgList.length-1 ? 0 : activePointIndex+1
            setActivePointIndex(activeIndex)
        }, 5000);
    }, [activePointIndex])
    // transform img
    const changeImage = (activePointIndex: any) => {
        document.querySelectorAll(".images")[0].setAttribute('style', `transition:all 0.3s;transform:translateX(-${960 * activePointIndex}px);`)
    }
    // transfrom bar
    const changBar = (activePointIndex: any) => {
        let barList = document.querySelectorAll(".bars li");
        for (let i = 0; i < barList.length; i++) {
            barList[i].className = "";
        }
        barList[activePointIndex].className = "active";
    }
    return (
      <div className="App">
        <ul className="images">
          { imgList }
        </ul>
        <ul className="bars" data-testid="not-empty">
          { barList }
        </ul>
      </div>
    )
}
export default App;
