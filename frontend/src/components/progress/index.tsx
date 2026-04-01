import React from "react";
import classNames from 'classnames';
import './style/index.css';
import {IProgress} from './interface';

export default function Progress(props: IProgress) {
  const {
    active,
    autoplay = true,
    onClick,
    className = ''
  }: IProgress = props;

  const classes = classNames(
    'progress',
    className
  )

  return (
    <div className={classes}>
      <button className="progress-btn" onClick={onClick}>
          <span className={
            classNames(
              'progress-span',
              active
              ? autoplay
                ? 'active'
                  : 'noAutoplayActive'
              : ''
            )}></span>
      </button>
    </div>
  )
}
