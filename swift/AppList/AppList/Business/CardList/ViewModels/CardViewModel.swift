import Foundation

class CardViewModel: ObservableObject {
    let model: CardModel
    @Published
    var isLiked: Bool
    
    init(model: CardModel) {
        self.model = model
        self.isLiked = false
    }
    
    func likeAction() {
        isLiked.toggle()
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
