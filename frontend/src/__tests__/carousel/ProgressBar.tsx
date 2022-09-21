import React from 'react';
// import { act } from 'react-dom/test-utils';
import { render, fireEvent } from '@testing-library/react';
import ProgressBar from '../../components/Carousel/ProgressBar';
import carouselData from '../../mockData/carousel';

const delay = 3000;
const currentIndex = 0;
const clickProgressBar = () => {}

type Root = {
  queryByTestId: (arg: string) => void;
  container: HTMLElement
}

describe('test ProgressBar', () => {

  let progressBar: Root;
  let items: NodeList;

  beforeAll(() => {
    progressBar = render(
      <ProgressBar
        delay={delay}
        carouselData={carouselData}
        currentIndex={currentIndex}
        clickProgressBar={clickProgressBar}
      />
    )
    items = progressBar.container.querySelectorAll('.progress-bar-item');
  })

  test('成功渲染了进度条', () => {
    const bar = progressBar.queryByTestId('progress-bar');
    expect(bar).toBeInTheDocument();
  })

  test('成功渲染了进度条item, 且数量正确', () => {
    expect(items).toHaveLength(carouselData.length);
  })

  test('滑动到当前item后, className正确', () => {
    const mockCurrentIndex = 1;
    const mockProgressBar = render(
      <ProgressBar
        delay={delay}
        carouselData={carouselData}
        currentIndex={mockCurrentIndex}
        clickProgressBar={clickProgressBar}
      />
    )
    const activeItem = mockProgressBar.container.querySelectorAll('.progress-bar-item span');
    expect(activeItem[mockCurrentIndex]).toHaveClass('active');
  })

})

describe('进度条点击事件', () => {

  let progressBar: Root;
  let items: NodeList;
  const mockClickIndex = 2;
  let mockCallback: (clickIndex: number) => void;

  beforeAll(() => {
    mockCallback = jest.fn();
    progressBar = render(
      <ProgressBar
        delay={delay}
        carouselData={carouselData}
        currentIndex={currentIndex}
        clickProgressBar={mockCallback}
      />
    )
    items = progressBar.container.querySelectorAll('.progress-bar-item');
  })

  test('点击调用正常，参数传递正常', () => {
    fireEvent.click(items[mockClickIndex]);
    expect(mockCallback).toBeCalled();
    expect(mockCallback).toBeCalledWith(mockClickIndex);
    
    fireEvent.click(items[mockClickIndex]);
    fireEvent.click(items[mockClickIndex]);
    expect(mockCallback).toBeCalledTimes(3);
  })
  
})