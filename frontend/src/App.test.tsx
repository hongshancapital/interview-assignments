import { render } from '@testing-library/react'
import App from './App'

test('测试App', () => {
  const { getByText } = render(<App />)
  const testList = [/XPhone/, /Tablet/, /Get airPods/]
  testList.forEach(line => {
    const linkElement = getByText(line)
    expect(linkElement).toBeInTheDocument()
  })
})