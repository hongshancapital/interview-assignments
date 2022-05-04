/* eslint-disable import/export */
import { render as _render } from '@testing-library/react'

const customRender = (ui: React.ReactElement, options = {}) =>
  _render(ui, {
    // wrap provider(s) here if needed
    wrapper: ({ children }) => children,
    ...options,
  })

export * from '@testing-library/react'
export { default as userEvent } from '@testing-library/user-event'
// override render export
export { customRender as render }
