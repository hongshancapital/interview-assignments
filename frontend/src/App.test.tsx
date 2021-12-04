import React from 'react';
import {render, cleanup} from '@testing-library/react'
import App from './App'

afterEach(cleanup)

it('take a snapshot: App', () => {
  const { asFragment } = render(<App />);

  expect(asFragment()).toMatchSnapshot()
})