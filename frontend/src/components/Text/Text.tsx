import React, { FC } from "react";

import "./Text.css";

export type TextVariant = "title" | "content";

export interface TextProps {
    text: string;
    variant: TextVariant;
    className?: string;
}

const CLASS_NAME_LOOKUP: Record<TextVariant, string> = {
    title: "text--title",
    content: "text--content",
};

const Text: FC<TextProps> = ({ text, variant, className }) => {
    return (
        <div className={`${CLASS_NAME_LOOKUP[variant]} ${className}`}>
            {text}
        </div>
    );
};

export default Text;
