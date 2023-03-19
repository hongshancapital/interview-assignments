import { ICarouselItem } from '../Carousel/types';
import iphone from '../assets/iphone.png';
import tablet from '../assets/tablet.png';
import airpods from '../assets/airpods.png';

import './index.scss';

const items: ICarouselItem[] = [
  {
    key: 'iphone',
    children: <div className='demo-container iphone'>
      <div className='empty'></div>
      <div className='demo-content'>
        <div className='title'>xPhone</div>
        <div className='text'>Lots to love. Less to speed.<br />Starting at $399</div>
      </div>
      <div className='img-container'>
        <img src={iphone} alt="" />
      </div>
    </div>
  },
  {
    key: 'tablet',
    children: <div className='demo-container tablet'>
      <div className='empty'></div>
      <div className='demo-content'>
        <div className='title'>Tablet</div>
        <div className='text'>Just the right amount of everything</div>
      </div>
      <div className='img-container'>
        <img src={tablet} alt="" />
      </div>
    </div>,
  },
  {
    key: 'airpods',
    children: <div className='demo-container airpods'>
      <div className='empty'></div>
      <div className='demo-content'>
        <div className='title'>Bug a Tablet or xPhone for collage.<br />Get airPods</div>
      </div>
      <div className='img-container'>
        <img src={airpods} alt="" />
      </div>
    </div>,
  }
]
export default items;