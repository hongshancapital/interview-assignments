//
//  AppInfosProvider.swift
//  Demo
//
//  Created by milomiao on 2022/6/22.
//

import Foundation
import SwiftUI
import Combine
import OSLog

let logger = Logger(subsystem: "com.milo.demo", category: "feed")

class AppDataProvider : ObservableObject {

    @Published var feed = FeedProvider()
    var store = StoreLike()
    
    static var store = StoreLike()
    var cancellable: Cancellable?


    init() {
        cancellable = NotificationCenter.default
            .publisher(for: UIScene.willDeactivateNotification)
            .sink { [weak self] notification in
                guard let self = self else { return }
                self.store.save()
            }
    }
    
    deinit {
        if let cancellable = cancellable {
            cancellable.cancel()
        }
    }
    
    static var shared = {
        return AppDataProvider()
    }()
}

class FeedProvider: ObservableObject {
    @Published var infos: [AppItemInfo]?
    @Published var hasMore = true
    
    @Published var from : Int = 0
    var step : Int = 10
    
    var cancellable : Cancellable?
    @Published var moreDescription: String = ""
    
    init() {
        cancellable = $from
            .map({ index in
                index < 50
            })
            .removeDuplicates()
            .sink(receiveValue: { [weak self] more in
                guard let self = self else { return }
                self.hasMore = more
                self.moreDescription = more ? "Loading" : "No more data"
                logger.debug("loading feed data:\(more)")
            })
    }
    
    deinit {
        if let cancellable = cancellable {
            cancellable.cancel()
        }
    }
    
    static func fetch(from: Int, length: Int) async -> [AppItemInfo]? {
        return await FeedRequest.loadData(from: from, length: length)
    }
    
    func updatePostion() {
        from += step
    }
    
    func resetPosition() {
        from = 0
    }
}


protocol FeedRequestable {
    static func loadData(from: Int, length: Int) async -> [AppItemInfo]?
}

class FeedRequest : FeedRequestable {
    class func loadData(from: Int, length: Int) async -> [AppItemInfo]? {
        await withUnsafeContinuation { continuation in
            DispatchQueue.global().async {
                guard let url = Bundle.main.url(forResource: "appInfos", withExtension: "json") else {
                    continuation.resume(returning: nil)
                    return
                }
                do {
                    let data = try Data(contentsOf: url)
                    let decoder = JSONDecoder()
                    let model = try decoder.decode(FeedModel.self, from: data)
                    let right = min(from + length, model.resultCount!);
                    if let results = model.results {
                        continuation.resume(returning: Array(results[from..<right]))
                    } else {
                        continuation.resume(returning: nil)
                    }
                } catch {
                    logger.error("\(error.localizedDescription)")
                    continuation.resume(returning: nil)
                }
            }
        }
        
    }
    
}

class FeedModel : Decodable {
    // MARK: Codable
    
    private enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
    
    var resultCount: Int?
    var results: [AppItemInfo]?
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        resultCount = try? values.decode(Int.self, forKey: .resultCount)
        results = try? values.decode([AppItemInfo].self, forKey: .results)
    }
}

class StoreLike {
    static let key = "likes"
    var likes: Set = Set<String>()
    init() {
        if let saves = UserDefaults.standard.value(forKey: StoreLike.key) as? Array<String> {
            likes = Set(saves)
        }
    }
    
    func update(_ id: String) {
        likes.insert(id)
    }
    
    func remove(_ id: String) {
        likes.remove(id)
    }
    
    func save() {
        let list = Array(likes)
        UserDefaults.standard.set(list, forKey: StoreLike.key)
        UserDefaults.standard.synchronize()
    }
}
