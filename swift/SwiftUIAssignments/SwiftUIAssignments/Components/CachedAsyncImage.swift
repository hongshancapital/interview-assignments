//
//  CachedAsyncImage.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/6.
//

import SwiftUI

private extension URLSession {
    ///  cache session for image
    static let imageSession: URLSession = {
        let cfg = URLSessionConfiguration.default
        cfg.urlCache = URLCache(memoryCapacity: 20 * 1024 * 1024, diskCapacity: 50 * 1024 * 1024)
        return URLSession(configuration: cfg)
    }()
}

struct CachedAsyncImage: View {

    private var urlRequest: URLRequest?

    /// session for cache
    private var session: URLSession?

    @State private var phase: AsyncImagePhase

    // MARK: - system
    init(url: URL?) {
        guard let url else {
            _phase = .init(wrappedValue: .failure(URLError(.badURL)))
            return
        }
        let urlRequest = URLRequest(url: url)
        let session = URLSession.imageSession

        /// load cache if exist
        if let data = session.configuration.urlCache?.cachedResponse(for: urlRequest)?.data,
           let uiImage = UIImage(data: data) {
            _phase = .init(wrappedValue: .success(Image(uiImage: uiImage)))
        } else {
            _phase = .init(initialValue: .empty)
        }

        self.urlRequest = urlRequest
        self.session = session
    }

    var body: some View {
        Group {
            switch phase {
            case .empty:
                // prepare load image
                ProgressView().task {
                    await load()
                }
            
            case let .success(image):
                // success
                image.resizable().scaledToFit()

            case let .failure(error):
                // error
                Text(error.localizedDescription)
                    .font(.callout)
                    .foregroundColor(.gray)
                
            @unknown default:
                fatalError("This has not been implemented.")
            }
        }.frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

// MARK: - private
private extension CachedAsyncImage {
    func load() async {
        guard let urlRequest, let session else { return }
        do {
            let (data, response) = try await session.data(for: urlRequest)
            guard let response = response as? HTTPURLResponse,
                  200...299 ~= response.statusCode,
                  let uiImage = UIImage(data: data) else {
                throw URLError(.unknown)
            }
            phase = .success(Image(uiImage: uiImage))
        } catch {
            phase = .failure(error)
        }
    }
}
