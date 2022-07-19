import React from 'react';
import { render } from '@testing-library/react';
import Carousel, {Progress} from './';

test('Carousel render item match passing products', () => {
  const products = [
    { title: "xPhone", subTitle: 'Lots to love. Less to spend. \n Starting at $399', imgUrl: '', style: { color: '#FFFFFF', backgroundColor: 'rgb(17,17,17)'},  },
    { title: "Tablet", subTitle: 'Just the right amount of everything.', imgUrl: '', style: {backgroundColor: 'rgb(250, 250, 250)'} },
    { title: "Buy a Tablet or xPhone for college. \n Get arPords.", subTitle: '', imgUrl: '', style: {backgroundColor: 'rgb(241, 241, 243)'}},
  ]
  const {getAllByTestId} = render(<Carousel products={products} delay={2000} />);
  expect(getAllByTestId("CarouselItem").length).toBe(products.length);
});

test("Carousel progress based on products", () => {
  const products = [
    { title: "xPhone", subTitle: 'Lots to love. Less to spend. \n Starting at $399', imgUrl: '', style: { color: '#FFFFFF', backgroundColor: 'rgb(17,17,17)'},  },
    { title: "Tablet", subTitle: 'Just the right amount of everything.', imgUrl: '', style: {backgroundColor: 'rgb(250, 250, 250)'} },
    { title: "Buy a Tablet or xPhone for college. \n Get arPords.", subTitle: '', imgUrl: '', style: {backgroundColor: 'rgb(241, 241, 243)'}},
  ]
  const { getAllByTestId } = render(<Progress products={products} delay={2000} curIdx={0} />);
  expect(getAllByTestId("CarouselProgressItem").length).toBe(products.length)
})
