//
//  ScdtAppDataModel.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/17.
//

import Foundation
import Combine

let TAG = "MainPage"

private func getString(fromDict dict: Dictionary<String, Any> , withName name: String) -> String {
    return dict[name] as? String ?? ""
}

private func getScdtAppIntroduction(fromObj obj: Any)-> [ScdtAppIntroduction] {
    var result = [ScdtAppIntroduction]()
    guard let dict = obj as? Dictionary<String, Any> else {
      return result
    }
    guard let count = dict["resultCount"] as? Int else {
        return result
    }
    if count <= 0 {
        return result
    }
    guard let items = dict["results"] as? [Dictionary<String,Any>] else {
        return result
    }
    let size = items.count
    var i = 0
    while i < size && i < count {
        let itemDict = items[i]
        let artworkUrl100 = getString(fromDict: itemDict, withName: "artworkUrl100")
        let artworkUrl60 = getString(fromDict: itemDict, withName: "artworkUrl60")
        let description = getString(fromDict: itemDict, withName: "description")
        let trackName = getString(fromDict: itemDict, withName: "trackName")
        let trackId = itemDict["trackId"] as? Int ?? 0
        result.append(ScdtAppIntroduction(trackId: String(trackId), name: trackName, introduction: description, imageUrl: artworkUrl60))
        i += 1
    }
    return result
}

protocol ScdtAppIntroductionsLoader {
    func loadIntroductions() throws -> Data
}

class ScdtAppDataModel: ObservableObject {
    @Published private(set) var appIntroductions: [ScdtAppIntroduction] = [ScdtAppIntroduction]()
    
    @Published private(set) var isLoading = false
    @Published private(set) var cantLoadMore = false
    @Published private(set) var loadFailed = false
    
    private let appIntrdouctionsLoader: ScdtAppIntroductionsLoader
    private let mainQueue: DispatchQueue
    
    init(_ appIntrdouctionsLoader: ScdtAppIntroductionsLoader = FakeScdtIntroducationsLoader(), _ mainQueue: DispatchQueue = DispatchQueue.main) {
        self.appIntrdouctionsLoader = appIntrdouctionsLoader
        self.mainQueue = mainQueue
        loadMoreIntroductions()
    }
    
    func loadMoreIntroductions(_ tryAgain: Bool = false) {
        loadFailed = false
        if isLoading {
            return
        }
        isLoading = true
        LogFinal(TAG, "load app introductions from server: retry = %d", tryAgain ? 1 : 0)
        Just("1")
            .subscribe(on: DispatchQueue.global())
            .tryMap { [weak self] fileName -> Any in
                guard let this = self else {
                    throw NSError(domain: "self is nil", code: -1)
                }
                let data = try this.appIntrdouctionsLoader.loadIntroductions()
                return try JSONSerialization.jsonObject(with: data)
        }.map { obj -> [ScdtAppIntroduction] in
            return getScdtAppIntroduction(fromObj: obj)
        }.receive(on: mainQueue)
            .sink(receiveCompletion: {[weak self] completion in
                guard let this = self else {
                    return
                }
                this.isLoading = false
                switch completion {
                case .failure(let error):
                    this.loadFailed = true
                    LogError(TAG, "failed to load app introductions from server: error = %s", error.localizedDescription)
                case .finished:
                    LogFinal(TAG, "success to load app introductions from server")
                }
            }, receiveValue: { [weak self] result in
                if let this = self {
                    if result.isEmpty {
                        this.cantLoadMore = true
                        return
                    }
                    this.appIntroductions.append(contentsOf: result)
                }
            })
    }
}
