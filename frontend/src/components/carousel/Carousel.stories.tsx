import { Meta, Story } from '@storybook/react';
import * as React from 'react';

import { Carousel } from './Carousel';

const meta: Meta = {
  title: 'Components/Carousel',
  component: Carousel,
  parameters: {
    controls: { expanded: true },
  },
};

export default meta;

export const Demo: Story = () => {
  const items = [
    {
      tit: 'we1',
      desc: 'ddd',
    },
    {
      tit: 'we2',
      desc: 'sss',
    },
    {
      tit: 'we3',
      desc: 'ddd',
    },
    {
      tit: 'we4',
      desc: 'sss',
    },
  ];
  return (
    <Carousel>
      {items.map(item => (
        <div>
          {item.tit} {item.desc}
        </div>
      ))}
    </Carousel>
  );
};
