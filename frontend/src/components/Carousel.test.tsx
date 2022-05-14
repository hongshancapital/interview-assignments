import React from 'react'
import { render } from '@testing-library/react'
import { Carousel, CarouselItem } from './Carousel'

test('render Carousel by single content', () => {
  const { getByText } = render(
    <Carousel>
      <CarouselItem
        content={{
          title: 'xPhone',
          subtitle: 'Lots to Love. Less to spend.\nStarting at $399.',
          color: '#ffffff',
          background: require('../assets/iphone.png'),
          backgroundColor: '#111111'
        }}
      />
    </Carousel>
  )
  const linkElement = getByText(/xPhone/i)
  expect(linkElement).toBeInTheDocument()
})

test('render Carousel by multi contents', () => {
  const { getByText } = render(
    <Carousel>
      <CarouselItem
        content={{
          title: 'xPhone',
          subtitle: 'Lots to Love. Less to spend.\nStarting at $399.',
          color: '#ffffff',
          background: require('../assets/iphone.png'),
          backgroundColor: '#111111'
        }}
      />
      <CarouselItem
        content={{
          title: 'Tablet',
          subtitle: 'Just the right amount of everything.',
          color: '#000000',
          background: require('../assets/tablet.png'),
          backgroundColor: '#fafafa'
        }}
      />
    </Carousel>
  )
  const linkElement = getByText(/Tablet/i)
  expect(linkElement).toBeInTheDocument()
})

test('render Carousel by myself content', () => {
  const { getByText } = render(
    <Carousel>
      <CarouselItem>
        <span>assets/iphone.png</span>
      </CarouselItem>
      <CarouselItem>
        <span>assets/tablet.png</span>
      </CarouselItem>
    </Carousel>
  )
  const linkElement = getByText(/assets\/tablet/i)
  expect(linkElement).toBeInTheDocument()
})
