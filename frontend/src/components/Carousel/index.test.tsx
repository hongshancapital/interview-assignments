import { render } from '@testing-library/react';
import { Carousel } from './index';
import type { Frame } from '../../type';

import phone from '../../assets/iphone.png';
import airpods from '../../assets/airpods.png';
import tablet from '../../assets/tablet.png';

const DEFAULT_FRAMES: Frame[] = [
  {
    title: 'xPhone',
    description: 'Lots to love. Less to spend. Starting at $399.',
    post: phone,
    backgroundColor: '#111111',
    fontColor: '#fff',
  },
  {
    title: 'Tablet',
    description: 'Juest the right amount of everything.',
    post: tablet,
    backgroundColor: '#FAFAFA',
    fontColor: '#000',
  },
  {
    title: 'Buy a Tablet or xPhone for college. Get arPods.',
    post: airpods,
    backgroundColor: '#F1F1F3',
    fontColor: '#000',
  },
];

function asyncTimeout(timeout) {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve();
    }, timeout);
  });
}

test('render Carousel component', async () => {
  const { getByText } = render(<Carousel frames={DEFAULT_FRAMES} />);
  expect(getByText('xPhone')).toBeInTheDocument();
  await asyncTimeout(2200);
  expect(getByText('Tablet')).toBeInTheDocument();
  await asyncTimeout(2200);
  expect(
    getByText('Buy a Tablet or xPhone for college. Get arPods.')
  ).toBeInTheDocument();
});
