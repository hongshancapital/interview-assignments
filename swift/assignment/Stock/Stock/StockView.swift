import SwiftUI
import UIPlus

public struct StockView: View {

    public init(networking: Networking) {
        _networking = networking
    }
    
    let _networking: Networking

    public var body: some View {
        StockList(data: $data, onKeywordChanged: search)
    }

    @State
    var data: [StockRecordGroup] = []

    func search(withKeyword keyword: String) {
        let url = URL(string: "https://host/path?keyword="+keyword)!
        let urlRequest = URLRequest(url: url)
        _networking.request(urlRequest) {
            data = $0 ?? []
        }
    }
}
