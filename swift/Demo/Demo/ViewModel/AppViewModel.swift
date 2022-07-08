//
//  AppViewModel.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/5.
//

import Foundation
import Combine

class AppViewModel: ObservableObject {
    
    static let shared = AppViewModel()
    
    @Published var appInfos = [AppInfo]()
    @Published var hasMore = true
    
    private var likeInfo: [String: Bool]
    private var currentPage = 0
    private let pageSize = 10

    private var cancellable: AnyCancellable?
    
    func requestAppinfos(_ isHeader: Bool) {
        if isHeader {
            currentPage = 0
            hasMore = true
        }
        cancellable = URLSession.shared.dataTaskPublisher(for: requestURL())
            .tryMap { $0.data }
            .decode(type: AppInfoData.self, decoder: JSONDecoder())
            .tryMap({ apiResponse in
                return apiResponse
                    .results
                    .suffix(from: self.pageSize*self.currentPage)
            })
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { completion in
                switch completion {
                case .finished:
                    break
                case .failure(let error):
                    print(error)
                }
            }, receiveValue: { infos in
                self.currentPage += 1
                if isHeader {
                    self.appInfos = [AppInfo](infos)
                } else {
                    self.appInfos.append(contentsOf: infos)
                }
                for index in 0..<self.appInfos.count {
                    var tmpInfo = self.appInfos[index]
                    tmpInfo.isLiked = self.queryLikeStatus(with: tmpInfo.id)
                    self.appInfos[index] = tmpInfo
                }
                if infos.count < self.pageSize {
                    self.hasMore = false
                }
            })
    }
    
    private func requestURL() -> URL {
        let numbers = pageSize*(currentPage + 1)
        let urlString = "https://itunes.apple.com/search?entity=software&limit=\(numbers)&term=chat"
        return URL(string: urlString)!
    }
    
    init() {
        if let tmpLikeInfo = UserDefaults.standard.value(forKey: "appLikeInfo") as? [String: Bool] {
            likeInfo = tmpLikeInfo
        } else {
            likeInfo = [:]
        }
    }
    
    func updateLikeStatus(with appId: Int, isLike: Bool) {
        likeInfo.updateValue(isLike, forKey: "\(appId)")
        UserDefaults.standard.set(likeInfo, forKey: "appLikeInfo")
        UserDefaults.standard.synchronize()
    }
    
    func queryLikeStatus(with appId: Int) -> Bool {
        let result = likeInfo["\(appId)"] ?? false
        return result
    }
}
