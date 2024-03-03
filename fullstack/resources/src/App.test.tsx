import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import { GetOriginLink, GetShortLink } from '@components';

describe("Smoke Test", () => {
  beforeAll(() => {
    global.matchMedia = global.matchMedia || function () {
      return {
        addListener: jest.fn(),
        removeListener: jest.fn(),
      };
    };
  });

  test('App renders without crashing', () => {
    render(<App />);
  });

  test('GetOriginLink renders without crashing', () => {
    render(<GetOriginLink />);
  });

  test('GetShortLink renders without crashing', () => {
    render(<GetShortLink />);
  });
});
