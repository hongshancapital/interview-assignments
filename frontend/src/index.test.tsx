import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

jest.mock('./App', () => () => <div>App</div>);

jest.mock('react-dom/client', () => ({
  createRoot: jest.fn().mockName('createRoot'),
}));

const mockedRootDiv = document.createElement('div');
const mockedRender = jest.fn().mockName('render');

describe('index', () => {
  beforeEach(() => {
    jest
      .mocked(ReactDOM.createRoot)
      .mockReturnValue({ render: mockedRender } as unknown as ReactDOM.Root);
    jest.spyOn(document, 'getElementById').mockReturnValue(mockedRootDiv);

    require('./index');
  });

  it('renders App', () => {
    expect(jest.mocked(ReactDOM.createRoot)).toHaveBeenCalledWith(
      mockedRootDiv
    );
    expect(mockedRender).toHaveBeenCalledWith(
      <React.StrictMode>
        <App />
      </React.StrictMode>
    );
  });
});
