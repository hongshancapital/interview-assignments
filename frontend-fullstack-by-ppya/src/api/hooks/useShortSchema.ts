import shortid from 'shortid';

/**
 * 生成短链schema
 */
const useShortSchema = () => {
   const schema = shortid.generate();
   return `https://ppya.cn/t/${schema}`;
};

export default useShortSchema;
