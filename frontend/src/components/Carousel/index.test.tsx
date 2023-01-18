
import { render ,waitFor} from '@testing-library/react';
import { useState } from 'react';
import Carousel from './index';
beforeEach(() => {
    jest.useFakeTimers()
})
test('Carousel props value', () => {
    let value=1
    const { container } = render(
        <Carousel value={value} onChange={page =>{
            value = page
        }} autoplay>
            <div>first page</div>
            <div>second page</div>
        </Carousel>
    );
    const carouselContent = container.querySelector('.carousel-content')
    expect(carouselContent).toBeInTheDocument();
    expect(carouselContent?.getAttribute('style')).toBe("left: calc(-1 * 100vw);")
});

test('Carousel props onChange & duration', async () => {
    const Component:ReactFC<{call:any}>=(props)=>{
        const [value, valueSet]=useState(0)
        const onChange =(value:number)=>{
            valueSet(value)
            props.call(value)
        }
        return <Carousel value={value} onChange={onChange} autoplay duration={500}>
            <div>first page</div>
            <div>second page</div>
            <div>third page</div>
        </Carousel>
    }
    const call=jest.fn()
    const { container } = render(<Component call={call}/>)
    const carouselContent = container.querySelector('.carousel-content')
    expect(carouselContent).toBeInTheDocument();
    expect(carouselContent?.getAttribute('style')).toBe("left: calc(0 * 100vw);")
    await waitFor(()=>{
        expect(carouselContent?.getAttribute('style')).toBe("left: calc(-1 * 100vw);")
        expect(call).toBeCalledTimes(3)
    },{
        timeout: 100000,
        interval: 100
    })
});

