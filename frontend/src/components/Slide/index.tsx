import classNames from 'classnames';
import React, { useMemo } from 'react';
import MultiText from '../MultiText';
import './index.scss';

export interface SlideProps {
    image: string;
    title: string | string[];
    description?: string | string[];
    backgroundColor: string;
    useWhiteText?: boolean;
    testID?: string;
}

const Slide: React.FC<SlideProps> = ({
    image,
    title,
    description,
    backgroundColor,
    useWhiteText,
    testID,
}) => {
    const contentClassName = useMemo(
        () =>
            classNames({
                slide__content: true,
                'slide__content--white': !!useWhiteText,
            }),
        [useWhiteText]
    );

    return (
        <div
            data-testid={testID}
            className={'slide'}
            style={{ backgroundColor: `${backgroundColor}` }}
        >
            <div className={contentClassName}>
                <MultiText
                    texts={title}
                    className="slide__content__text text--title"
                />
                {description ? (
                    <MultiText
                        texts={description}
                        className="slide__content__text text--description"
                    />
                ) : null}
            </div>
            <div className="slide__image">
                <div
                    className="slide__image__inner"
                    style={{
                        background: `no-repeat center bottom/contain url(${image})`,
                    }}
                />
                <div className="slide__image__bottom_padding"></div>
            </div>
        </div>
    );
};

export default Slide;
