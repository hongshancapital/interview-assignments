import React from 'react'
import { render } from '@testing-library/react'
import App from './App'
import airPodImg from './assets/airpods.png'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'

test('renders banner images', async () => {
  const app = render(<App />)
  const airPods = await app.findByTestId('airPodsImage')
  expect(airPods.src).toContain(airPodImg)

  const iPhone = await app.findByTestId('iphoneImage')
  expect(iPhone.src).toContain(iphoneImg)

  const tablet = await app.findByTestId('tabletImage')
  expect(tablet.src).toContain(tabletImg)
})
