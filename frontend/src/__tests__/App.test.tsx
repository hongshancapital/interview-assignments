import React from 'react';
import { render, screen, within } from '@testing-library/react';
import { KeyframeEffect, Animation } from './types'
import App from '../App';

test('test', async () => {
  window.KeyframeEffect = KeyframeEffect
  window.Animation = Animation
  const { container } = render(<App />);
  const posters = container.getElementsByClassName('Poster');
  const progresses = container.getElementsByClassName('Progress');
  expect(posters.length).toBe(3);
  expect(progresses.length).toBe(3);
});
