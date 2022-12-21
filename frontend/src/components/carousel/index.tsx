import React, {  useEffect, useState } from 'react';
import './index.css'

export default () => {
    const imgContainer = [
        {
            imgSrc: require("../../assets/airpods.png")
        },
        {
            imgSrc: require("../../assets/iphone.png")
        },
        {
            imgSrc: require("../../assets/tablet.png")
        }
    ]

    function useInterval(callback: (time: number) => void, interval: number) {
        useEffect(() => {
            const start = new Date().valueOf();
            const I = setInterval(() => {
                callback(new Date().valueOf() - start)
            }, interval)
            return () => clearInterval(I)
        }, [])
    }

    function useCarouse(N: number, speed = 3000) {
        const [carouse, setCarouse] = useState<number>(0)
        useInterval((diff) => {
            setCarouse(Math.floor(diff / speed) % N)
        }, 300)
        return carouse
    }

    const slider: number = useCarouse(imgContainer.length);
    return (
      <div className="scroller">
          <div className="inner"
          style={{
              width: `${imgContainer.length * 100}%`,
              transform: `translateX(-${100 * slider/imgContainer.length}%)`
          }}>
              {imgContainer.map(src => {
                  return <img key={src.imgSrc} src={src.imgSrc}/>
              })}
          </div>
      </div>
    );
};
