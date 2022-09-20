import React from 'react';
import { render } from '@testing-library/react';
import Carousel from '.';
import CarouselItem from './item';
// TODO 单元测试这块之前计划了但是还没来及了解，目前确实这块是个知识盲区。但是相信可以快速掌握起来。
test('carousel基础渲染测试', () => {
  const renderRes = render(<div>
    <Carousel height={450}>
    <CarouselItem>
      page1
    </CarouselItem>
    <CarouselItem>
      page2
    </CarouselItem>
  </Carousel>
  </div>)
  const {getByText} = renderRes
  const page1 = getByText(/page1/i);
  const page2 = getByText(/page2/i);
  expect(page1).toBeInTheDocument();
  expect(page2).toBeInTheDocument();
  // 高度测试
  // 默认播放间隔测试
  // 默认播放时长
  // 默认动画类型
})