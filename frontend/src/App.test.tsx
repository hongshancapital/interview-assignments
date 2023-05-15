import App, { data } from './App'
import { render, getByText, type Matcher } from '@testing-library/react'

describe('App', () => {
  it('should render', () => {
    const { container } = render(<App />)
    const slides = container.querySelectorAll<HTMLElement>('.slide')

    const createTextsMatcher = (dataSource: string[]): Matcher => {
      return content => dataSource.every(i => content.includes(i))
    }

    // Page 1
    expect(getByText(slides[0], createTextsMatcher(data[0].headline))).toBeInTheDocument()
    if (data[0].eyebrow) {
      expect(getByText(slides[0], createTextsMatcher(data[0].eyebrow))).toBeInTheDocument()
    }

    // Page 2
    expect(getByText(slides[1], createTextsMatcher(data[1].headline))).toBeInTheDocument()
    if (data[1].eyebrow) {
      expect(getByText(slides[1], createTextsMatcher(data[1].eyebrow))).toBeInTheDocument()
    }

    // Page 3
    expect(getByText(slides[2], createTextsMatcher(data[2].headline))).toBeInTheDocument()
    if (data[2].eyebrow) {
      expect(getByText(slides[2], createTextsMatcher(data[2].eyebrow))).toBeInTheDocument()
    }
  })
})
