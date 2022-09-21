import React from 'react';
import { render } from '@testing-library/react';
import FullscreenBanner from '../../components/Carousel/FullscreenBanner';
import { ItemProps } from '../../components/Carousel/FullscreenBannerItem';
import carouselData from '../../mockData/carousel';

const mockCarouselData = [
  {
    id: 100,
    title: 'test title',
    describe: `test describe`,
    img: 'test img',
    themeColor: 'test #111',
    fontColor: 'test #fff'
  },
]

const delay = 3000;
const currentIndex = 0;

type Root = {
  queryByTestId: (arg: string) => void;
  getByText: (arg: string) => void;
  container: HTMLElement;
}

describe('测试 FullscreenBanner', () => {

  let banner: Root;
  let bannerDom: HTMLElement | null;
  beforeAll(() => {
    banner = renderFullscreenBanner(delay, mockCarouselData, currentIndex)
    bannerDom = banner.container.querySelector('.banner');
  })

  test('banner正常渲染', () => {
    const { getByText } = banner;
    expect(bannerDom).toBeInTheDocument();
    expect(getByText(mockCarouselData[0].title)).toBeInTheDocument();
  })

  test('banner数量渲染正确', () => {
    const fullBanner = renderFullscreenBanner(delay, carouselData, currentIndex)
    const fullBannerDom = fullBanner.container.querySelectorAll('.banner-item');
    expect(fullBannerDom.length).toBe(carouselData.length);
  })
  
})

type CarouselData = Array<ItemProps>

function renderFullscreenBanner(
  delay: number, carouselData: CarouselData, currentIndex: number
): Root {
  return (
    render(
      <FullscreenBanner
        delay={delay}
        carouselData={carouselData}
        currentIndex={currentIndex}
      />
    )
  )
}