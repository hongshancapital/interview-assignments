import SwiftUI
import UIPlus
import Stock

@main
struct MainApp: App {
    
    var body: some Scene {
        WindowGroup {
            StockView(networking: self)
        }
    }
    
    // TODO: 通常定制一个 URLSession 会更符合我们的需求
    let _urlSession = URLSession.shared
}

extension MainApp: Networking {
    
    func request<T: Decodable>(_ urlRequest: URLRequest, response: @escaping (T?) -> Void) {
        _urlSession.dataTask(with: urlRequest) { data, _, error in
            if let error = error as? URLError {
                handle(error)
                response(nil)
                return
            }
            if let data = data {
                do {
                    // TODO: 真实项目中，我会自定义一个 Decoder！
                    let t = try JSONDecoder().decode(T.self, from: data)
                    response(t)
                } catch {
                    handle(error as! DecodingError)
                    response(nil)
                }
            }
        }
    }
    
    func handle(_ error: URLError) {
        // TODO: 通常我们会用一些手段让用户知道出错了
    }
    
    func handle(_ error: DecodingError) {
        // TODO: 通常我们会用一些手段让用户知道出错了
    }
}
