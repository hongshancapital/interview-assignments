// 号牌生成有多种实现方式，用本接口对外隐藏实现。
interface TokenGenerator {
  generateToken():string;
}

export default TokenGenerator;