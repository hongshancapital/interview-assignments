import React from 'react';
import { render } from '@testing-library/react';
import tablet from '../assets/tablet.png';
import iphone from '../assets/iphone.png';
import Switch from '../components/Switch';

const configs = [
  {
    title: 'xPhone',
    content:'Lots to love. Less to spend. Starting at $399.',
    style: 'black' as const,
    logo: iphone
  },
  {
    title: 'Tablet',
    content:'Just the right amount of everything',
    style: 'white'as const,
    logo: tablet
  }
];

test('Switch', () => {
  const { getByText, getByAltText } = render(<Switch pages={configs} />);
  configs.forEach(config => {
    const title = getByText(config.title);
    expect(title).toBeInTheDocument();

    if (config.content) {
      const content = getByText(config.content);
      expect(content).toBeInTheDocument();
    }
    const content = getByAltText(config.title);
    expect(content).toBeInTheDocument();
  })
});
