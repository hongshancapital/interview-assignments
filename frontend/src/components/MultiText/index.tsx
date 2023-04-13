import classNames from 'classnames';
import React, { useMemo } from 'react';

export interface MultiTextProps {
    texts: string | string[];
    className?: string;
}

const MultiText: React.FC<MultiTextProps> = ({ className, texts }) => {
    const handledTexts = useMemo(() => {
        if (typeof texts === 'string') {
            return [texts];
        }

        return texts;
    }, [texts]);
    const mergedClassName = useMemo(
        () => classNames(['multi_text', className]),
        [className]
    );

    return (
        <div className={mergedClassName}>
            {handledTexts.map((text) => (
                <div key={text} className="multi_text__item">
                    {text}
                </div>
            ))}
        </div>
    );
};

export default MultiText;
