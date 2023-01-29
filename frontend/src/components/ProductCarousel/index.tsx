import Carousel from '../Carousel';
import imgIphone from '../../assets/iphone.png';
import imgAirpods from '../../assets/airpods.png';
import imgTablet from '../../assets/tablet.png';
import './style.css';
import { Item } from './Item';

const childMap = {
  [imgIphone]: (
    <>
      <h1 className="text-color-white mb-10">xPhone</h1>
      <h3 className="text-color-white">Lots to love. Less to spend.</h3>
      <h3 className="text-color-white">Starting at $399.</h3>
    </>
  ),
  [imgTablet]: (
    <>
      <h1 className="text-color-black mb-10">Tablet</h1>
      <h3 className="text-color-black">Just the right amount of everything.</h3>
    </>
  ),
  [imgAirpods]: (
    <>
      <h1 className="text-color-black">Buy a Tablet or xPhone for college.</h1>
      <h1 className="text-color-black">Get arPods.</h1>
    </>
  ),
};

/** 业务组件 */
const ProductCarousel = () => {
  return (
    <div
      style={{
        width: 668,
        aspectRatio: '1376 / 777',
      }}
    >
      <Carousel auto={true}>
        {[imgIphone, imgTablet, imgAirpods].map((src) => {
          return (
            <Item key={src} src={src}>
              {childMap[src]}
            </Item>
          );
        })}
      </Carousel>
    </div>
  );
};

export default ProductCarousel;
