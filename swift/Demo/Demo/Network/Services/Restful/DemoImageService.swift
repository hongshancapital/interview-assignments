//
//  DemoImageService.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/16.
//

import UIKit
import Combine

protocol DemoImageService {
    func load(imageURL: URL) async throws -> UIImage
}

struct DemoRealImageService: DemoImageService {
    let imageRepository: DemoImageRepository
    init(webRepository: DemoImageRepository = DemoRealImageRepository(session: URLSession(configuration: .default), baseURL: Host.itunesHost)) {
        self.imageRepository = webRepository
    }

    func load(imageURL: URL) async throws -> UIImage {
        try await imageRepository.load(imageURL: imageURL)
    }
}
