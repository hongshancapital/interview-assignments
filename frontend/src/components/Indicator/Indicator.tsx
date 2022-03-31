import React from "react";
import "./Indicator.css";

interface Progress {
    val: number,
    id: number
}
interface IndicatorProps {
    index?: number;
    delay?: number;
    indicatorIsShow?: boolean;
}
const Indicator: React.FC<IndicatorProps> = (props: IndicatorProps) => {
    const { index = 0, delay = 3000, indicatorIsShow = true } = props;
    return (
        <>
            {
                indicatorIsShow ? <div className="progress">
                    {
                        [
                            { val: 0, id: 0 },
                            { val: 1, id: 1 },
                            { val: 2, id: 2 }
                        ].map((value: Progress) => {
                            return <div
                                className="long"
                                key={value.id}
                            >
                                <div className="short"
                                    style={{
                                        width: index === value.val ? '100%' : 0,
                                        transition: index === value.val ? `width ease ${delay}ms` : 'none',
                                    }} />
                            </div>
                        })
                    }
                </div> : null
            }
        </>
    )
}

export default Indicator;
