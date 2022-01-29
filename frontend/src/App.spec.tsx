import React from 'react';
import { render, screen } from '@testing-library/react';
import { Banner } from './App';

describe('test Banner', () => {
  it('when render Banner', () => {
    const mock = {
      src: 'https://abc.com',
      title: ['a', 'b', 'c'],
      context: ['1', '2', '3'],
      className: 'mock-banner',
    }
    render(
      <Banner {...mock} />
    )
    mock.title.forEach(v => expect(screen.queryByText(v)).toBeInTheDocument());
    mock.context.forEach(v => expect(screen.queryByText(v)).toBeInTheDocument());
    expect(screen.queryByTestId(mock.src)).toBeInTheDocument();
    expect(screen.queryByTestId(mock.src)).toHaveClass(mock.className);
  })
})
