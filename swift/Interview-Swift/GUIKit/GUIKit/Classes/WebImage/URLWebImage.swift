//
//  URLWebImage.swift
//  ProduceImageType
//
//  Created by lizhao on 2022/9/16.
//

import Foundation
import UIKit


public class URLWebImage: WebImageType {
    public var id: ID

    let url: URL
    public init(_ url: URL) {
        self.url = url
        self.id = url.hashValue.description
    }

    public func prepareForDisplay() async throws -> WebImageType {
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let image = DownloadedWebImage(data: data) else {
            throw WebImageError.dataToImageConvertionFailed
        }
        return try await image.prepareForDisplay()
    }

    public func getUIImage() async throws -> UIImage {
        let image = try await self.prepareForDisplay()
        return try await image.getUIImage()
    }

    public func hash(into hasher: inout Hasher) {
        hasher.combine(url)
    }
}


public class DownloadedWebImage: WebImageType {
    public var id: ID {
        "DownloadedWebImage-\(rendered)\(uiImage.hashValue)"
    }
    
    var uiImage: UIImage
    var rendered: Bool
    
    public init(uiImage: UIImage, rendered: Bool = false) {
        self.uiImage = uiImage
        self.rendered = rendered
    }
    
    public init?(data: Data) {
        guard let uiImage = UIImage(data: data) else {
            return nil
        }
        self.uiImage = uiImage
        self.rendered = false
    }
    
    public func prepareForDisplay() async throws -> WebImageType {
        if rendered {
            return self
        }
        
        if let img = await uiImage.byPreparingForDisplay() {
            return DownloadedWebImage(uiImage: img, rendered: true)
        } else {
            throw WebImageError.uiImageDecodeFailed
        }
    }
    
    public func getUIImage() async throws -> UIImage {
        if !rendered {
            let img = try await prepareForDisplay()
            uiImage = try await img.getUIImage()
            rendered = true
        }
        return uiImage
    }
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(uiImage)
        hasher.combine(rendered)
    }
}
