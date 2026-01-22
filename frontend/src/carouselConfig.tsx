import { MutableRefObject } from "react";
import { CarouseApi, CarousePageConfigProps } from "./components/carousel"
const airIcon = require('./assets/airpods.png');
const tablet = require('./assets/tablet.png');
const iphone = require('./assets/iphone.png');

/**
 * 获取跑马灯配置
 * @param CarouselRef 
 * @returns 
 */
export const getCarouPageConfig = (CarouselRef: MutableRefObject<CarouseApi | undefined>): CarousePageConfigProps => [
    {
      type: 'Image',
      props: {
        image: iphone,
        title: ['xPhone'],
        description: [
          'Lots to love. Less to spend.',
          'Starting at $399.'
        ],
        style: {
          backgroundColor: 'rgb(17, 17, 17)',
          backgroundSize: '50%',
          backgroundPosition: '50% 70%',
          color: 'white'
        }
      }

    },
    {
      type: 'Image',
      props: {
        image: tablet,
        title: ['Tablet'],
        description: [
          'Just the right amount of everying',
        ],
        style: {
          backgroundColor: 'rgb(250, 250, 250)',
          backgroundSize: '90%',
          backgroundPosition: '50% 75%',
          color: 'black'
        }
      }
    },
    {
      type: 'Image',
      props: {
        image: airIcon,
        title: [
          'Buy a Tablet or xPhone for college',
          'Get arPods.'
        ],
        style: {
          backgroundColor: 'rgb(241, 241, 243)',
          backgroundSize: '130%',
          backgroundPosition: '50% 75%',
          color: 'black'
        }

      }
    },
    {
      type: 'Content',
      children: <div
        style={{
          textAlign: 'center',
          width: '100%',
          height: '100%',
          padding: '10% 0',

        }}
      >
        <div>跑马灯工具栏</div>
        <div
          style={{
            display: 'grid',
            position: 'absolute',
            paddingTop: 50,
            gridAutoFlow: 'column',
            left: '50%',
            transform: 'translateX(-50%)',
            textAlign: 'center',
            gridTemplateColumns: '100px 100px 100px 100px 100px',
            gridColumnGap: '10px',
          }}
        >
          <button onClick={() => CarouselRef.current?.autoPlay(true)}>继续</button>
          <button onClick={() => CarouselRef.current?.autoPlay(false)}>暂停</button>
          <button onClick={() => {
            CarouselRef.current?.goTo(0);
            CarouselRef.current?.autoPlay(5);
          }}>五秒时长</button>
          <button onClick={() => CarouselRef.current?.prev()}>上一页</button>
          <button onClick={() => CarouselRef.current?.next()}>下一页</button>
        </div>
      </div>
    },
    {
      type: 'Content',
      // @ts-ignore
      children: () => '错误组件',
    }


  ]