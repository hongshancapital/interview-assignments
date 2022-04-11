import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import App from './App';
import Carousel from './carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

jest.useFakeTimers();

test('desc should render normal', () => {
  const { getByText } = render(<App />);
  const desc = getByText(/Lots to love.Less to spend./i);
  expect(desc).toBeInTheDocument();
});

test('autoplay should normal', async () => {
  const durationTime = 5000;
  const { getByTestId } = render(
    <Carousel
      datas={[{
        title: ['xPhone'],
        desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
        img: iphone,
        color: '#fff'
      }, {
        title: ['Tablet'],
        desc: ['Just the right amount of everything.'],
        img: tablet,
        color: '#000'
      }, {
        title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
        img: airpods,
        color: '#000'
      }]}
      duration={durationTime}>
    </Carousel>
  );
  expect(getByTestId('carousel').getAttribute('data-index')).toEqual('0');
  act(() => {
    jest.advanceTimersByTime(durationTime);
  })
  expect(getByTestId('carousel').getAttribute('data-index')).toEqual('1');
  act(() => {
    jest.advanceTimersByTime(durationTime);
  })
  expect(getByTestId('carousel').getAttribute('data-index')).toEqual('2');
  act(() => {
    jest.advanceTimersByTime(durationTime);
  })
  expect(getByTestId('carousel').getAttribute('data-index')).toEqual('0');
});
