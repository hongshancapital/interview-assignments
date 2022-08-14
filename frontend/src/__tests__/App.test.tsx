import React from 'react';
import { render, screen, within, cleanup } from '@testing-library/react';
import { KeyframeEffect, Animation } from './types'
import { initialData } from '../stores/AppContext'
import App from '../App';

beforeAll(() => {
  window.KeyframeEffect = KeyframeEffect
  window.Animation = Animation
})

beforeEach(cleanup)

test('the number of posters and progresses need to be the same', async () => {
  const { container } = render(<App />);
  const posters = container.getElementsByClassName('Poster');
  const progresses = container.getElementsByClassName('Progress');
  expect(posters.length).toBe(progresses.length);
});

test('check poster info', async () => {
  const { container } = render(<App />);
  const posters = container.getElementsByClassName('Poster');
  for(let i = 0; i < posters.length; i++) {
    const pds = posters[i].getElementsByClassName('poster-des');
    expect(pds[0].textContent).toBe(initialData.posters[i].descs[0].text)
  }
})
