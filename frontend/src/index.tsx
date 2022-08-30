import React from 'react';
import ReactDOM from 'react-dom';
import Carousel from './components/carousel';
import Page from './components/page';
import { CarouselList, animationProps } from './constants';
import 'reset.css';
import './index.css';

ReactDOM.render(
  <React.StrictMode>
    <Carousel {...animationProps}>
      {
        CarouselList.map(carousel => <Page {...carousel} />)
      }
    </Carousel>
  </React.StrictMode>,
  document.getElementById('root'),
);
