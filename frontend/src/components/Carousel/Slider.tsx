import React, {useEffect} from "react"
import useInterval from "./hooks/useInterval"
import {TriggerEnum} from "./enums/triggerEnum"

export interface SliderProps {
    items: Array<any>;
    activeIndex?: number;
    onChange?: (index: number) => any;
    interval: number;
    trigger: string;
    autoplay: boolean;
}

const Slider: React.FC<SliderProps> = (props: SliderProps) => {
    const {
        interval,
        trigger,
        items = [],
        activeIndex = 0,
        autoplay,
        onChange = (name: any, index: number) => {}
    } = props

    const [percent, finished, startInterval] = useInterval(interval, autoplay)

    const onChangeWrapper = (name: any, index: number) => {
        if (activeIndex === index) {
            return
        }
        onChange(name, index)
        if (autoplay) {
            setTimeout(startInterval, 0)
        }
    }

    useEffect(() => {
        if (finished) {
            const newIndex = (activeIndex >= items.length - 1) ? 0 : (activeIndex + 1)
            onChangeWrapper(items[newIndex]?.props?.name, newIndex)
        }
    }, [finished])

    return <ul className={'z-carousel__slider'}>
        {
            items.map((item: any, index: number) => {
                const isActiveIndex = activeIndex === index
                const name = item.props.name
                return <li
                    key={name}
                    className={`${isActiveIndex ? 'is-active' : ''}`}
                    onMouseEnter={() => (trigger === TriggerEnum.Hover) && onChangeWrapper(name, index)}
                    onClick={() => (trigger === TriggerEnum.Click) && onChangeWrapper(name, index)}
                >
                    {
                        <div
                            style={{
                                width: (autoplay && isActiveIndex) ? `${100 * percent}%` : '100%',
                                opacity: isActiveIndex ? 1 : 0
                            }}
                        />
                    }
                </li>
            })
        }
    </ul>
}

export default Slider