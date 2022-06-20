import React from 'react';
import axios from 'axios';
import {render, fireEvent, waitFor} from '@testing-library/react';
import { App } from './App';

jest.mock('axios');

describe('Short url generate test', () => {
  it('should generate a short url link after click the generate button.', async () => {
    const shortUrl = `http://example.com/short`;
    (axios.post as jest.Mock).mockImplementationOnce((url: string) => {
      if (url.includes('/v1/urls')) {
        return Promise.resolve({
          data: {
            shortUrl: shortUrl,
          },
        });
      }
    });
    const { container } = render(<App />);
    fireEvent.change(container.querySelector('input[type=text]') as HTMLElement, { target: { value: 'Input some long url' }});
    fireEvent.click(container.querySelector('button') as HTMLElement);

    await waitFor(() => expect((container.querySelector('a') as HTMLElement).textContent).toBe(shortUrl));
  });
});
