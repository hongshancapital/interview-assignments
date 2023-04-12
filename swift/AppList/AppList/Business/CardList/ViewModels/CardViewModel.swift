import Foundation

class CardViewModel: ObservableObject {
    let model: CardModel
    @Published
    var isLiked: Bool
    
    init(model: CardModel) {
        self.model = model
        self.isLiked = false
    }
    
    func updateLikeState() {
        let likedData = UserDefaults.CardList.likedData.stringArrayValue
        isLiked = likedData?.contains(where: { $0 == model.bundleId }) ?? false
    }
    
    func likeAction() {
        isLiked.toggle()
        updateLikedData()
    }
    
    private func updateLikedData() {
        // update local data
        if var likedData = UserDefaults.CardList.likedData.stringArrayValue {
            if isLiked {
                likedData.append(model.bundleId)
            } else {
                likedData.removeAll { $0 == model.bundleId }
            }
            UserDefaults.CardList.likedData.set(likedData)
        } else {
            UserDefaults.CardList.likedData.set([model.bundleId])
        }
    }
}

extension CardViewModel: Identifiable {
    var id: String { model.bundleId }
}

extension CardViewModel: Equatable {
    static func == (lhs: CardViewModel, rhs: CardViewModel) -> Bool {
        lhs.model.bundleId == rhs.model.bundleId
    }
}
