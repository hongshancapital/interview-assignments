import { render, screen } from "@testing-library/react";
import App from './App';

test('find app in body', () => {
  render(<App />);
  const app = screen.getByTestId("app");
  expect(app).toBeInTheDocument();
});
