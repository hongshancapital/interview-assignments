//
//  Product.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import Foundation

struct Product: Decodable, Identifiable, Equatable {
    let id: String
    let trackName: String
    let description: String
    let artworkURL: URL
    var isLiked: Bool = false

    enum CodingKeys: String, CodingKey {
        case id = "bundleId"
        case trackName = "trackName"
        case description = "description"
        case artworkURL = "artworkUrl60"
    }
    
    static func == (lhs: Self, rhs: Self) -> Bool {
        return lhs.id == rhs.id
    }
}

#if DEBUG
// For debugging or testing.
extension Product {
    static let preview = Self(
        id: "com.moxco.bumble",
        trackName: "Bumble - Dating. Friends. Bizz",
        description: "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections.",
        artworkURL: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/2b/91/7b/2b917b8b-77cb-e8e8-418b-6fd38e82f814/source/60x60bb.jpg")!,
        isLiked: false
    )
    
    static func makePreviews(count: Int) -> [Self] {
        guard count > 0 else { return [] }
        return (0 ..< count).map { _ in
            let id = "com.moxco.bumble" + "\((0..<Int.max).randomElement()!)"
            return Self(
                id: id,
                trackName: "Bumble - Dating. Friends. Bizz",
                description: "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections.",
                artworkURL: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/2b/91/7b/2b917b8b-77cb-e8e8-418b-6fd38e82f814/source/60x60bb.jpg")!,
                isLiked: false
            )
        }
    }
}
#endif
