import React from 'react';
import { Carousel } from '../../components';
import './index.sass';

const data = [{
  backgroundColor: 'rgb(17, 17, 17)',
  backgroundImage: require('../../assets/iphone.png'),
  title: 'xPhone',
  content: 'Lots to love. Less to spend.\n Starting at $399.',
  color: '#fff'
}, {
  backgroundColor: 'rgb(250 250 250)',
  backgroundImage: require('../../assets/tablet.png'),
  title: 'Tablet',
  content: 'Just the right amount of everything.',
  color: '#000'
}, {
  backgroundColor: 'rgb(241, 241, 243)',
  backgroundImage: require('../../assets/airpods.png'),
  title: 'Buy a Tablet or xPhone for college. \n Get arPods.',
  content: '',
  color: '#000'
}];


function CarouselPage(): JSX.Element {

  return (<div className='carousel-page-main'>
    <div className='carousel-page-container'>
      <div className='carousel-pages-content'>
        <Carousel
          autoplay={true}
          intervalTime={3000}
          speed={500}
        >
          {data.map((item, index) => {
            const { backgroundColor, backgroundImage, title, content, color } = item;
            
            return (<div 
              className='carousel-page-content' 
              key={index} 
              style={{
                backgroundColor: backgroundColor,
                backgroundImage: `url(${backgroundImage})`,
                color: color
              }}>
              <div className='carousel-page-content-title'>{title}</div>
              <div className='carousel-page-content-content'>{content}</div>
            </div>)
          })}
        </Carousel>
      </div>
    </div>
  </div>)
}


export default CarouselPage;