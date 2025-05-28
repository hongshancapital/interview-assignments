//

//
//  AsyncImage.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/3.
//  支持iOS14的AsyncImage
//
    

import SwiftUI

@available(iOS, deprecated: 15.0)
public struct AsyncImage<Content> : View where Content : View {
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (AsyncImagePhase) -> Content
    
    public init(url: URL?, scale: CGFloat = 1) where Content == Image {
        self.url = url
        self.scale = scale
        self.transaction = Transaction()
        self.content = { $0.image ?? Image("") }
    }
    
    public init<I, P>(url: URL?, scale: CGFloat = 1, @ViewBuilder content: @escaping (Image) -> I, @ViewBuilder placeholder: @escaping () -> P) where Content == _ConditionalContent<I, P>, I: View, P: View {
        self.url = url
        self.scale = scale
        self.transaction = Transaction()
        self.content = { phase -> _ConditionalContent<I, P> in
            if let image = phase.image {
                return ViewBuilder.buildEither(first: content(image))
            } else {
                return ViewBuilder.buildEither(second: placeholder())
            }
        }
    }
    
    public init(url: URL?, scale: CGFloat = 1, transaction: Transaction = Transaction(), @ViewBuilder content: @escaping (AsyncImagePhase) -> Content ) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }
    
    public var body: some View {
        if #available(iOS 14.0, *) {
            ContentBody(url: url, scale: scale, transaction: transaction, content: content)
        } else {
            ContentCompatBody(url: url, scale: scale, transaction: transaction, content: content)
        }
    }
    
}

@available(iOS, deprecated: 15.0)
private final class Provider: ObservableObject {
    @Published var phase: AsyncImagePhase
    
    init() {
        self.phase = .empty
    }
    
    func task(url: URL?, scale: CGFloat, transaction: Transaction) {
        guard let url = url else { return }
        URLSession.shared.dataTask(with: url) { data, _, error in
            DispatchQueue.main.async { [weak self] in
                if let error = error {
                    self?.phase = .failure(error)
                    return
                }
                
                withTransaction(transaction) {
                    self?.phase = self?.image(from: data, scale: scale).map {AsyncImagePhase.success($0)} ?? .empty
                }
            }
        }
        .resume()
    }
    
    private func image(from data: Data?, scale: CGFloat) -> Image? {
        return data.flatMap { UIImage(data: $0, scale: scale) }.map(Image.init(uiImage:))
    }
}

private struct ContentBody<Content> : View where Content: View {
    @StateObject private var provider = Provider()
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (AsyncImagePhase) -> Content
    
    init(url: URL?, scale: CGFloat, transaction: Transaction, @ViewBuilder content: @escaping (AsyncImagePhase) -> Content ) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }
    
    var body: some View {
        content(provider.phase)
            .onAppear {
                provider.task(url: url, scale: scale, transaction: transaction)
            }
            .onChange(of: url) { url in
                provider.task(url: url, scale: scale, transaction: transaction)
            }
    }
}

private struct ContentCompatBody<Content>: View where Content: View {
    struct Body: View {
        @ObservedObject private var provider: Provider
        private let content: (AsyncImagePhase) -> Content
        
        init(provider: Provider, url: URL?, scale: CGFloat, transaction: Transaction, @ViewBuilder content: @escaping (AsyncImagePhase) -> Content) {
            self.provider = provider
            self.content = content
            self.provider.task(url: url, scale: scale, transaction: transaction)
        }
        
        var body: some View {
            content(provider.phase)
        }
    }
    
    @State private var provider = Provider()
    private let url: URL?
    private let scale: CGFloat
    private let transaction: Transaction
    private let content: (AsyncImagePhase) -> Content
    
    init(url: URL?, scale: CGFloat, transaction: Transaction, @ViewBuilder content: @escaping (AsyncImagePhase) -> Content) {
        self.url = url
        self.scale = scale
        self.transaction = transaction
        self.content = content
    }
    
    var body: Body {
        Body(provider: provider, url: url, scale: scale, transaction: transaction, content: content)
    }
}
