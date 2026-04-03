import { render } from '@testing-library/react';
import Carousel from './index';

describe('Carousel test', () => {
 
  test('Should render carousel items and indicators', () => {
    const { container } = render(<Carousel>
      <div>111</div>
      <div>222</div>
      <div>333</div>
    </Carousel>)

    expect(container.querySelectorAll('.carousel-item').length).toBe(3)
    expect(container.querySelectorAll('.carousel-indicator').length).toBe(3)
  })

})