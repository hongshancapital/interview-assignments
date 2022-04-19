//
//  ImageLoad.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/16/22.
//

import Foundation
import SwiftUI

enum ImageLoaderError: Error {
    case InvalidURLStr
    case HTTPLoadFail
    case ConvertDataFail
}

extension URLSession: URLSessionProtocol {
    func requestData(from url: URL) async throws -> (Data, URLResponse) {
        return try await self.data(from: url, delegate: nil)
    }
}

class ImageLoader {
    var urlSession: URLSessionProtocol
    
    init(urlSession: URLSessionProtocol = URLSession.shared) {
        self.urlSession = urlSession
    }
    
    func fetchImage(from urlStr: String, size: CGSize = CGSize(width: 50, height: 50)) async throws -> Image {
        try await Task.sleep(nanoseconds: 500_000_000)
        guard let url = URL(string: urlStr) else {
            throw ImageLoaderError.InvalidURLStr
        }
        let (data, res) = try await self.urlSession.requestData(from: url)
        guard (res as? HTTPURLResponse)?.statusCode == 200 else {
            throw ImageLoaderError.HTTPLoadFail
        }
        guard let tempImage = UIImage(data: data)?.preparingThumbnail(of: size) else {
            throw ImageLoaderError.ConvertDataFail
        }
        return Image(uiImage: tempImage)
    }
}
