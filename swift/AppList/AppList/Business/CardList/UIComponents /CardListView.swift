import SwiftUI

struct CardListView: View {
    
    let cardList: [CardViewModel]
    
    var body: some View {
        ForEach(cardList, id: \.model.bundleId) { card in
            CardView(viewModel: card)
                .listRowInsets(EdgeInsets(top: .Padding.one, leading: 0, bottom: .Padding.one, trailing: 0))
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
        }
    }
}
