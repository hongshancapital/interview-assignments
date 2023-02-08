/**global变量 */
declare global {
    namespace NodeJS {
        interface Global {
            Exception: ExceptionConstructor
        }
    }
}
