import { render } from '@testing-library/react'
import Index from '../src/pages/index'

it('renders homepage unchanged', () => {
  const { container } = render(<Index />)
  expect(container).toMatchSnapshot()
})