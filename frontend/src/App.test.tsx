import App from './App'
import { render, getByText } from '@testing-library/react'

describe('App', () => {
  it('should render', () => {
    const { container } = render(<App />)
    const slides = container.querySelectorAll<HTMLElement>('.slide')

    // Page 1
    expect(getByText(slides[0], 'xPhone')).toBeInTheDocument()
    expect(
      getByText(
        slides[0],
        content =>
          content.includes('Lots to love. Less to spend.') && content.includes('Starting at $399.')
      )
    ).toBeInTheDocument()

    // Page 2
    expect(getByText(slides[1], 'Tablet')).toBeInTheDocument()
    expect(getByText(slides[1], 'Just the right amount of everything.')).toBeInTheDocument()

    // Page 3
    expect(
      getByText(
        slides[2],
        content =>
          content.includes('Buy a Tablet or xPhone for college.') &&
          content.includes('Get airPods.')
      )
    ).toBeInTheDocument()
  })
})
