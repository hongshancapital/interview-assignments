import SwiftUI
import UIPlus
import Stock

@main
struct StockApp: App {
    var body: some Scene {
        WindowGroup {
            StockView(networking: self)
        }
    }
}

extension StockApp: Networking {
    
    func request<T>(_ urlRequest: URLRequest, response: @escaping (T?) -> Void) where T : Decodable {
        if urlRequest.url?.query == "keyword=Dyson" {
            do {
                let path = Bundle.main.path(forResource: "stock", ofType: "json")!
                let url = URL(fileURLWithPath: path)
                let data = try Data(contentsOf: url)
                let t = try JSONDecoder().decode(T.self, from: data)
                response(t)
            } catch {
                response(nil)
            }
        } else {
            response(nil)
        }
    }
}
