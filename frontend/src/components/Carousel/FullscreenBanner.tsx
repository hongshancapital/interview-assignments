import React, { ReactElement } from 'react';
import MemoBannerList, { ItemProps } from './FullscreenBannerItem';

interface BannerProps {
  carouselData: Array<ItemProps>;
  currentIndex: number;
  delay: number;
}

function FullscreenBanner(props: BannerProps): ReactElement {

  const { carouselData, currentIndex } = props;
  const containerW = `${carouselData.length * 100}vw`;

  return (
    <div
      className="banner"
      style={{
        width: containerW,
        transform: `translateX(-${currentIndex * 100}vw)`
      }}
    >
      <MemoBannerList carouselData={carouselData} />
    </div>
  )
}

export default FullscreenBanner;