import { useCallback } from 'react';
import Carousel from './components/carousel';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import './App.css';

interface Slider {
    title: string,
    desc?: string,
    price?:string,
    log:string,
    background: string,
    className?:string
}

function App() {

  //图片大小不一致，需要特殊处理
  const sliderList:Slider[] = [{
    title: 'xPhone',
    desc: 'Lots to love.Less to spend.',
    price:'Starting at $399',
    log: iphone,
    background: '#111',
    className:'first-page'
  },{
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    log: tablet,
    background: '#fafafa'
  },{
    title: 'Buy a Tablet or xPhone for college. \n Get arPods',
    price:undefined,
    log: airpods,
    background: '#f2f2f4'
    }]
  
  const handleSwitch = useCallback((index: number) => {
    console.log(`滚动到${index + 1}页`)
  }, [])

  return (
    <div className='App'>
      <Carousel
        className="carousel-body"
        speed={2500}
        onSwitch={handleSwitch}
      >
        {
          sliderList.map((slider, index) => (
            <div
              key={`${slider.title}_${index}`}
              className={`${slider.className ?? ''} slider-page`}
              style={{ backgroundColor: slider.background }}
            >
              <div className="slider-content">
                <h1 className='slider-title'>{slider.title}</h1>
                {
                  slider.desc && 
                  <p>{slider.desc }</p>
                }
                {
                  slider.price && 
                  <p>{slider.price }</p>
                }
              </div>
              <img src={slider.log} alt='log' className='slider-log'/>
            </div>
          ))
        }
      </Carousel>
    </div>
  );
}

export default App;
