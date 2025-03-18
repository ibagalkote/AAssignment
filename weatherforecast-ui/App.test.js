import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Please enter ZIP code text', () => {
  render(<App />);
  const linkElement = screen.getByText(/Please enter ZIP code/i);
  expect(linkElement).toBeInTheDocument();
});
