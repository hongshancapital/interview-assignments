import { render, screen } from '@testing-library/react';
import Carousel from './index';
import bannerList  from  '../../constants/index';


describe('Carousel', () => {
    it('renders Carousel item', () => {
      render(<Carousel list={bannerList}  time={3}></Carousel>);
  
      expect(screen.getByLabelText('carousel-item')).toBeInTheDocument();
    });
});