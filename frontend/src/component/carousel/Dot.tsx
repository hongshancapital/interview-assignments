import React from 'react';

interface DotProps {
	isActive: boolean;
	duration: number;
}

export const Dot: React.FC<DotProps> = ({
	isActive,
	duration
}) => {
	return (
		<div className='dot-content'>
			<span className={`dot ${isActive ? 'active' : ''}`}
				style={{animationDuration: `${duration}s`}}
			></span>
		</div>
	);
}