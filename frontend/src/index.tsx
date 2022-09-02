import React from 'react';
import ReactDOM from 'react-dom';
import Carousel from 'src/components/carousel';
import Page from 'src/components/page';
import { CarouselList, animationProps } from 'src/constants';
import 'reset.css';
import './index.css';

ReactDOM.render(
  <React.StrictMode>
    <Carousel {...animationProps}>
      {
        CarouselList.map((carousel, i) => <Page key={i} {...carousel} />)
      }
    </Carousel>
  </React.StrictMode>,
  document.getElementById('root'),
);
