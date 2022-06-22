import React from "react";
import "./App.css";
import { Carousel, ImageContent } from './shared/Components'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  return <div className="App">
    <Carousel 
      autoPlay={false}
    style={{
      height: '100vh'
    }}>
        <ImageContent basicBackgroundColor="#111111"
          style={{
            backgroundSize: '34% auto',
            backgroundPosition: '50% 50%'
          }}
        image={iphone} title={<p className="font-in-dark">xPhone</p>} description={
          <>
            <p className="font-in-dark">Lots to love. Less to spend</p>
            <p className="font-in-dark">Starting at $399</p>
          </>
        }></ImageContent>
        <ImageContent basicBackgroundColor="#fafafa" image={tablet} title="Tablet" description={'Just the right amount of everything'}></ImageContent>
        <ImageContent basicBackgroundColor="#f1f1f3" image={airpods} title={
          <>
          <p>Buy a Tablet or xPhone for college</p>
          <p>Get arPods</p>
          </>
        }></ImageContent>
    </Carousel>
  </div>;
}

export default App;
