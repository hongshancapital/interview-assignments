import React from 'react';

interface DescriptionItemProps {
    title: string[];
    comment: string[];
    theme?: string;
}

export interface ContentItemProps {
    name: string;
    src: string;
    description: DescriptionItemProps;
}

interface Props {
    slider?: number;
    description?: DescriptionItemProps | null;
}

const Description = ({
    description,
}: Props): JSX.Element => {
    const {title = [], theme, comment = []} = description ?? {};
    return (
        <div className={`description description-${theme ? theme : 'dark'}`}>
            <ul className="title">
                {title.map(item => <li key={item}>{item}</li>)}
            </ul>
            <ul className="comment">
                {comment.map(item => <li key={item}>{item}</li>)}
            </ul>
        </div> 
    );
};

export default Description;