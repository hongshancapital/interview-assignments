import React from 'react';
import { render } from '@testing-library/react';
import Rotation from './Rotation';

describe('<Rotation />', () => {
  it('rotation item all install', () => {
    const testData = [
      {
        title: 'Hello1',
      },
      {
        title: 'Hello2',
      },
      {
        title: 'Hello3',
      },
    ]
    const { getAllByText } = render(<Rotation rotationData={testData}/>)
    const titleElements = getAllByText(/Hello\d/)
    expect(titleElements.length).toEqual(testData.length);
  })
})