//
//  HRSearchViewModel.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/11.
//

import Combine
import Foundation

class SearchViewModel: ObservableObject {
    @Published var list = [CommodityModel]()
    @Published var searchString = ""
    
    init() {
        $searchString
            .dropFirst(1)
            .debounce(for: .seconds(0.5), scheduler: RunLoop.main)
            .flatMap({
                URLSession(configuration: .default)
                    .dataTaskPublisher(for: RequestApi.search($0).url)
            })
            .tryMap {
                try? $0.data.map(RequestResponse<[CommodityModel]>.self)
            }
            .map({ $0?.data })
            .replaceError(with: nil)
            .map({ $0 ?? [] })
            .receive(on: RunLoop.main)
            .assign(to: &$list)
    }
}
