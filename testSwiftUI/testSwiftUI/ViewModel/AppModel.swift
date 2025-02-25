//
//  AppModel.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/5.
//

import SwiftUI

// MARK: - AppResonseModel
struct AppResonseModel: Codable, Hashable {
    let resultCount: Int
    let results: [AppModel]
}

struct AppModel: Identifiable, Hashable, Codable {
    
    var id = ""
    var artworkUrl60: String
    var description: String
    var trackName: String
    
    private var _isCacheCollectioned: Bool? = nil
    var isCollectioned: Bool {
        get {
            return  _isCacheCollectioned ?? getIsCollectionedFromDisk()
        }
        set {
            _isCacheCollectioned = newValue
            storeIsCollectionedToDisk(isCollected: newValue)
        }
    }
    
    private func getIsCollectionedFromDisk() -> Bool{
        let value = UserDefaults.standard.value(forKey: id + "APPModelCollection") as? Bool
        return value ?? false
    }
    
    private func storeIsCollectionedToDisk(isCollected: Bool) {
        DispatchQueue.global().async {
            UserDefaults.standard.set(isCollected, forKey: id + "APPModelCollection")
            UserDefaults.standard.synchronize()
        }
    }
    
    enum CodingKeys: String, CodingKey {
        case id = "bundleId"
        case artworkUrl60
        case description
        case trackName
    }
}
