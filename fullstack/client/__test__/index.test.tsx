/**
 * https://github.com/vercel/next.js/blob/canary/examples/with-jest
 * https://testing-library.com/docs/react-testing-library/example-intro
 * https://nextjs.org/docs/testing
 * npm jest-fetch-mock
 * 
 */
import { render, screen, waitFor } from '@testing-library/react'
import fetchMock from 'jest-fetch-mock'
import userEvent from '@testing-library/user-event'
import Index from '../src/pages/index'

fetchMock.enableMocks()

describe('Index', () => {
  beforeEach(() => {
    fetchMock.resetMocks()
  })

  it('renders title', () => {
    render(<Index />)

    const title = screen.getByText("短域名服务")
    expect(title).toBeInTheDocument()

    const createTitle = screen.getByText("短域名读取")
    expect(createTitle).toBeInTheDocument()

    const searchTitle = screen.getByText("短域名创建")
    expect(searchTitle).toBeInTheDocument()
  })

  it('Search short url hash been called success', async () => {
    fetchMock.mockResponseOnce(JSON.stringify({
      "data": {
        "long_url": "https://baidu.com",
        "short_key": "UhSDssss",
      },
      "code": 0,
      "msg": "请求成功"
    }))

    render(<Index />)

    await userEvent.click(screen.getByText('Search'))

    setTimeout(() => {
      expect(screen.getByText('UhSDssss')).toBeInTheDocument()
    }, 1000)
  })

  it('Search short url hash been called fail', async () => {
    fetchMock.mockReject(() => Promise.reject({
      code: 400,
    }))

    render(<Index />)

    await userEvent.click(screen.getByText('Search'))

    setTimeout(() => {
      expect(screen.getByText('Network response was not ok')).toBeInTheDocument()
    }, 1000)
  })

  it('Create short url hash been called success', async () => {
    fetchMock.mockResponseOnce(JSON.stringify({
      "data":[
        {
          "long_url": "https://baidu.com",
          "short_key": "UhSDRTeO",
        }
      ],
      "code": 0,
      "msg": "请求成功"
    }))

    render(<Index />)

    await userEvent.click(screen.getByText('Create'))
  
    setTimeout(() => {
      expect(screen.getByText('create success')).toBeInTheDocument()
    }, 1000)
  })

  it('Create short url hash been called fail', async () => {
    fetchMock.mockReject(() => Promise.reject({
      code: 400,
    }))

    render(<Index />)

    await userEvent.click(screen.getByText('Search'))

    setTimeout(() => {
      expect(screen.getByText('Network response was not ok')).toBeInTheDocument()
    }, 1000)
  })
})
