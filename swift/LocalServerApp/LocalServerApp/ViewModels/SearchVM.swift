//
//  SearchVM.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import Combine
import Foundation

class SearchVM: ObservableObject {
    @Published var list = [CommodityModel]()
    @Published var searchKey = ""
    
    init() {
       $searchKey
            .dropFirst(1) // 第一次drop掉
            .debounce(for: .seconds(0.5), scheduler: RunLoop.main)
            .flatMap({
                URLSession(configuration: .default)
                    .dataTaskPublisher(for: Apis.search($0).url)
            })
            .tryMap {
                try? $0.data.map(NetworkReponse<[CommodityModel]>.self)
            }
            .map({ $0?.data })
            .replaceError(with: nil)
            .map({ $0 ?? [] })
            .receive(on: RunLoop.main)
            .assign(to: &$list)
        
    }
    
   
//    也可以用此方法 非Combine
//    func requestSearch(keyword: String?) {
//        var components = URLComponents(string: "http://localhost:1024/search")
//        var querys = [URLQueryItem]()
//        if let keyword = keyword {
//            querys.append(.init(name: "keyword", value: keyword))
//        }
//        components?.queryItems = querys
//
//        guard let url = components?.url else { return }
//
//        print("调几口")
//
//        URLSession.shared
//            .dataTask(with: url) { [weak self] data, _, _ in
//                let model = try? data?.map(NetworkReponse<[CommodityModel]>.self)
//                DispatchQueue.main.async {
//                    self?.list = model?.data ?? []
//                }
//                print(model?.data)
//            }
//            .resume()
//
//    }

}
