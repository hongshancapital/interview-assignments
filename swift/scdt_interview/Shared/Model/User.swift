//
//  User.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

struct User: Codable {
    var favoriteAppIDs: Set<Int>

    func isFavorite(id: Int) -> Bool {
        favoriteAppIDs.contains(id)
    }
}
