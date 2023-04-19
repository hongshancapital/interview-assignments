import React, {ReactNode, useCallback, useEffect, useMemo, useRef, useState} from 'react';
import './Carousel.css';

type TextType = 'title' | 'desc' | 'custom';

interface TextInfo {
    /**
     * 文本类型
     */
    type: TextType,

    /**
     * 文本内容
     * @desc 当使用自定义渲染时该值可选
     */
    text?: string;

    /**
     * 自定义渲染函数
     * @desc 使用非标文本时可使用自定义模式
     */
    render?: () => ReactNode;
}

export interface Slide {
    /**
     * 唯一标识符
     * @desc 用于受控时的唯一标识
     */
    id?: string | number;

    /**
     * 文本信息集合
     */
    textInfos?: TextInfo[];

    /**
     * 背景图片
     */
    backgroundImage: string;

    /**
     * 拓展样式
     */
    extStyles?: Record<string, string>;
}

interface Props {
    /**
     * 幻灯片对象集合
     * @type Slide
     */
    slides: Slide[];

    /**
     * 默认超时时间
     * @unit ms
     */
    duration?: number;

    /**
     * 受控模式下激活的幻灯片ID
     * @type Slide.id
     * @desc 使用这一功能必须使slide含有‘id’属性
     */
    activeId?: Slide['id'];

    /**
     * 点击marker
     * @param {Slide.id} id
     * @param {number} idx
     * @return void
     */
    onClickSlideMarker?: (id: Slide['id'], idx: number) => void;
}

const DEFAULT_DURATION = 3000;

export default function Carousel(props: Props) {
    const intervalRef = useRef<NodeJS.Timer>();
    const [activeIdx, setActiveIdx] = useState(0);
    const duration = useMemo(() => {
        return props.duration || DEFAULT_DURATION;
    }, [props.duration]);

    /**
     * 设置定时器定时更新非受控组件活动幻灯片
     */
    useEffect(() => {
        intervalRef.current = setInterval(() => {
            if (props.activeId === undefined) {
                setActiveIdx((activeIdx + 1) % props.slides.length);
            }
        }, duration);

        return () => {
            clearInterval(intervalRef.current);
        };
    }, [duration, activeIdx]);

    /**
     * cleanup
     */
    useEffect(() => {
        return () => {
            clearInterval(intervalRef.current);
        };
    }, []);

    /**
     * 点击标记时更新非受控组件或发送通知给控制者
     */
    const onMarkerClick = useCallback((slide: Slide, idx: number) => {
        if (props.activeId === undefined) {
            setActiveIdx(idx);
        }

        props.onClickSlideMarker?.(slide.id, idx);
    }, [props.onClickSlideMarker]);

    /**
     * 渲染文本信息
     * @param {TextInfo} textInfo
     * @return {ReactNode}
     */
    const textInfoRender = (textInfo: TextInfo) => {
        const { type, text, render } = textInfo;
        let el: ReactNode = <></>;

        switch (type) {
            case 'title':
                el = <h1>{text}</h1>;
                break;
            case 'desc':
                el = <p>{text}</p>;
                break;
            case 'custom':
                if (render) {
                    el = render();
                }
                break;
            default:
                el = <h1>{text}</h1>;
                break;
        }

        return <div key={text}>{el}</div>;
    };

    /**
     * 获取指示器状态
     * @param {Slide} slide
     * @param {number} idx
     * @return {number | string}
     */
    const getMarkerShowStatus = (slide: Slide, idx: number) => {
        if (props.activeId !== undefined) {
            return slide.id === props.activeId;
        } else {
            return idx === activeIdx;
        }
    }

    return (
        <div>
            <div
                className="slide-container"
                data-testid="slide-container"
                style={{
                    willChange: 'auto',
                    transform: `translateX(${-100 * activeIdx}vw)`,
                }}
            >
                {props.slides.map((slide) => (
                    <div
                        key={slide.id}
                        className="slide-item"
                        style={{
                            ...slide.extStyles,
                        }}>
                        <div
                            className="slide-item-content"
                            style={{
                                backgroundImage: `url('${slide.backgroundImage}')`,
                            }}
                        >
                            <div className="text-content">
                                {slide.textInfos?.map(textInfoRender)}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="marker-container" data-testid="marker-container">
                {props.slides.map((slide, idx) => (
                    <div
                        className="marker-item"
                        key={slide.id}
                        onClick={() => onMarkerClick(slide, idx)}
                    >
                        <div className='marker'>
                            <span
                              className={`marker-lighter ${getMarkerShowStatus(slide, idx) ? 'show' : ''}`}
                              style={{ animationDuration: `${duration}ms` }}
                            />
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
