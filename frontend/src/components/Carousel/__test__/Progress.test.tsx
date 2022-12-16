import { render, screen } from '@testing-library/react';
import Progress from '../AutoProgress/Progress';

test('Progress component props test', async ()=>{
  render(
    <Progress
      percent={50}
      width={100}
      height={5}
      strokeColor='#ffffff'
      backgroundColor='#000000'
      className='test-progress' />
  );
  const getEleEventArr = ['carousel-progress', 'carousel-progress-bg']
    .map(id=>screen.queryByTestId(id));
  const [wrapper, innerEle] = await Promise.all(getEleEventArr);
  expect(wrapper).toHaveStyle({
    width: '100px',
    height: '5px',
    'background-color': '#000000'
  });
  expect(wrapper).toHaveClass('test-progress');
  expect(innerEle).toHaveStyle({
    'background-color': '#ffffff',
    width: '50%'
  });
});