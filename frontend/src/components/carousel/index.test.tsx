import React from 'react';
import "@testing-library/jest-dom/extend-expect";
import {render, cleanup} from '@testing-library/react'
import Carousel from './index'

afterEach(cleanup)

it('take a snapshot: Carousel', () => {
  const { asFragment } = render(<Carousel>
    <div>11111</div>
    <div>22222</div>
  </Carousel>)

  expect(asFragment()).toMatchSnapshot()
})

// afterEach(cleanup);
//
// it('1、一共几张图：', async () => {
//
//   const { getByTestId, getByText } = render(<Carousel />);
//
//   const contentList = getByTestId('main-content');
//
//
//   // expect(linkElement).toBeInTheDocument();
//
//   // const index = await waitForElement(() => getByText('1'))
//   //
//   // expect(index).toHaveTextContent('1')
//
// });
// it('2、几秒切换一张：', async () => {
//
//   const { getByTestId, getByText } = render(<Carousel />);
//
//   const contentList = getByTestId('main-content');
//
//   // expect(linkElement).toBeInTheDocument();
//   // const index = await waitForElement(() => getByText('1'))
//   // expect(index).toHaveTextContent('1')
//
// });
// it('3、1张图时轮播吗：', async () => {
//
//   const { getByTestId, getByText } = render(<Carousel />);
//
//   const contentList = getByTestId('main-content');
//
//   // expect(linkElement).toBeInTheDocument();
//   // const index = await waitForElement(() => getByText('1'))
//   // expect(index).toHaveTextContent('1')
//
// });