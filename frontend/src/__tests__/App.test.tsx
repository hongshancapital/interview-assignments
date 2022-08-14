import React from 'react';
import { render, screen, within, cleanup } from '@testing-library/react';
import { KeyframeEffect, Animation } from './types'
import App from '../App';

beforeEach(cleanup)

test('the number of poster and progress need to be the same', async () => {
  window.KeyframeEffect = KeyframeEffect
  window.Animation = Animation
  const { container } = render(<App />);
  const posters = container.getElementsByClassName('Poster');
  const progresses = container.getElementsByClassName('Progress');
  expect(posters.length).toBe(progresses.length);
});
