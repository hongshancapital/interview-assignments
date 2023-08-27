//
//  AppsManager.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation

class AppManager: AppNetworkProtocol {
    typealias T = AppItemViewModel

    static let shared = AppManager()

    // Memory map to record user behaviours
    private var favoritedMap = [Int64:Bool]()

    // Backend interface to replace in future
    private var backend:(any BackendProtocol)?

    private init() { }

    public func configBackend(_ backendServer:(any BackendProtocol)?) {
        backend = backendServer
    }

    public func getApps(_ page: Int, _ count: Int) async -> [AppItemViewModel] {
        // Make sure backend is not nil
        guard let backend = backend else { return [] }

        // Validate input parameters
        guard page > 0 && count > 0 else { return [] }

        let rawAppItems = await backend.getApps(page, count)

        var result: [AppItemViewModel] = []

        for rawItem in rawAppItems {
            let isFavorited = isFavorited(rawItem.trackId)
            let item = AppItemViewModel(rawItem.trackId, rawItem.trackName, rawItem.description, isFavorited, rawItem.artworkUrl60)
            result.append(item)
        }

        return result
    }

    private func isFavorited(_ itemId: Int64) -> Bool {
        return favoritedMap[itemId] ?? false
    }

    public func markFavorited(_ itemId: Int64, _ flag: Bool) {
        favoritedMap[itemId] = flag
    }
}
