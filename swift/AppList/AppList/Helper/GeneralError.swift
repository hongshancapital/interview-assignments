struct GeneralError: Error {
    let code: Int
    let message: String
}

extension GeneralError {
    static let loadData = GeneralError(code: 999, message: "Load data error")
    static let fetchData = GeneralError(code: 1000, message: "fetch data error")
}
