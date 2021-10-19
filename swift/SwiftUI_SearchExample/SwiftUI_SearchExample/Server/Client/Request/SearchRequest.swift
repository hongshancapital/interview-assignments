import Foundation

class SearchRequest: NSObject {
    
    class func dataTaskPublisher(keyword: String) -> URLSession.DataTaskPublisher {
        return Request(method: "GET",
                       headers: [:],
                       url: kApiHost + kApiSearch,
                       query: ["keyword": keyword],
                       body: [:])
            .dataTaskPublisher()
    }
    
}
