import React from 'react'
import { render } from '@testing-library/react';
import { data } from '../../resource'
import Demo from './Demo'

describe('Demo component', () => {

  const [, testData] = data

  it('ui', () => {
    const { getByText, getByAltText } = render(<Demo {...testData } />)

    expect(getByText(testData.title)).toBeInTheDocument()
    expect(getByText(testData.subTitle!)).toBeInTheDocument()
    expect(getByAltText(testData.title)).toBeInTheDocument()
  })

})