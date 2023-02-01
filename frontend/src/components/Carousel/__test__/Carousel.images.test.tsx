import renderer from 'react-test-renderer';
import Carousel from '../index';

describe('Carousel images', () => {
  it('should match snapshots', () => {
    const carouselItems: React.ReactNode[] = [
      <div>1</div>,
      <div>2</div>,
      <div>3</div>,
    ]
    expect(renderer.create(<Carousel items={carouselItems}/>)).toMatchSnapshot();
  })
})