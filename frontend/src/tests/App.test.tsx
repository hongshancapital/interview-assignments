import React from 'react';
import { render, screen, within } from '@testing-library/react';
import { KeyframeEffect, Animation } from './types'
import App from '../App';

test('how to test ?', async () => {
  window.KeyframeEffect = KeyframeEffect
  window.Animation = Animation
  render(<App />);
});
