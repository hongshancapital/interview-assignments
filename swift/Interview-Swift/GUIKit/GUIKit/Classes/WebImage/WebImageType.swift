//
//  WebImageType.swift
//  WebImageType
//
//  Created by lizhao on 2022/9/16.
//

import UIKit

public protocol WebImageType {
    typealias ID = String
    var id: ID { get }
    
    func prepareForDisplay() async throws -> WebImageType
    func getUIImage() async throws -> UIImage
    
    func hash(into hasher: inout Hasher)
}

public extension WebImageType {
    func eraseToAnyWebImage() -> AnyWebImage {
        return AnyWebImage(self)
    }
    
    func getUIImageFrom(cache: WebImageCache? = nil) async throws -> UIImage {
        let c = cache ?? WebImageCache.shared
        return try await c.get(self)
    }
    
    func removeFromCache(_ cache: WebImageCache?) async {
        let c = cache ?? WebImageCache.shared
        await c.remove(self)
    }
}


public struct AnyWebImage: WebImageType, Equatable, Hashable, Identifiable {
    private let image: WebImageType
    
    public init(_ image: WebImageType) {
        self.image = image
    }
    
    public var id: ID {
        image.id
    }
    
    public func prepareForDisplay() async throws -> WebImageType {
        try await image.prepareForDisplay()
    }
    
    public func getUIImage() async throws -> UIImage {
        return try await image.getUIImage()
    }
    
    public func hash(into hasher: inout Hasher) {
        image.hash(into: &hasher)
    }
    
    public static func ==(lsh: AnyWebImage, rhs: AnyWebImage) -> Bool {
        lsh.id == rhs.id
    }
}
