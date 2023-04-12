import SwiftUI

struct HeartLikeView: View {
    let isLiked: Bool
    
    private var likeImageName: String {
        isLiked ? "heart.fill" : "heart"
    }
    private var scaleEffect: CGFloat {
        isLiked ? 1 : 0.8
    }
    private var foregroundColor: Color {
        isLiked ? .red : .secondary
    }
    
    var body: some View {
        Image(systemName: likeImageName)
            .resizable()
            .scaledToFit()
            .frame(width: .Length.three, height: .Length.three)
            .scaleEffect(scaleEffect)
            .foregroundColor(foregroundColor)
            .animation(.easeOut(duration: .Animation.short), value: isLiked)
    }
}
