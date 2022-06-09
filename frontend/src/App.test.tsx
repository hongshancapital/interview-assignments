import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

it('No crash', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App />, div);
});

