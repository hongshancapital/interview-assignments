import React from 'react';
import { Carousel } from './components';
import banner_1 from './assets/iphone.png';
import banner_2 from './assets/tablet.png';
import banner_3 from './assets/airpods.png';

/**
 * NOTE:
 *
 * 1. In general, the banner info should be flexible config by user.
 *
 * 2. Carousel component don't care what be rendered in the Carousel.Item .
 */
const bannerConfig = [
  {
    id: 1,
    title: {
      value: 'xPhone',
      style: {
        color: '#fff',
      },
    },
    subtitle: {
      value: 'Lots to love. Less to spend.',
      style: {
        color: '#fff',
      },
    },
    desc: {
      value: 'Starting at $399.',
      style: {
        color: '#fff',
      },
    },
    cover: {
      value: banner_1,
      style: {},
    },
  },
  {
    id: 2,
    title: {
      value: 'Tablet',
      style: {},
    },
    subtitle: {
      value: 'Just the right amount of everything.',
      style: {},
    },
    desc: null,
    cover: {
      value: banner_2,
      style: {},
    },
  },
  {
    id: 3,
    title: {
      value: 'Buy a Tablet or xPhone for college.',
      style: {},
    },
    subtitle: {
      value: 'Get airPods.',
      style: {
        marginTop: 6,
        fontSize: 48,
        fontWeight: 'bold',
      },
    },
    desc: null,
    cover: {
      value: banner_3,
      style: {},
    },
  },
];

export const Banners = () => {
  return (
    <div className="pr-banner-container">
      <Carousel
        style={{ height: '100%' }}
        interval={3000}
        // autoplay={false}
        indicator={<Carousel.Indicator className="pr-banner-indicator" />}
      >
        {bannerConfig.map((banner) => (
          <Carousel.Item key={banner.id} className="pr-banner-item">
            <div className="pr-banner-info">
              {banner.title && (
                <p className="pr-banner-title" style={banner.title.style}>
                  {banner.title.value}
                </p>
              )}
              {banner.subtitle && (
                <p className="pr-banner-subtitle" style={banner.subtitle.style}>
                  {banner.subtitle.value}
                </p>
              )}
              {banner.desc && (
                <p className="pr-banner-desc" style={banner.desc.style}>
                  {banner.desc.value}
                </p>
              )}
            </div>
            <img
              alt="item one"
              src={banner.cover.value}
              style={banner.cover.style}
              className="pr-banner-cover"
            />
          </Carousel.Item>
        ))}
      </Carousel>
    </div>
  );
};
