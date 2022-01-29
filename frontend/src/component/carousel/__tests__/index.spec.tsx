import React from 'react';
import { screen, render, cleanup, act } from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import Carousel from '../index'

const sleep = (t: number) => new Promise<string>(resolve => setTimeout(() => resolve('done'), t))

describe('test Carousel', () => {
  afterEach(cleanup)

  it('when render carousel', () => {
    const list = ['a', 'b', 'c']
    render(<Carousel>
      {list.map(v => <Carousel.Item key={v}>{v}</Carousel.Item>)}
    </Carousel>)
    list.forEach(v => expect(screen.getByText(v)).toBeInTheDocument())
  })

  it('when unmount carousel listener removed', () => {
    const wrapper = render(<Carousel>
      <Carousel.Item>Hello world</Carousel.Item>
    </Carousel>)
    const removeMock = jest.spyOn(window, 'removeEventListener')
    wrapper.unmount()
    expect(removeMock).toHaveBeenCalled()
  })

  it('when async carousel render', () => {
    function App() {
      const [list, setList] = React.useState<string[]>(['1', '2', '3']);
      const pushList = () => setList([...list, Date.now() + ''])
      const removeList = () => setList(list.filter((v, i) => i !== 0))
      return(
        <>
          <Carousel>
            {list.map(v => <Carousel.Item key={v}><div data-testid="carousel-item">{v}</div></Carousel.Item>)}
          </Carousel>
          <button data-testid="add-btn" onClick={pushList}>add</button>
          <button data-testid="remove-btn" onClick={removeList}>add</button>
        </>
      )
    }
    render(<App />)
    expect(screen.getAllByTestId('carousel-item')).toHaveLength(3);
    userEvent.click(screen.getByTestId('add-btn'))
    userEvent.click(screen.getByTestId('add-btn'))

    expect(screen.getAllByTestId('carousel-item')).toHaveLength(5)
    userEvent.click(screen.getByTestId('remove-btn'))
    expect(screen.getAllByTestId('carousel-item')).toHaveLength(4)
  })

  it('when render carousel onChange has been call', async () => {
    const onChangeMock = jest.fn()
    const wrapper = render(<Carousel onChange={onChangeMock} duration={1000}>
      <Carousel.Item>a</Carousel.Item>
      <Carousel.Item>b</Carousel.Item>
    </Carousel>)
    act(() => {
      sleep(1000).then(() => {
        wrapper.unmount();
        expect(onChangeMock).toHaveBeenCalledTimes(1);
      });
    })
  })

  it('when render vertical & dotPosition=left should carousel class has carousel-vertical & dot className has carousel-dot-left', () => {
    render(
      <Carousel vertical style={{ height: '100vh' }} dotPosition="left">
        <Carousel.Item>Hello vertical</Carousel.Item>
      </Carousel>
    )
    expect(screen.getByTestId('carousel')).toHaveClass('carousel-vertical');
    expect(screen.getByTestId('dot')).toHaveClass('carousel-dot-left');
  })
})
