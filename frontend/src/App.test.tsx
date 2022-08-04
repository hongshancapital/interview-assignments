import React from 'react';
import { render } from '@testing-library/react';
import { Carousel, Card } from './App';
import { CONFIG } from './constant';
import { act } from 'react-dom/test-utils';

const globalTimeout = global.setTimeout;

const template = <Carousel interval={1000}>
{
  CONFIG.map((item, index) => {
    const { title, content, fontColor, backgroundImage} = item;
    return <Card
      key={`${index}-${title}`}
      title={title}
      content={content}
      fontColor={fontColor}
      backgroundImage={backgroundImage}
    />
  })
}
</Carousel>

export const sleep = async (timeout = 0) => {
  await act(async () => {
    await new Promise(resolve => {
      globalTimeout(resolve, timeout);
    });
  });
};

test('renders slick', async () => {
  const { container  } = render(template);

  expect(container.querySelector('.slick-dot')).toHaveClass('slick-dot active');
  await sleep(1500);
  expect(container.querySelectorAll('.slick-list li')[1]).toHaveClass('slick-dot active');
});

test('renders card', async () => {
  const { container  } = render(template);
  await sleep(1500);
  expect(container.querySelectorAll('.scroller')[0]).toHaveStyle('transform: translateX(-100vw);');
  await sleep(1000);
  expect(container.querySelectorAll('.scroller')[0]).toHaveStyle('transform: translateX(-200vw);');
});

