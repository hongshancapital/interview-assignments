import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import ItemRenderer from './index'

test('ItemRenderer test', () => {
  const title = 'Tablet'
  render(
    <ItemRenderer
      title={title}
      theme="Lighten"
      descriptions={['']}
      image="https://img.alicdn.com/imgextra/i1/O1CN01vgHRN61bd5usKPMkZ_!!6000000003487-0-tps-290-290.jpg"
    />
  )
  expect(screen.getByText(title)).toBeInTheDocument();
})