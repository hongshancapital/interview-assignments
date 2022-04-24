import React from "react";
import "./App.css";
import Carousel from './Carousel';
import CarouselItem from './CarouselItem';

const contentStyle: any = {
    height: '100vh',
    color: '#fff',
    lineHeight: '160px',
    textAlign: 'center',
    background: '#364d79',
};

function App() {
  return (
      <div className="App">
        <Carousel autoplay>
            <div>
                <CarouselItem
                    style={{ background: '#000', height: '100vh' }}
                    title={(
                        <div style={{ color: '#fff' }} >XPhone</div>
                    )}
                    description={(
                        <div style={{ color: '#fff' }}>
                            <div>Lots to love, less to spend.</div>
                            <div>Starting at $399.</div>
                        </div>
                    )}
                />
            </div>
            <div>
                <CarouselItem
                    style={{ background: '#fff', height: '100vh' }}
                    title={(
                        <div style={{ color: '#000' }} >
                            Tablet
                        </div>
                    )}
                    description={(
                        <div style={{ color: '#000' }}>
                            <div>Just the right amount of everything.</div>
                        </div>
                    )}
                />
            </div>
            <div>
                <CarouselItem
                    style={{ background: '#fff', height: '100vh' }}
                    title={(
                        <div style={{ color: '#000' }} >
                            Buy a Tablet or xPhone for college.
                        </div>
                    )}
                    description={(
                        <div style={{ color: '#000' }}>
                            <div>Get arPods</div>
                        </div>
                    )}
                />
            </div>
        </Carousel>
      </div>
  );
}

export default App;
