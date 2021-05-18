import Foundation
import UIKit
import SwiftUI

struct CircleView: View {
    var color: Color = .red
    var offsetX: CGFloat = 0.0
    var offsetY: CGFloat = 0.0
    var scaleTo: CGFloat = 1.0
    @Binding var isAnimating: Bool
    
    var body: some View {
        Circle()
            .frame(width: 50, height: 50)
            .foregroundColor(color)
            .opacity(0.5)
            .offset(x: isAnimating ? 0:offsetX , y: isAnimating ? 0:offsetY)
            .rotationEffect(.degrees(isAnimating ? 360 : 0))
            .scaleEffect(isAnimating ? scaleTo:1)
            .animation( isAnimating ?
                Animation
                    .easeInOut(duration: 0.5)
                    .repeatForever(autoreverses: true)
                :.default
            )
    }
}



struct LoadingView: View {
    @Binding var isAction: Bool
    
    var body: some View{
        ZStack {
            CircleView(color: .red, offsetX: 25, offsetY: 0, scaleTo: 0.4, isAnimating: $isAction)
            CircleView(color: .blue, offsetX: -25, offsetY: 0, scaleTo: 0.3, isAnimating: $isAction)
            CircleView(color: .yellow, offsetX: 0, offsetY: 25, scaleTo: 0.2, isAnimating: $isAction)
            CircleView(color: .green, offsetX: 0, offsetY: -25, scaleTo: 0.1, isAnimating: $isAction)
        }.opacity(isAction ? 1 : 0)
    }
}
