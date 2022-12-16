interface PluginContext {
    setActiveIndex: React.Dispatch<React.SetStateAction<number>>;
    setPaused: React.Dispatch<React.SetStateAction<boolean>>;
    setStyle: React.Dispatch<React.SetStateAction<React.CSSProperties>>;
    getActiveIndex: () => number;
    getLength: () => number;
}

type BaseEleProps = React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement>

export type {
  PluginContext,
  BaseEleProps
};