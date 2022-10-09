//
//  MarkService.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

protocol MarkService {
    
    func update(by trackId: Int, marked: Bool) async
    
    func isMarked(for trackId: Int) async -> Bool
    
}

struct LocalMarkService: MarkService {
    
    static let shared = LocalMarkService()
    private init() { }
    
    func update(by trackId: Int, marked: Bool) {
        var sets = getMarkedTrackIds()
        if marked {
            sets.insert(trackId)
        } else {
            sets.remove(trackId)
        }
        writeTrackIds(sets)
    }
    
    func isMarked(for trackId: Int) -> Bool {
        return getMarkedTrackIds().contains(trackId)
    }
    
    private let key = "Apps.UserDefaults.Key"
    
    private func getMarkedTrackIds() -> Set<Int> {
        if let trackIds = UserDefaults.standard.object(forKey: key) as? Array<Int> {
            return Set(trackIds)
        } else {
            return Set<Int>()
        }
    }
    
    private func writeTrackIds(_ trackIds: Set<Int>) {
        UserDefaults.standard.set(Array(trackIds), forKey: key)
    }
    
}
