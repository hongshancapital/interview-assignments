import SwiftUI

struct CardView: View {
    @ObservedObject
    var viewModel: CardViewModel
    
    var body: some View {
        HStack(spacing: 0) {
            AppIconView(url: viewModel.model.artworkUrl60)
                .padding(.trailing, .Padding.one)
            
            VStack(alignment: .leading, spacing: 0) {
                Text(viewModel.model.artistName)
                    .lineLimit(1)
                    .font(.headline)
                
                Text(viewModel.model.description)
                    .lineLimit(2)
                    .font(.subheadline)
                    .padding(.top, .Padding.one)
            }
            .padding(.trailing, .Padding.two)
            
            Spacer()
            
            HeartLikeView(isLiked: viewModel.isLiked)
                .onTapGesture {
                    viewModel.likeAction()
                }
        }
        .padding(.Padding.two)
        .background(Color.white)
        .clipShape(
            RoundedRectangle(cornerRadius: .CornerRadius.two, style: .continuous)
        )
        .onAppear {
            viewModel.updateLikeState()
        }
    }
}
