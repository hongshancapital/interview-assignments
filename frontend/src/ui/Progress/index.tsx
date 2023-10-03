import styles from './styles.module.css'

interface ProgressProps {
    rate: string | number;
    backgroundColor?: string;
    barColor?: string;
    children?: React.ReactNode;
    duration?: number;
}

const Progress = ({
    rate = '50%',
    children,
    backgroundColor = '#a0a0a0',
    barColor = '#fff',
    duration = 2,
}: ProgressProps) => {
    return (
        <div
            className={styles['cell-progress']}
            style={{ backgroundColor }}
        >
            {
                !!rate ?
                    (
                        <div
                            className={styles['bar']}
                            style={{ width: rate, backgroundColor: barColor, animation: `${duration}s linear ${styles['slide-in']}` }}
                        ></div>
                    )
                    : null
            }
            {children}
        </div>
    )
}

export default Progress