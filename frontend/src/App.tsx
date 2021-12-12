import React from 'react';
import './App.scss';
import Carousel from './Carousel';
import Description from './Description';
import Image from './Image';
import './Description.scss';
import {content, config} from './config';

function App() {
  const contents = () => {
    return content.map(item => {
      const {name, src, description} = item;
      return (
        <Image key={name} src={src} name={name}>
          <Description description={description} />
        </Image>
      );
    }) || <></>;
  };

  return (
    <div className="App">
      <Carousel {...config}>
        {contents()}
      </Carousel>
    </div>
  );
}

export default App;
