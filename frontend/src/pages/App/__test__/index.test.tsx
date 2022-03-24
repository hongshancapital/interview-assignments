import React from 'react'
import { render } from '@testing-library/react'
import App from '../index'

describe('Test App Page', () => {
  test('App Page Render Snapshot', () => {
    const { container } = render(<App />)
    expect(container).toMatchSnapshot()
  })
})
