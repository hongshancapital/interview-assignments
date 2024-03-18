import './App.css';
import React from "react";
import Carousel from "./swiper";
import img1 from './assets/airpods.png'
import img2 from "./assets/iphone.png";
import img3 from "./assets/tablet.png";
// App.tsx

const images = [
    {
        img: img2,
        text: (<div>
            <div className={'title'} style={{color:'#fff', height:'unset'}}>xPhone</div>
            <div style={{color:'#fff',  height:'unset'}}>Lots to love.Less to spend.</div>
            <div style={{color:'#fff',  height:'unset'}}>Starting at $399.</div>
        </div>)
    },
    {
        img: img1,
        text: (<div>
            <div  className={'title'}>Tablet</div>
            <div>Just the right amount of everything.</div>
        </div>)
    },
    {
        img: img3,
        text: (<div>
            <div  className={'title'}>Buy a Tablet or xPhone for College.</div>
            <div>Get airPods</div>
        </div>)
    }
];


const App:React.FC = () => {
  return <div className='App'>
      <Carousel width={'100vw'} height={'100vh'} images={images} >
          {
              images.map((item, index) => {
                  return (
                      <div style={{position:'relative'}} key={index}>
                          <div style={{position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', height:'unset'}}>{item.text}</div>
                          <img src={item.img} alt={`slide-${index}`} />
                      </div>
                  )
              })
          }
      </Carousel>
  </div>;
}

export default App;
