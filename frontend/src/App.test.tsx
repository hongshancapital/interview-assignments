import React from 'react';
import { render, cleanup, act } from '@testing-library/react';
import App from './App';
import { initData } from './components/stores/data';
import Carousel from "./components/Carousel";
import { CONTENT_STYLE } from './components/stores/data'

beforeEach(cleanup)

test("add one poster", () => {
  const { posters } = initData;
  const morePoster = {
    posterId: 3,
    bgColor: '#111',
    pic: {
      name: 'iphone.png',
      width: '90px',
      height: '110px'
    },
    contents: [
      {
        text: 'test poster',
        style: { ...CONTENT_STYLE, color: '#fff' }
      }
    ]
  };
  posters.push(morePoster);
  const { container } = render(
    <Carousel time={4000} autoPlay={true} posters={posters} />
  );
  setTimeout(() => {
    expect(container.querySelector(".progress-active")?.textContent).toBe("test poster");
  }, 16000);
});


test('verify that poster and progress are the same length', async () => {
  const { container } = render(<App />);
  const posters = container.getElementsByClassName('Poster');
  const progresses = container.getElementsByClassName('Progress');
  expect(posters.length).toBe(progresses.length);
});


