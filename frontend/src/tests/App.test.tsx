import React from 'react';
import { render } from '@testing-library/react';
import { KeyframeEffect, Animation } from './types'
import App from '../App';

test('renders learn react link', () => {
  // const { getByText } = render(<App />);
  // const linkElement = getByText(/learn react/i);
  // expect(linkElement).toBeInTheDocument();
  window.KeyframeEffect = KeyframeEffect
  window.Animation = Animation
  const { getByText } = render(<App />);
  if (window) console.log('ooooooooook')
  if (document.timeline) { console.log('1111111') } else { console.log('2222222')}
});
