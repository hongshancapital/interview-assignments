import React from 'react';
import { render, screen } from '@testing-library/react';

import { Product } from '../Product';

describe('Product Component', () => {
  const title = 'mock title';
  const subtitle = 'mock subtitle';

  it('Should render the title and subtitle', () => {
    render(<Product title={title} subtitle={subtitle} image="" />);

    expect(screen.getByText(title)).toBeVisible();
    expect(screen.getByText(subtitle)).toBeVisible();
  });
});
