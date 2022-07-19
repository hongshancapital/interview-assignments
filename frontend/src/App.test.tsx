import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

describe("App Smoke Test", () => {
  test('App renders without crashing', () => {
    render(<App />);

    // screen.debug();
  });
});