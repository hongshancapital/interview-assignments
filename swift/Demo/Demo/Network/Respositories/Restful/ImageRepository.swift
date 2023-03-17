//
//  ImageWebRepository.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/16.
//

import UIKit
import Combine

protocol DemoImageRepository: RestfulWebRepository {
    func load(imageURL: URL) async throws -> UIImage
}

struct DemoRealImageRepository: DemoImageRepository {
    let session: URLSession
    let baseURL: String
    let bgQueue = DispatchQueue(label: "bg_image_download_queue")
    
    init(session: URLSession, baseURL: String) {
        self.session = session
        self.baseURL = baseURL
    }
    
    func load(imageURL: URL) async throws -> UIImage {
        let urlRequest = URLRequest(url: imageURL)
        let (data, response) = try await session.data(for: urlRequest)
        
        guard let code = (response as? HTTPURLResponse)?.statusCode else {
            throw APIError.unexpectedResponse
        }
        
        guard HTTPCodes.success.contains(code) else {
            throw APIError.httpCode(code)
        }

        guard let image = UIImage(data: data) else {
            throw APIError.imageDeserialization
        }
        
        return image
    }
}
