import { render } from '@testing-library/react';
import renderer from 'react-test-renderer'
import { mount } from 'enzyme'
import Carousel from './index';

const props = {
    data: [{
        serviceUrl: '', // 图片url
        title: ['xPhone'], // 标题
        desc: ['Lots to love.Less to spend.','Starting at $399.'],
        color: "#ffffff"
      },
      {
        serviceUrl: '', // 图片url
        title: ['Tablet'], // 标题
        desc: ['Just the right amount of everything.'],
        color: "#000000"
      }], // 数据
    indicatorDots: false, // 是否展示指示点
}

// 数据为空时，不渲染轮播图组件，如果数据不为空，则展示对应长度的轮播图DOM
const setupByRender = () => {
    const wrapper = render(<Carousel {...props} />);
    return {
        props,
        wrapper
    }
}

const setupByMount = () => {
    const wrapper = mount(<Carousel {...props} />);
    return {
        props,
        wrapper
    }
}

it('No indication',()=>{
    const { wrapper } = setupByRender();
    expect(wrapper.getByTestId('indicators')).toBeEmptyDOMElement();
})

it('should render item equal',()=>{
    const { wrapper } = setupByMount();
    wrapper.find('.title').forEach((node:any,index:number)=>{
        expect(node.text()).toBe(wrapper.props().data[index].title?.join('\r\n'))
    })
})

it('renders correctly',()=>{
    const tree = renderer.create(<Carousel {...props} />).toJSON();
    expect(tree).toMatchSnapshot();
})


