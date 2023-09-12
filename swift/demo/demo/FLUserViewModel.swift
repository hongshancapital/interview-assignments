//
//  FLUserViewModel:.swift
//  demo
//
//  Created by 张帅 on 2023/4/8.
//

import UIKit
import Combine
enum Constant {
    static let maxDataCount: CGFloat = 50
    static let pageDataCount: Int = 10
}
class FLUserViewModel: ObservableObject {
    @Published private(set) var users: [FLUser] = []
    @Published private(set) var userImages = [Int : UIImage]()
    @Published var pageIndex: Int = 0
    
    func loadMore(pageIndex: Int) {
        let startIndex: Int = pageIndex * Constant.pageDataCount
        let endIndex: Int = startIndex + Constant.pageDataCount
        guard endIndex < usersForJsonData.count - 1 else {
            return
        }
        let newData = usersForJsonData[startIndex..<endIndex]
        users += newData
    }
    
    func refresh() {
        pageIndex = 0
        let startIndex: Int = 0
        let endIndex: Int = Constant.pageDataCount
        let newData = usersForJsonData[startIndex...endIndex]
        users = Array(newData)
    }
    
    func getImage(for user: FLUser) {
        guard case .none = userImages[user.artistId] else {
            return
        }
        let iconUrl = URL(string: user.artworkUrl100)
        guard let iconUrl else {
            return
        }
        let request = URLRequest(url: iconUrl)
        URLSession.shared.send(request: request)
            .map { UIImage(data: $0) }
            .replaceError(with: nil)
            .eraseToAnyPublisher()
            .receive(on: DispatchQueue.main)
            .receive(subscriber: Subscribers.Sink<UIImage?, Never>(receiveCompletion: {_ in}) { [weak self] image in
                guard let self = self else { return }
                self.userImages[user.artistId] = image
            })
     }
}
