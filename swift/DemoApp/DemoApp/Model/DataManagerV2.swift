//
//  DataManagerV2.swift
//  DemoApp
//
//  Created by liang on 2022/5/26.
//

import Foundation
import Combine

class DataManagerV2: ObservableObject {
    @Published var appList = [AppModel]()
    @Published var hasMore = true
    private var pageToFetch = 0
    private let perPage = 20

    private var cancellable: AnyCancellable?
    
    func fetch(_ isRefresh: Bool) {
        if isRefresh {
            pageToFetch = 0
            hasMore = true
        }
        cancellable = URLSession.shared.dataTaskPublisher(for: urlToRequest())
            .tryMap { $0.data }
            .decode(type: ApiResponse.self, decoder: JSONDecoder())
            .tryMap({ apiResponse in
                return apiResponse.results.suffix(from: self.perPage*self.pageToFetch)
            })
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { completion in
                print(".sink(): received the completion", String(describing: completion))
                switch completion {
                case .finished:
                    break
                case .failure(let anError):
                    debugPrint("received error: ", anError)
                }
            }, receiveValue: { list in
                self.pageToFetch += 1
                if isRefresh {
                    self.appList = [AppModel].init(list)
                } else {
                    self.appList.append(contentsOf: list)
                }
                if list.count < self.perPage {
                    self.hasMore = false
                }
            })
    }
    
    private func urlToRequest() -> URL {
        let numberToRequest = min(perPage*(pageToFetch + 1), 50) 
        let urlString = """
        https://itunes.apple.com/search?\
        entity=software&limit=\(numberToRequest)&term=chat
        """
        return URL(string: urlString)!
    }
}
